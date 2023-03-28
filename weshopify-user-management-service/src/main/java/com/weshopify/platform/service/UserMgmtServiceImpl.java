package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.weshopify.platform.bean.UserBean;
import com.weshopify.platform.exceptions.APIException;
import com.weshopify.platform.model.WSO2PhoneNumbers;
import com.weshopify.platform.model.WSO2User;
import com.weshopify.platform.model.WSO2UserPersonals;
import com.weshopify.platform.outbound.UserMgmtClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserMgmtClient userMgmtClient;

	@Override
	public List<UserBean> getAllUsers() {
		List<WSO2User> wso2UsersList = userMgmtClient.findAllUsers();

		if (!CollectionUtils.isEmpty(wso2UsersList)) {
			log.info("wso2 Users size {}", wso2UsersList.size());

			List<UserBean> usersList = new ArrayList<>();

			wso2UsersList.stream().forEach(wso2User -> {

				/**
				 * Filter the users list to not displaying the wso2 admin users details in the
				 * response
				 */
				Arrays.asList(wso2User.getEmails()).forEach(email -> {
					if (!email.contentEquals("admin@wso2.com")) {
						usersList.add(mapWSO2UserToUserBean(wso2User));
					}
				});

			});

			return usersList;
		} else {
			throw new RuntimeException("No Users Found");
		}
	}

	@Override
	public List<UserBean> createUser(UserBean user) {
		
		List<WSO2User>	usersList = userMgmtClient.createUser(mapUserBeanToWSO2User(user));
		if(!CollectionUtils.isEmpty(usersList)) {
			List<UserBean> usersBeanList = new ArrayList<>();
			
			usersList.parallelStream().forEach(wso2User->{
				usersBeanList.add(mapWSO2UserToUserBean(wso2User));
			});
			
			return usersBeanList;
		}else {
			throw new APIException("No Users Found",HttpStatus.NOT_FOUND.value());
		}
		
	}

	/**
	 * To Convert the WSO2 User Model to User Bean
	 * 
	 * @param wso2User
	 * @return
	 */
	private UserBean mapWSO2UserToUserBean(WSO2User wso2User) {
		UserBean userBean = UserBean.builder().id(wso2User.getId()).firstName(wso2User.getName().getGivenName())
				.lastName(wso2User.getName().getFamilyName()).emails(wso2User.getEmails())
				.userId(wso2User.getUserName()).build();
		return userBean;

	}

	/**
	 * To Convert the user bean to wso2 user model
	 * @param userBean
	 * @return
	 */
	private WSO2User mapUserBeanToWSO2User(UserBean userBean) {

		WSO2UserPersonals personals = WSO2UserPersonals.builder().familyName(userBean.getLastName())
				.givenName(userBean.getFirstName()).build();

		WSO2PhoneNumbers userContactNum =  WSO2PhoneNumbers.builder().type("work").value(userBean.getMobile()).build();
		
		WSO2User wso2UserModel = WSO2User
								.builder()
								.emails(userBean.getEmails())
								.name(personals)
								.password(userBean.getPassword())
								.phoneNumbers(Arrays.asList(userContactNum))
								.schemas(new String[] {})
								.userName(userBean.getUserId())
								.build();
		return wso2UserModel;

	}

}
