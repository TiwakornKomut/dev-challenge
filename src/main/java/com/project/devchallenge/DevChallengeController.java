package com.project.devchallenge;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devchallenge.logic.LinkListLogic;
import com.project.devchallenge.logic.LinkListResponseCode;
import com.project.devchallenge.model.LinkListResponse;
import com.project.devchallenge.model.LinkListException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class DevChallengeController {

	public static final String DEFAULT_STRING = "The Spring Boot dev challenge project";
	public static final String API_TEST_PATH = "/api/test";
	
	@RequestMapping("/")
	public String index() {
		return DEFAULT_STRING;
	}

	@PostMapping(API_TEST_PATH)
	@ResponseBody
	public String test(@RequestParam String linklist) {
		String result = null;
		LinkListResponse response = new LinkListResponse(LinkListResponseCode.RESPONSECODE_SUCCESS);

		// Try to execute and catch the error

		try {
			response.linkList = LinkListLogic.Excute(linklist);
		} catch (LinkListException linkListException) {
			response.setCode(linkListException.errorCode);
		} catch (Exception exception) {
			response.setCode(LinkListResponseCode.RESPONSECODE_SYSTEM_ERROR);
		}

		// Return response as json

		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(response);
		} catch (Exception exception) {
			result = response.toString();
		}

		return result;
	}
}