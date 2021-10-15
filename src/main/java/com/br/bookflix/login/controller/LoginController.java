package com.br.bookflix.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.login.UserLogin;
import com.br.bookflix.login.service.LoginServiceImpl;
import com.br.bookflix.user.User;
import com.br.bookflix.user.controller.UserModelAssembler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Login", tags = { "Login" }, description = "Login functionality.")
public class LoginController {
	
	@Autowired
	private LoginServiceImpl loginService;
	
	@Autowired
	private UserModelAssembler userAssembler;
	
	@ApiOperation(value = "Login to the application", response = User.class, notes = "This endpoint is responsible for the login functionality.")
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public EntityModel<User> login(
			@ApiParam(name = "user", value = "Email and password in JSON object format") @RequestBody UserLogin userLogin)
			throws BookflixException {
		User user = loginService.login(userLogin);

		return userAssembler.toModel(user);
	}
	
}
