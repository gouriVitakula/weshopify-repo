package com.weshopify.platform.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSO2UserPersonals implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563521298145170544L;
	
	// first Name
	private String givenName;
	
	//last Name
	private String familyName;
	
}
