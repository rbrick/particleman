package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;

public class DiceRollInteger extends Function {
	public java.util.Random random;


	public DiceRollInteger(String name){
		super(name);
		this.random = new java.util.Random();
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		double returnValue = 0;
		double rollCount = this.evaluateArgument(arguments, ctx, 0);
		double min = this.evaluateArgument(arguments, ctx, 1);
		double max = this.evaluateArgument(arguments, ctx, 2);

		for (int i = 0; i < rollCount; i++) {
			returnValue += Math.round(this.random.nextDouble() * (max - min) + min);
		}

		return returnValue;
	}
}
