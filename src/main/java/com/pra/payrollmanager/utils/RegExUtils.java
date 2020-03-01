package com.pra.payrollmanager.utils;

import java.util.regex.Pattern;

public class RegExUtils {

	public static String regExLiteral(String s) {
		return Pattern.quote(s);
	}
}
