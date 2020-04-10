package com.project.devchallenge.model;

import com.project.devchallenge.logic.LinkListResponseCode;

public class LinkListException extends Exception {

	public int errorCode;
	public String errorDescription;

	public LinkListException(int errorCode, String erroerDescription) {
		this.errorCode = errorCode;
		this.errorDescription = erroerDescription;
	}

	public LinkListException(int errorCode) {
		this.errorCode = errorCode;
		this.errorDescription = LinkListResponseCode.mapping.get(errorCode);
	}

}