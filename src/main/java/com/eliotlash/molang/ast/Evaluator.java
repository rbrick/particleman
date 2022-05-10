package com.eliotlash.molang.ast;

import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.functions.FunctionDefinition;
import com.eliotlash.molang.functions.classic.*;
import com.eliotlash.molang.functions.limit.Clamp;
import com.eliotlash.molang.functions.limit.Max;
import com.eliotlash.molang.functions.limit.Min;
import com.eliotlash.molang.functions.rounding.Ceil;
import com.eliotlash.molang.functions.rounding.Floor;
import com.eliotlash.molang.functions.rounding.Round;
import com.eliotlash.molang.functions.rounding.Trunc;
import com.eliotlash.molang.functions.utility.Lerp;
import com.eliotlash.molang.functions.utility.LerpRotate;
import com.eliotlash.molang.functions.utility.Random;
import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.variables.RuntimeVariable;

import java.util.HashMap;
import java.util.Map;

public class Evaluator implements Expr.Visitor<Double>, Stmt.Visitor<Void> {

    private static Evaluator evaluator;
    private ExecutionContext context = new ExecutionContext(this);

    private Map<FunctionDefinition, Function> functionMap = new HashMap<>();

    public Evaluator() {
        registerFunction("math", new Abs("abs"));
        registerFunction("math", new CosDegrees("cos"));
        registerFunction("math", new Cos("cosradians"));
        registerFunction("math", new SinDegrees("sin"));
        registerFunction("math", new Sin("sinradians"));
        registerFunction("math", new Exp("exp"));
        registerFunction("math", new Ln("ln"));
        registerFunction("math", new Mod("mod"));
        registerFunction("math", new Pow("pow"));
        registerFunction("math", new Sqrt("sqrt"));
        registerFunction("math", new Clamp("clamp"));
        registerFunction("math", new Max("max"));
        registerFunction("math", new Min("min"));
        registerFunction("math", new Ceil("ceil"));
        registerFunction("math", new Floor("floor"));
        registerFunction("math", new Round("round"));
        registerFunction("math", new Trunc("trunc"));
        registerFunction("math", new Lerp("lerp"));
        registerFunction("math", new LerpRotate("lerprotate"));
        registerFunction("math", new Random("random"));
    }

    public void registerFunction(String target, Function function) {
        functionMap.put(new FunctionDefinition(new Expr.Variable(target), function.getName()), function);
    }

    public static Evaluator getEvaluator() {
        if (evaluator == null) {
            evaluator = new Evaluator();
        }
        return evaluator;
    }

    public void setExecutionContext(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public Void visitExpression(Stmt.Expression stmt) {
        evaluate(stmt.expr());
        return null;
    }

    @Override
    public Void visitReturn(Stmt.Return stmt) {
        return null;
    }

    @Override
    public Void visitBreak(Stmt.Break stmt) {
        return null;
    }

    @Override
    public Void visitContinue(Stmt.Continue stmt) {
        return null;
    }

    @Override
    public Void visitLoop(Stmt.Loop stmt) {
        return null;
    }

    @Override
    public Double visitAccess(Expr.Access expr) {
        if(context.assignableMap.containsKey(expr)) {
            return context.assignableMap.getDouble(expr);
        }
        return null;
    }

    @Override
    public Double visitAssignment(Expr.Assignment expr) {
        double value = evaluate(expr.expression());
        context.assignableMap.put(expr.variable(), value);
        return value;
    }

    @Override
    public Double visitBinOp(Expr.BinOp expr) {
        return expr.operator().apply(() -> evaluate(expr.left()), () -> evaluate(expr.right()));
    }

    @Override
    public Double visitBlock(Expr.Block expr) {
        return 0.0;
    }

    @Override
    public Double visitCall(Expr.Call expr) {
        FunctionDefinition functionDefinition = new FunctionDefinition(expr.target(), expr.member());
        Function function = this.functionMap.get(functionDefinition);
        if (function == null) {
            return 0.0;
        } else {
            try {
                return function.evaluate(expr.arguments().toArray(Expr[]::new), context);
            } catch (Exception e) {
                return 0.0;
            }
        }
    }

    @Override
    public Double visitCoalesce(Expr.Coalesce expr) {
        var value = evaluate(expr.value());
        return value == null ? evaluate(expr.fallback()) : value;
    }

    @Override
    public Double visitConstant(Expr.Constant expr) {
        return expr.value();
    }

    @Override
    public Double visitGroup(Expr.Group expr) {
        return evaluate(expr.value());
    }

    @Override
    public Double visitNegate(Expr.Negate expr) {
        Double evaluate = evaluate(expr.value());
        return -evaluate;
    }

    @Override
    public Double visitNot(Expr.Not expr) {
        return evaluate(expr.value()) == 0 ? 1.0 : 0.0;
    }

    @Override
    public Double visitTernary(Expr.Ternary expr) {
        Expr branch = evaluate(expr.condition()) == 0 ? expr.ifFalse() : expr.ifTrue();
        return evaluate(branch);
    }

    @Override
    public Double visitVariable(Expr.Variable expr) {
        RuntimeVariable runtimeVariable = context.getCachedVariable(expr.name());
        return context.getVariableMap().getOrDefault(runtimeVariable, 0);
    }

    public Double evaluate(Expr expr) {
        return expr.accept(this);
    }

    public ExecutionContext getContext() {
        return context;
    }
}
