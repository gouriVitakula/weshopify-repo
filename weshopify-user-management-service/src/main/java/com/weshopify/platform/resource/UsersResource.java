package com.weshopify.platform.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.bean.RoleBean;
import com.weshopify.platform.bean.UserBean;
import com.weshopify.platform.service.RoleMgmtService;
import com.weshopify.platform.service.UserMgmtService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Narsi
 * @since: 24-12-2022 {@summary}: Create and Manage the user resources
 *
 */

@RestController
@Slf4j
public class UsersResource {

	@Autowired
	private RoleMgmtService roleMgmtService;
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping(value = "/users")
	public ResponseEntity<List<UserBean>> createUser(@Valid @RequestBody UserBean userBean) {
		log.info("Weshopify Users Data is: " + userBean.toString());
		List<UserBean> usersList = userMgmtService.createUser(userBean);
		return ResponseEntity.ok(usersList);
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<UserBean>> findAllUsers() {
		List<UserBean> usersList = userMgmtService.getAllUsers();
		return ResponseEntity.ok(usersList);
	}

	@PutMapping(value = "/users")
	public ResponseEntity<List<UserBean>> updateUser(@RequestBody UserBean userBean) {
		log.info("Weshopify Users Data is: " + userBean.toString());
		return null;
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<UserBean> findUserById(@PathVariable("userId") String userId) {
		return null;
	}
	
	@DeleteMapping(value = "/users/{userId}")
	public ResponseEntity<List<UserBean>> deleteUserById(@PathVariable("userId") String userId) {
		return null;
	}
	
	@GetMapping(value = "/users/roles")
	public ResponseEntity<List<RoleBean>> findAllRoles() {
		List<RoleBean> rolesList = roleMgmtService.getAllRoles();
		ResponseEntity<List<RoleBean>> rolesResponse = null;
		if(null != rolesList && rolesList.size() >0) {
			rolesResponse = ResponseEntity.ok().body(rolesList);
		}else {
			rolesResponse = ResponseEntity.noContent().build();
		}
		return rolesResponse;
	}
	
	@PostMapping(value = "/users/roles")
	public ResponseEntity<List<RoleBean>> createRole(@RequestBody RoleBean rolePayload) {
		List<RoleBean> rolesList = roleMgmtService.createRole(rolePayload);
		ResponseEntity<List<RoleBean>> rolesResponse = null;
		if(null != rolesList && rolesList.size() >0) {
			rolesResponse = ResponseEntity.ok().body(rolesList);
		}else {
			rolesResponse = ResponseEntity.noContent().build();
		}
		return rolesResponse;
	}
}
