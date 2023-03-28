package com.weshopify.platform.validations;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserValidationMessages {

	private List<UserMessage> messages;
}
