package com.eliotlash.molang.functions.rounding;

import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.variables.ExecutionContext;

public class Trunc extends Function {

	public Trunc(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 1;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		double value = this.evaluateArgument(arguments, ctx, 0);

		return value < 0 ? Math.ceil(value) : Math.floor(value);
	}
}
