package com.eliotlash.molang.utils;

public class MolangUtils {
	public static float normalizeTime(long timestamp) {
		return ((float) timestamp / 24000);
	}

	public static float booleanToFloat(boolean input) {
		return input ? 1.0F : 0.0F;
	}

	public static boolean doubleToBoolean(double input) {
		// Any value that isn't 0.0 is truthy: https://bedrock.dev/docs/beta/Molang#Values
		return input != 0.0;
	}
}
