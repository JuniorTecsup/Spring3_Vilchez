package com.jvilchez.aplicacion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvilchez.aplicacion.entity.User;
import com.jvilchez.aplicacion.service.AuthenticationService;
import com.jvilchez.aplicacion.service.JwtTokenService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	//fase2
	@Autowired
    private AuthenticationService authenticationService;
	
	@Autowired
    private JwtTokenService jwtTokenService;
	
	@PostMapping("login")
	public User login(@RequestParam String correo, @RequestParam String clave) {
		logger.info("login("+correo+", "+clave+")");
		
		User user = authenticationService.login(correo, clave);
		logger.info("Login success: " + user);
		
		String token = jwtTokenService.generateToken(user.getCorreo());
		logger.info("Token: " + token);
		//No tengo token en mi user// pudes implementarlo.. you relax
		user.setToken(token);		
		return user;
	}

}
