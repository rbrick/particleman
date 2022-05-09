package com.eliotlash.molang.functions.limit;

import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;

public class Max extends Function {

	public Max(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 2;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Math.max(this.evaluateArgument(arguments, ctx, 0), this.evaluateArgument(arguments, ctx, 1));
	}
}
