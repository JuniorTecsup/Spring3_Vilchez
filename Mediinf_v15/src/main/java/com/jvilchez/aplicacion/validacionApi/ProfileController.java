package com.jvilchez.aplicacion.validacionApi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvilchez.aplicacion.controller.AuthenticationController;
import com.jvilchez.aplicacion.entity.User;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	
private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@GetMapping
	public User profile(@AuthenticationPrincipal User user) {
		logger.info("profile("+user+")");
		
		return user;
	}


}
