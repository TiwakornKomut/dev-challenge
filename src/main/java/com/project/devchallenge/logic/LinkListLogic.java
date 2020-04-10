package com.project.devchallenge.logic;

import com.project.devchallenge.model.LinkListException;
import com.project.devchallenge.util.LinkListUtil;

public class LinkListLogic {

	public static final String LINK_LIST_SEPARATOR = "->";

	/**
	 * The entry point which will be call from REST API
	 * 
	 * @param base64String
	 * @return
	 * @throws LinkListException
	 */
	public static String Excute(String base64String) throws LinkListException {
		if (base64String == null || base64String.isEmpty() || base64String.trim().isEmpty())
			throw new LinkListException(
					LinkListResponseCode.RESPONSECODE_INPUT_STRING_IS_NULL_OR_EMPTY);
		String linkListString = null;
		try {
			linkListString = LinkListUtil.DecodeBase64String(base64String);
		} catch (Exception exception) {
			throw new LinkListException(
					LinkListResponseCode.RESPONSECODE_INPUT_STRING_IS_NOT_BASED64);
		}
		return LinkListUtil.EncodeBase64String(Swap(linkListString.trim()));
	}

	/**
	 * The main function for process the link list swapping
	 * 
	 * @param linkListString
	 * @return
	 * @throws LinkListException
	 */
	private static String Swap(String linkListString) throws LinkListException {
		String[] items = null;
		try {
			// Handle special case
			// input: 1->2->3->4->
			// result: 2->1->4->3
			// these input is not valid
			if (linkListString.endsWith(LINK_LIST_SEPARATOR)) {
				throw new LinkListException(
						LinkListResponseCode.RESPONSECODE_INVALID_LINKLIST_FORMAT);
			}
			items = linkListString.split(LINK_LIST_SEPARATOR);
		} catch (Exception exception) {
			throw new LinkListException(
					LinkListResponseCode.RESPONSECODE_INVALID_LINKLIST_FORMAT);
		}
		if (!checkLinkListFormat(items)) {
			throw new LinkListException(
					LinkListResponseCode.RESPONSECODE_INVALID_LINKLIST_FORMAT);
		}
		swapAdjacentDigit(items);
		return makeLinkListString(items);
	}

	/**
	 * Check the format for each number in list
	 * 
	 * @param items
	 * @return
	 */
	private static boolean checkLinkListFormat(String[] items) {
		if (items.length == 0)
			return false;
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null || items[i].isEmpty()
					|| !LinkListUtil.IsNumeric(items[i].trim())) {
				// invalid linklist format
				return false;
			}
		}
		return true;
	}

	/**
	 * Swapping adjacent digit
	 * 
	 * @param items
	 */
	private static void swapAdjacentDigit(String[] items) {
		for (int i = 0; i < items.length; i += 2) {
			if (i >= items.length - 1)
				break;
			String tmp = items[i];
			items[i] = items[i + 1];
			items[i + 1] = tmp;
		}
	}

	/**
	 * Create the link list string from array of item
	 * 
	 * @param items
	 * @return
	 */
	private static String makeLinkListString(String[] items) {
		String linkListString = "";
		for (int i = 0; i < items.length; i++)
			linkListString += items[i] + ((i < items.length - 1) ? LINK_LIST_SEPARATOR : "");
		return linkListString;
	}

}