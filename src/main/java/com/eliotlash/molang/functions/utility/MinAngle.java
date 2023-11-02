package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.utils.Interpolations;
import com.eliotlash.molang.variables.ExecutionContext;

public class MinAngle extends Function {

	public MinAngle(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		double result = this.evaluateArgument(arguments, ctx, 0);
		// Clamp the result to -360 to 360, then add 360 to make it positive, then mod 360 to get the positive angle
		result = ((result % 360) + 360) % 360;
		// If the result is greater than 180, subtract 360 to get the negative angle
		if (result > 179) result -= 360;
		return result;
	}
}
