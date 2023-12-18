package com.eliotlash.molang.ast;

import java.util.Objects;

public class EvaluatableExpr implements Evaluatable {

    private final Expr expr;
    private final boolean constant;

    public EvaluatableExpr(Expr expr) {
        this.expr = Objects.requireNonNull(expr);
        this.constant = expr instanceof Expr.Constant;
    }

    public double evaluate(Evaluator evaluator) {
        Double result = evaluator.evaluate(this.expr);
        return result == null ? 0 : result;
    }

    public boolean isConstant() {
        return this.constant;
    }

    public double getConstant() {
        if(!isConstant()) {
            return 0.0;
        }
        return Evaluator.getGlobalEvaluator().evaluate(this.expr);
    }
}
