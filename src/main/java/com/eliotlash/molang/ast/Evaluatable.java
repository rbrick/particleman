package com.eliotlash.molang.ast;

import java.util.List;
import java.util.Objects;

public class Evaluatable {
    private Expr expr = null;
    private List<Stmt> stmts = null;
    private boolean constant = false;

    public Evaluatable(Expr expr) {
        this.expr = Objects.requireNonNull(expr);
        constant = expr instanceof Expr.Constant;
    }

    public Evaluatable(List<Stmt> stmts) {
        this.stmts = Objects.requireNonNull(stmts);
    }

    public double evaluate(Evaluator evaluator) {
        if (stmts != null) {
            return evaluator.evaluate(stmts);
        }
        if (expr != null) {
            Double result = evaluator.evaluate(expr);
            return result == null ? 0 : result;
        }

        //should never get here
        return 0.0;
    }

    public boolean isConstant() {
        return constant;
    }

    public double getConstant() {
        if(!isConstant()) {
            return 0.0;
        }
        return Evaluator.getEvaluator().evaluate(expr);
    }

}
