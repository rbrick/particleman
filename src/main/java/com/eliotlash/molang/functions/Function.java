package com.eliotlash.molang.functions;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.variables.ExecutionContext;

public abstract class Function {
    protected String name;

    public Function(String name) {
        this.name = name;
    }

    /**
     * Get name of this function
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get minimum count of arguments this function needs
     */
    public int getRequiredArguments() {
        return 0;
    }

    public abstract double _evaluate(Expr[] arguments, ExecutionContext ctx);

    public double evaluate(Expr[] arguments, ExecutionContext ctx) throws Exception {
        if (arguments.length < this.getRequiredArguments()) {
            String message = String.format("Function '%s' requires at least %s arguments. %s are given!", name, this.getRequiredArguments(), arguments.length);

            throw new Exception(message);
        }

        return _evaluate(arguments, ctx);
    }

    protected double evaluateArgument(Expr[] arguments, ExecutionContext ctx, int index) {
        if(index < 0 || index - 1 > arguments.length) {
            return 0;
        }
        return ctx.getEvaluator().evaluate(arguments[index]);
    }
}
