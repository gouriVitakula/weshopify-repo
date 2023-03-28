package com.weshopify.platform.outbound;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weshopify.platform.bean.RoleBean;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleMgmtClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${iamserver.base-url}")
	private String iam_server_api_base_url; // https://iam.weshopifyapp.in/scim2

	@Value("${iamserver.role-api}")
	private String role_api_context; // /Roles

	@Value("${iamserver.user-name}")
	private String iam_user_name;

	@Value("${iamserver.password}")
	private String iam_password;
	
	@Value("${iamserver.role-api-schema}")
	private String roleApiSchema;

	public List<RoleBean> findAllRoles() {
		List<RoleBean> resourcesList = null;
		String role_api_url = iam_server_api_base_url + role_api_context;
		log.info("constructed role api url is:\t" + role_api_url);

		HttpEntity<String> requestBody = prepareRequestBody(null);

		ResponseEntity<Object> apiResponse = restTemplate.exchange(role_api_url, HttpMethod.GET, requestBody,
				Object.class);
		log.info("response code of the role api is:\t" + apiResponse.getStatusCode().value());
		if (HttpStatus.OK.value() == apiResponse.getStatusCode().value()) {
			Object responseBody = apiResponse.getBody();
			resourcesList = parseResponse(responseBody);
		}

		return Optional.ofNullable(resourcesList).get();
	}

	public List<RoleBean> createRole(RoleBean roleBean){
		List<RoleBean> resourcesList = null;
		String role_api_url = iam_server_api_base_url + role_api_context;
		log.info("constructed role api url is:\t" + role_api_url);

		//roleBean.setSchemas(new String[]{"urn:ietf:params:scim:schemas:extension:2.0:Role"});
		roleBean.setSchemas(new String[]{roleApiSchema});
		
		String rolePayload = null;
		try {
			rolePayload = objectMapper.writeValueAsString(roleBean);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String rolePayload = objectMapper.convertValue(roleBean, String.class);
		
		HttpEntity<String> requestBody = prepareRequestBody(rolePayload);
		
		ResponseEntity<Object> apiResponse = restTemplate
											.exchange(role_api_url, 
													HttpMethod.POST, 
													requestBody, 
													Object.class);
		log.info("response code of the role api is:\t" + apiResponse.getStatusCode().value());
		if (HttpStatus.CREATED.value() == apiResponse.getStatusCode().value()) {
			//Object responseBody = apiResponse.getBody();
			resourcesList = findAllRoles();
		}

		return Optional.ofNullable(resourcesList).get();
	}
	
	private HttpEntity<String> prepareRequestBody(String rolePayload) {
		HttpEntity<String> requestBody = null;
		String adminCreds = iam_user_name + ":" + iam_password;
		log.info("admin creds are:\t" + adminCreds);

		String encodedAdminCreds = Base64.getEncoder().encodeToString(adminCreds.getBytes());
		log.info("admin creds encoded are:\t" + encodedAdminCreds);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + encodedAdminCreds);
		if(StringUtils.isEmpty(rolePayload) || StringUtils.isBlank(rolePayload)) {
			requestBody = new HttpEntity<>(headers);
		}else {
			 headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			 requestBody = new HttpEntity<String>(rolePayload, headers);
		}
		
		
		return requestBody;
	}

	private List<RoleBean> parseResponse(Object responseBody) {
		List<RoleBean> resourcesList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
			log.info("the response body is:\t" + response);

			JSONObject jsonResponseObject = new JSONObject(response);
			JSONArray jsonArray = (JSONArray) Optional.ofNullable(jsonResponseObject)
					.filter(condition -> jsonResponseObject.has("Resources")).get().get("Resources");
			log.info("Resources are:\t" + jsonArray.toString());

			Gson gson = new Gson();
			Type type = new TypeToken<List<RoleBean>>() {
			}.getType();
			resourcesList = gson.fromJson(jsonArray.toString(), type);
			log.info("Resources list are:\t" + resourcesList.size());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return Optional.ofNullable(resourcesList).get();
	}
}
