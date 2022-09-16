package com.ssfw.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author a
 */
public class NumberUtil {

	private static final String ZERO = "0";
	private static final String NULL = "null";

	public static boolean isNotNull(Number number) {
		return !(null == number || 0 == number.doubleValue());
	}

	public static boolean isNull(Number number) {
		return !isNotNull(number);
	}

	public static boolean isNull(CharSequence number) {
		return  StringUtils.isBlank(number) || NULL.contentEquals(number) || ZERO.contentEquals(number);
	}

	public static boolean isNotNull(CharSequence number) {
		return !isNull(number);
	}

	public static <T extends Number> T setEmptyToNull(T integer) {

		if (isNotNull(integer)) {
			return integer;
		}
		return null;
	}

	public static String setScale(Number number, int newScale, RoundingMode roundingMode) {

		BigDecimal rate = BigDecimal.valueOf(Double.parseDouble(number.toString()));
		BigDecimal bigDecimal = rate.setScale(newScale, null == roundingMode ? RoundingMode.HALF_DOWN : roundingMode);
		return bigDecimal.stripTrailingZeros().toPlainString();
	}
}


