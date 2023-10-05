package com.eliotlash.molang.functions.classic;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;

/**
 * Arc tangent function
 */
public class Atan2 extends Function {

	public Atan2(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 2;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Math.atan2(this.evaluateArgument(arguments, ctx, 0), this.evaluateArgument(arguments, ctx, 1));
	}
}
