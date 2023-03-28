package com.weshopify.platform.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weshopify.platform.bean.RoleBean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSO2User implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3877212295621231125L;
	
	private String[] schemas;
	private String userName;
	private String password;
	private String[] emails;
	
	@JsonIgnore
	private String id;
	
	private WSO2UserPersonals name;
	
	@JsonIgnore
	private List<RoleBean> roles;
	private List<WSO2PhoneNumbers> phoneNumbers;

}
