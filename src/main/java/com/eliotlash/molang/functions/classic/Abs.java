package com.eliotlash.molang.functions.classic;

import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;

/**
 * Absolute value function
 */
public class Abs extends Function {

	public Abs(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 1;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Math.abs(this.evaluateArgument(arguments, ctx, 0));
	}
}
