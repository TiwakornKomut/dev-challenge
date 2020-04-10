package com.project.devchallenge.util;

import java.util.Base64;

public class LinkListUtil {

	/**
	 * Encode base64 string
	 * @param originalString
	 * @return
	 */
	public static String EncodeBase64String(String originalString) {
		String encodedString = Base64.getEncoder().encodeToString(originalString.getBytes());
		return encodedString;
	}

	/**
	 * Decode base64 string
	 * @param encodedString
	 * @return
	 */
	public static String DecodeBase64String(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);

		return decodedString;
	}

	/**
	 * Check whether the string is numeric or not
	 * @param strNum
	 * @return
	 */
	public static boolean IsNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}