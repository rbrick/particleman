package com.eliotlash.molang.utils;

public class MolangUtils {
	public static float normalizeTime(long timestamp) {
		return ((float) timestamp / 24000);
	}

	public static float booleanToFloat(boolean input) {
		return input ? 1.0F : 0.0F;
	}

	public static boolean doubleToBoolean(double input) {
		// if not 1 we want to return false as decimal numbers are invalid in this case
		return input == 1.0;
	}
}
