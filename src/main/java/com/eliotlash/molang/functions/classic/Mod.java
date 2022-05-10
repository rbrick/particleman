package com.eliotlash.molang.functions.classic;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;

public class Mod extends Function {

	public Mod(String name) {
		super(name);
	}

	@Override
	public int getRequiredArguments() {
		return 2;
	}

	@Override
	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		return this.evaluateArgument(arguments, ctx, 0) % this.evaluateArgument(arguments, ctx, 1);
	}
}
