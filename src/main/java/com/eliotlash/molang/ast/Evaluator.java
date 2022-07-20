package com.eliotlash.molang.ast;

import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.functions.FunctionDefinition;
import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.variables.RuntimeVariable;

import java.util.List;

public class Evaluator implements Expr.Visitor<Double>, Stmt.Visitor<Void> {
    /**
     * A global evaluator with a global context that is not tied to a specific entity
     */
    private static ExecutionContext globalContext;
    private static Evaluator globalEvaluator;

    static {
        globalContext = new ExecutionContext(globalEvaluator);
        globalEvaluator = new Evaluator();
        globalEvaluator.setExecutionContext(globalContext);
    }

    private ExecutionContext context = new ExecutionContext(this);

    public static Evaluator getGlobalEvaluator() {
        return globalEvaluator;
    }

    public static ExecutionContext getGlobalContext() {
        return globalContext;
    }

    public void setExecutionContext(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public Void visitExpression(Stmt.Expression stmt, StmtContext ctx) {
        ctx.lastExprValue = evaluate(stmt.expr());
        return null;
    }

    @Override
    public Void visitReturn(Stmt.Return stmt, StmtContext ctx) {
        ctx.returnValue = evaluate(stmt.value());
        return null;
    }

    @Override
    public Void visitBreak(Stmt.Break stmt, StmtContext ctx) {
        return null;
    }

    @Override
    public Void visitContinue(Stmt.Continue stmt, StmtContext ctx) {
        return null;
    }

    @Override
    public Void visitLoop(Stmt.Loop stmt, StmtContext ctx) {
        Double count = evaluate(stmt.count());
        for (int i = 0; i < count; i++) {
            evaluate(stmt.expr());
        }
        return null;
    }

    @Override
    public Double visitAccess(Expr.Access expr) {
        if (context.assignableMap.containsKey(expr)) {
            return context.assignableMap.getDouble(expr);
        }
        if (context.functionScopedArguments.containsKey(expr)) {
            return context.functionScopedArguments.getDouble(expr);
        }
        RuntimeVariable cachedVariable = context.getCachedVariable(expr.target().name() + "." + expr.member());
        if (context.getVariableMap().containsKey(cachedVariable)) {
            return context.getVariableMap().getDouble(cachedVariable);
        }
        return 0.0;
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
        return evaluate(expr.statements());
    }

    @Override
    public Double visitCall(Expr.Call expr) {
        FunctionDefinition functionDefinition = new FunctionDefinition(expr.target(), expr.member());
        Function function = this.context.getFunction(functionDefinition);
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

    public double evaluate(List<Stmt> stmts) {
        StmtContext ctx = new StmtContext();
        for (Stmt stmt : stmts) {
            //early return
            if (ctx.returnValue != null) {
                return ctx.returnValue;
            }
            evaluate(stmt, ctx);
        }
        if (ctx.returnValue != null) {
            return ctx.returnValue;
        }

        if (ctx.lastExprValue != null) {
            return ctx.lastExprValue;
        }

        return 0.0;
    }

    private void evaluate(Stmt stmt, StmtContext ctx) {
        stmt.accept(this, ctx);
    }

    public ExecutionContext getContext() {
        return context;
    }
}
