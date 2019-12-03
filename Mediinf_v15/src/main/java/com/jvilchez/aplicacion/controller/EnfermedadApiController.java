package com.jvilchez.aplicacion.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.jvilchez.aplicacion.entity.Enfermedad;

import com.jvilchez.aplicacion.service.EnfermedadService;

@RestController
@RequestMapping("/api")
public class EnfermedadApiController {
    
	//private String STORAGEPATH;
	
	//Regenerar con todo y storage ..word
	
	@Autowired
	private EnfermedadService enfermedadService;

	
			@GetMapping("/enfermedades") 
			public List<Enfermedad> enfermedades() {
				List<Enfermedad> enfermedades = enfermedadService.findAll();
				return enfermedades;
				
			}
			//pag 50y inicial
			@GetMapping("/enfermedad/{id}")
			public Enfermedad obtener(@PathVariable Long id) {
				
				Enfermedad enfermedad = enfermedadService.findById(id);
					
				return enfermedad;
			}

			
			@PostMapping("enfermedad/crear")
			public ResponseEntity<?>agregarEnfermedad(@RequestBody Enfermedad enfermedad){
				enfermedadService.save(enfermedad);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
			

			@DeleteMapping("/enfermedad/eliminar/{id}")
		    public ResponseEntity<?> deleteEnfermedad(@PathVariable Long id) {
		       
				enfermedadService.deleteById(id);

		        return ResponseEntity.ok().build();
		    }
			
			@PutMapping("/enfermedad/editar/{id}")
		    public ResponseEntity<?> updateEnfermedad(@PathVariable(value ="id")Long id,@RequestBody Enfermedad enfermedad) {
		       
				//enfermedadService.updateById(id);
            Enfermedad enf= null;
            enf=enfermedadService.findById(id);
				//return enfermedadService.findById(id){
               if(enf != null){
					enf.setNombre(enfermedad.getNombre());
					enf.setMedicamento_g(enfermedad.getMedicamento_g());
		            enfermedadService.save(enf);
		            return new ResponseEntity<>(enf, HttpStatus.OK);
               }else{
    			  return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    		   }
		    }
	
}