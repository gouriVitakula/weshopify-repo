package com.weshopify.platform.bean;

import java.io.File;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author Narsi
 * @since: 24-12-2022
 * @apiNote: WeshopifyPlatformUser
 * {@summary}: PlatformUserBean is used to hold the user's data
 * and will be saved this users in WSO2 IAM
 *
 */

@Data
@Builder
public class WeshopifyPlatformUserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6897965677146490306L;
	
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	private boolean status;
	private File photos;

}
