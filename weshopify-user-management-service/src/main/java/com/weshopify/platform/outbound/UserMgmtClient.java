package com.weshopify.platform.outbound;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weshopify.platform.exceptions.APIException;
import com.weshopify.platform.model.WSO2User;
import com.weshopify.platform.utils.ApplicationsUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserMgmtClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationsUtil propsUtil;

	@Autowired
	private ObjectMapper objectMapper;

	public List<WSO2User> findAllUsers() {
		try {
			String user_api_uri = propsUtil.getIam_server_api_base_url() + propsUtil.getUser_api_context();
			log.info("user api is ", user_api_uri);
			List<WSO2User> wso2UsersList = null;

			HttpEntity<String> requestBody = propsUtil.prepareRequestBody(null);
			ResponseEntity<Object> apiResponse = restTemplate.exchange(user_api_uri, HttpMethod.GET, requestBody,
					Object.class);
			log.info("response code of the role api is:\t" + apiResponse.getStatusCode().value());
			if (HttpStatus.OK.value() == apiResponse.getStatusCode().value()) {
				Object responseBody = apiResponse.getBody();
				wso2UsersList = propsUtil.parseUserResponse(responseBody);
			}else {
				throw new APIException(apiResponse.getBody().toString(), apiResponse.getStatusCode().value());
			}

			return Optional.ofNullable(wso2UsersList).get();
		}catch (Exception e) {
			throw new APIException(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
	}

	public List<WSO2User> createUser(WSO2User wso2User) {

		try {
			String user_api_uri = propsUtil.getIam_server_api_base_url() + propsUtil.getUser_api_context();
			log.info("user api is ", user_api_uri);
			List<WSO2User> wso2UsersList = null;

			String payload = null;
			payload = objectMapper.writeValueAsString(wso2User);

			HttpEntity<String> requestBody = propsUtil.prepareRequestBody(payload);
			ResponseEntity<Object> apiResponse = restTemplate.exchange(user_api_uri, HttpMethod.POST, requestBody,
					Object.class);
			log.info("response code of the role api is:\t" + apiResponse.getStatusCode().value());
			if (HttpStatus.CREATED.value() == apiResponse.getStatusCode().value()) {

				wso2UsersList = findAllUsers();
			} else {
				throw new APIException(apiResponse.getBody().toString(), apiResponse.getStatusCode().value());
			}

			return Optional.ofNullable(wso2UsersList).get();
		} catch (Exception e) {
			throw new APIException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());

		}

	}
}
