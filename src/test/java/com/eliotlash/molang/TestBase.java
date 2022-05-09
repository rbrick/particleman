package com.eliotlash.molang;

import java.util.List;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.ast.Operator;

public class TestBase {
	protected static Expr call(String target, String method, Expr... args) {
		return new Expr.Call(v(target), method, List.of(args));
	}

	protected static Expr.Access access(String var, String member) {
		return new Expr.Access(v(var), member);
	}

	protected static Expr.BinOp op(Expr left, Operator op, Expr right) {
		return new Expr.BinOp(op, left, right);
	}

	protected static Expr.Group paren(Expr expr) {
		return new Expr.Group(expr);
	}

	protected static Expr.Constant c(double constant) {
		return new Expr.Constant(constant);
	}

	protected static Expr.Variable v(String var) {
		return new Expr.Variable(var);
	}
}