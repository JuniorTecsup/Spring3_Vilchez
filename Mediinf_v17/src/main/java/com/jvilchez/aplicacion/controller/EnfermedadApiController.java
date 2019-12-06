package com.jvilchez.aplicacion.controller;


import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jvilchez.aplicacion.entity.Enfermedad;

import com.jvilchez.aplicacion.service.EnfermedadService;

@RestController
@RequestMapping("/api")
public class EnfermedadApiController {
    
	private String STORAGEPATH;
	
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
			
			//Crear con imagenes
			
			@PostMapping("/insertar")
			public Enfermedad crear(@RequestParam(name="enfermedad_img", required=false) MultipartFile enfermedad_img, @RequestParam("nombre") String nombre, @RequestParam("sintomas") String sintomas, @RequestParam("apto") String apto, @RequestParam("medicamento_g") String medicamento_g, @RequestParam("medicamento_l") String medicamento_l, @RequestParam("medicamento_n") String medicamento_n, @RequestParam("medi_g") String medi_g, @RequestParam("medi_l") String medi_l, @RequestParam("medi_n") String medi_n) throws Exception {
				//logger.info("call crear(" + nombre + ", " + precio + ", " + detalles + ", " + imagen + ")");
				
				Enfermedad enfermedad = new Enfermedad();
				enfermedad.setNombre(nombre);
				enfermedad.setSintomas(sintomas);
				enfermedad.setApto(apto);
				enfermedad.setMedicamento_g(medicamento_g);
				enfermedad.setMedicamento_l(medicamento_l);
				enfermedad.setMedicamento_n(medicamento_n);
				enfermedad.setMedi_g(medi_g);
				enfermedad.setMedi_l(medi_l);
				enfermedad.setMedi_n(medi_n);
				
				if (enfermedad_img != null && !enfermedad_img.isEmpty()) {
					String filename = System.currentTimeMillis() + enfermedad_img.getOriginalFilename().substring(enfermedad_img.getOriginalFilename().lastIndexOf("."));
					enfermedad.setEnfermedad_img(filename);
					if(Files.notExists(Paths.get(STORAGEPATH))){
				        Files.createDirectories(Paths.get(STORAGEPATH));
				    }
					Files.copy(enfermedad_img.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
				}
				
				enfermedadService.save(enfermedad);
				
				return enfermedad;
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
