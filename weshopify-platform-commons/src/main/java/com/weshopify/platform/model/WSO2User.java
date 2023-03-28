package com.weshopify.platform.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
//@AllArgsConstructor(staticName = "of")
public class WSO2User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3877212295621231125L;
	
	private String[] schemas;
	private String userName;
	private String password;
	private String[] emails;
	
	//@JsonIgnore
	private String id;
	
	private WSO2UserPersonals name;
	
	@JsonIgnore
	private Map<String, String> meta;
	//@JsonIgnore
	private List<Role> roles;
	private List<WSO2PhoneNumbers> phoneNumbers;

}
