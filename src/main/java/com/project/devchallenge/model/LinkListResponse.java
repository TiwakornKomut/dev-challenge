package com.project.devchallenge.model;

import com.project.devchallenge.logic.LinkListResponseCode;

public class LinkListResponse {
	public String linkList;
	public int code;
	public String description;

	public LinkListResponse(int code) {
		setCode(code);
	}

	/**
	 * Set the code and also description nonresponse with it
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
		this.description = LinkListResponseCode.mapping.get(code);
	}

	@Override
	public String toString() {
		return "linkList: " + linkList + ", code: " + code + ", description: " + description;
	}
}