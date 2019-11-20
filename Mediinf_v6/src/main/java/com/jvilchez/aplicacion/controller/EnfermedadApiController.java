package com.jvilchez.aplicacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jvilchez.aplicacion.entity.Enfermedad;
import com.jvilchez.aplicacion.service.EnfermedadService;

@Controller
@RestController
public class EnfermedadApiController {
    
	//private String STORAGEPATH;
	
	//@Autowired
	private EnfermedadService enfermedadService;
	
	
	//API CONSUMELO :V
	
			@GetMapping("/api")
			public List<Enfermedad> enfermedades() {
				List<Enfermedad> enfermedades = enfermedadService.findAll();
				return enfermedades;
			}
			
			@GetMapping("/api/{id}")
			public Enfermedad obtener(@PathVariable Long id) {
				
				Enfermedad enfermedad = enfermedadService.findById(id);
					
				return enfermedad;
			}
	
}
