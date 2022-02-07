package com.eliotlash.molang.math.functions;

import java.util.Arrays;

import com.eliotlash.molang.math.IValue;

/**
 * Abstract function class
 * <p>
 * This class provides function capability (i.e. giving it arguments and
 * upon {@link #get()} method you receive output).
 */
public abstract class Function implements IValue {
	protected IValue[] args;
	protected String name;

	public Function(IValue[] args, String name) throws Exception {
		if (args.length < this.getRequiredArguments()) {
			String message = String.format("Function '%s' requires at least %s arguments. %s are given!", name, this.getRequiredArguments(), args.length);

			throw new Exception(message);
		}

		this.args = args;
		this.name = name;
	}

	/**
	 * Get the value of nth argument
	 */
	public double getArg(int index) {
		if (index < 0 || index >= this.args.length) {
			return 0;
		}

		return this.args[index].get();
	}

	@Override
	public String toString() {
		StringBuilder args = new StringBuilder();

		for (int i = 0; i < this.args.length; i++) {
			args.append(this.args[i].toString());

			if (i < this.args.length - 1) {
				args.append(", ");
			}
		}

		return this.getName() + "(" + args + ")";
	}

	/**
	 * Get name of this function
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get minimum count of arguments this function needs
	 */
	public int getRequiredArguments() {
		return 0;
	}

	@Override
	public boolean isConstant() {
		return Arrays.stream(args).allMatch(IValue::isConstant);
	}
}
