package com.weshopify.platform.model;

import java.io.Serializable;
import java.util.Date;

//@Data
public class Auditor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2669765009392181867L;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private String createdBy;
	private String modifiedBy;
	
}
