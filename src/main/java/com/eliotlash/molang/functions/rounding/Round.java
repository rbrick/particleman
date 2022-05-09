package com.eliotlash.molang.functions.rounding;

import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.ast.Expr;

public class Round extends Function {

	public Round(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 1;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Math.round(this.evaluateArgument(arguments, ctx, 0));
	}
}
