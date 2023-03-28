package com.weshopify.platform.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BrandsBean implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -929521821994144083L;
	
	private String id;

	private String name;
	private String logoPath;
	
	//private List<String> categories;
	private List<CategoryBean> categories;
	
}
