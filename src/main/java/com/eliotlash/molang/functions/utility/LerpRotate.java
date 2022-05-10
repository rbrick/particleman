package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.utils.Interpolations;
import com.eliotlash.molang.variables.ExecutionContext;

public class LerpRotate extends Function {

	public LerpRotate(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return Interpolations.lerpYaw(this.evaluateArgument(arguments, ctx, 0), this.evaluateArgument(arguments, ctx, 1), this.evaluateArgument(arguments, ctx, 2));
	}
}
