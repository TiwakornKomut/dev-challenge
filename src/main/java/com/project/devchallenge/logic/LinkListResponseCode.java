package com.project.devchallenge.logic;

import java.util.HashMap;
import java.util.Map;

public class LinkListResponseCode {

	/**
	* The response code and description table
	*/

	public static final int RESPONSECODE_SUCCESS = 0;
	public static final int RESPONSECODE_SYSTEM_ERROR = 1;
	public static final int RESPONSECODE_INPUT_STRING_IS_NULL_OR_EMPTY = 2;
	public static final int RESPONSECODE_INPUT_STRING_IS_NOT_BASED64 = 3;
	public static final int RESPONSECODE_INVALID_LINKLIST_FORMAT = 4;
	
	public static final Map<Integer, String> mapping = new HashMap<Integer, String>()
	{{
		put(RESPONSECODE_SUCCESS, "Success");
		put(RESPONSECODE_INPUT_STRING_IS_NULL_OR_EMPTY, "Input string is null or empty");
		put(RESPONSECODE_INPUT_STRING_IS_NOT_BASED64, "Input string is not Based64 encoded string");
		put(RESPONSECODE_INVALID_LINKLIST_FORMAT, "Invalid linklist format");
	}};
	
}