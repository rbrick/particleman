package com.eliotlash.molang.functions.classic;

import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.ast.Expr;

public class SinDegrees extends Function {
	public SinDegrees(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 1;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Math.sin(this.evaluateArgument(arguments, ctx, 0) / 180 * Math.PI);
	}
}
