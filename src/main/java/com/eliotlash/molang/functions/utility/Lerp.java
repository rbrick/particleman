package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.variables.ExecutionContext;
import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.utils.Interpolations;

public class Lerp extends Function {

	public Lerp(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Interpolations.lerp(this.evaluateArgument(arguments, ctx, 0), this.evaluateArgument(arguments, ctx, 1), this.evaluateArgument(arguments, ctx, 2));
	}
}
