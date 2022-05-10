package com.eliotlash.molang.functions.limit;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.utils.MathUtils;
import com.eliotlash.molang.variables.ExecutionContext;

public class Clamp extends Function {
	public Clamp(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return MathUtils.clamp(this.evaluateArgument(arguments, ctx, 0), this.evaluateArgument(arguments, ctx, 1), this.evaluateArgument(arguments, ctx, 2));
	}
}
