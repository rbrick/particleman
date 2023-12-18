package com.eliotlash.molang.functions.utility;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.Function;
import com.eliotlash.molang.variables.ExecutionContext;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRoll extends Function {

	public DiceRoll(String name) {
		super(name);
	}

	public boolean isConstant() {
		return false;
	}

	@Override
	public int getRequiredArguments() {
		return 3;
	}

	public double _evaluate(Expr[] arguments, ExecutionContext ctx) {
		double returnValue = 0;
		double rollCount = this.evaluateArgument(arguments, ctx, 0);
		if (rollCount > 0) {
			double min = this.evaluateArgument(arguments, ctx, 1);
			double max = this.evaluateArgument(arguments, ctx, 2);

			for (int i = 0; i < rollCount; i++) {
				returnValue += ThreadLocalRandom.current().nextDouble() * (max - min) + min;
			}
		}

		return returnValue;
	}
}
