package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;

public class Random extends Function {
	public java.util.Random random;


	public Random(String name){
		super(name);
		this.random = new java.util.Random();
	}

	public boolean isConstant() {
		return false;
	}

	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		double random = 0;

		if (arguments.length >= 3) {
			this.random.setSeed((long) this.evaluateArgument(arguments, ctx, 2));
			random = this.random.nextDouble();
		} else {
			random = Math.random();
		}

		if (arguments.length >= 2) {
			double a = this.evaluateArgument(arguments, ctx, 0);
			double b = this.evaluateArgument(arguments, ctx, 1);

			double min = Math.min(a, b);
			double max = Math.max(a, b);

			random = random * (max - min) + min;
		} else if (arguments.length >= 1) {
			random = random * this.evaluateArgument(arguments, ctx, 0);
		}

		return random;
	}
}
