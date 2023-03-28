package com.weshopify.platform.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class Role implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 2017612110322874299L;
	
	 private String type;

	  private String value;

	  private String display;

	  @JsonProperty("$ref")
	  private String ref;

}
