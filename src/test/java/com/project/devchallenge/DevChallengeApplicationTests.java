package com.project.devchallenge;

import static org.assertj.core.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevChallengeApplicationTests {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}

	@Test
	public void testIndex() throws Exception {
		assertThat(this.template.getForObject("http://localhost:" + port + "/", String.class))
				.isEqualTo(DevChallengeController.DEFAULT_STRING);
	}

	private String GetApiTestUrl() {
		return "http://localhost:" + port + "/" + DevChallengeController.API_TEST_PATH;
	}

	public static final String RESPONSE_STRING_IS_NULL_OR_EMPTY = "{\"linkList\":null,\"code\":2,\"description\":\"Input string is null or empty\"}";
	public static final String RESPONSE_INPUT_STRING_IS_NOT_BASED64 = "{\"linkList\":null,\"code\":3,\"description\":\"Input string is not Based64 encoded string\"}";
	public static final String RESPONSE_INVALID_LINKLIST_FORMAT = "{\"linkList\":null,\"code\":4,\"description\":\"Invalid linklist format\"}";
	MultiValueMap<String, Object> postBody = new LinkedMultiValueMap<String, Object>();

	@Test
	public void testNullOrEmptyInputString() {
		postBody.clear();
		postBody.add("linklist", "");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_STRING_IS_NULL_OR_EMPTY);
		postBody.clear();
		postBody.add("linklist", " ");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_STRING_IS_NULL_OR_EMPTY);
	}

	@Test
	public void testInputStringNotBase64Encoded() {
		// 1->2->3->4
		postBody.clear();
		postBody.add("linklist", "1->2->3->4");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INPUT_STRING_IS_NOT_BASED64);
	}

	@Test
	public void testInvalidLinklistFormat() {
		// ->2->3->4->5
		postBody.clear();
		postBody.add("linklist", "LT4yLT4zLT40LT41");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
		// 1->2->->4->5
		postBody.clear();
		postBody.add("linklist", "MS0+Mi0+LT40LT41");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
		// 1->2->3->4->
		postBody.clear();
		postBody.add("linklist", "MS0+Mi0+My0+NC0+");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
		// 1->2->...4->5
		postBody.clear();
		postBody.add("linklist", "MS0+Mi0+Li4uNC0+NQ==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
		// 1->2...4->5
		postBody.clear();
		postBody.add("linklist", "MS0+Mi4uLjQtPjU=");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
		// sdfsadfdsfadf
		postBody.clear();
		postBody.add("linklist", "c2Rmc2FkZmRzZmFkZg==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class))
				.isEqualTo(RESPONSE_INVALID_LINKLIST_FORMAT);
	}

	@Test
	public void testSuccessCase() {
		// 1->2->3->4
		postBody.clear();
		postBody.add("linklist", "MS0+Mi0+My0+NA==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class)).isEqualTo(
				"{\"linkList\":\"Mi0+MS0+NC0+Mw==\",\"code\":0,\"description\":\"Success\"}");
		// 1->2->3->4->5
		postBody.clear();
		postBody.add("linklist", "MS0+Mi0+My0+NC0+NQ==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class)).isEqualTo(
				"{\"linkList\":\"Mi0+MS0+NC0+My0+NQ==\",\"code\":0,\"description\":\"Success\"}");
		// 1->2
		postBody.clear();
		postBody.add("linklist", "MS0+Mg==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class)).isEqualTo(
				"{\"linkList\":\"Mi0+MQ==\",\"code\":0,\"description\":\"Success\"}");
		// 1
		postBody.clear();
		postBody.add("linklist", "MQ==");
		assertThat(this.template.postForObject(GetApiTestUrl(), postBody, String.class)).isEqualTo(
				"{\"linkList\":\"MQ==\",\"code\":0,\"description\":\"Success\"}");
	}

}
