package com.jvilchez.aplicacion.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jvilchez.aplicacion.entity.Enfermedad;
import com.jvilchez.aplicacion.repository.EnfermedadRepository;
import com.jvilchez.aplicacion.service.EnfermedadService;

@Controller
@RequestMapping("/enfermedades")
public class EnfermedadController {
	
	private static String UPLOADED_FOLDER = "src/main/resources/static/img/enfermedades/";
		 
	    @Autowired
	    private EnfermedadRepository uc;
	    
	    @Autowired
		EnfermedadService enfermedadService;

	 
	    @RequestMapping(value="lista", method = RequestMethod.GET)// se añada or defecto "/crud"
	    public String listaEnfermedades(ModelMap mp){
	        mp.put("enfermedades", uc.findAll());
	        return "enfermedades/lista";
	    }
	    
	    @RequestMapping(value="listauser", method = RequestMethod.GET)// se añada or defecto "/crud"
	    public String listauser(ModelMap mp){
	        mp.put("enfermedades", uc.findAll());
	        return "user-comun/inicio_user";//img
	    }
	 
	    @RequestMapping(value="/nuevo", method=RequestMethod.GET)
	    public String nuevo(ModelMap mp){
	        mp.put("enfermedad", new Enfermedad());
	        return "enfermedades/nuevo";//vista
	    } 
	    	     
	 
	    @RequestMapping(value="/crear", method=RequestMethod.POST)//hace el insert y se mantiene en crear
	    public String crear(@Valid Enfermedad enfermedad,
	            BindingResult bindingResult, ModelMap mp){
	        if(bindingResult.hasErrors()){
	            return "/enfermedades/nuevo";
	        }else{
	            uc.save(enfermedad);
	            mp.put("enfermedad", enfermedad);
	            return "enfermedades/creado";//modificar el tipo de mape0
	        }
	    }
	 
	    @RequestMapping(value="/creado", method = RequestMethod.POST)
	    public String creado(@RequestParam("enfermedad") Enfermedad enfermedad){
	    	
	        return "/enfermedades/creado";
	    }
	    
	    
	    @PostMapping("/upload") // //new annotation since 4.3
	    public String singleFileUpload(@RequestParam("file") MultipartFile file,
	                                   RedirectAttributes redirectAttributes) {

	        if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return "user-comun/uploadStatus";
	        }

	        try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	            redirectAttributes.addFlashAttribute("message",
	                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "enfermedades/lista";
	    }
	    
	    @GetMapping("/uploadStatus")
	    public String uploadStatus() {
	        return "user-comun/uploadStatus";
	    }
	    
	    
	    
	    
	    @RequestMapping(value="/borrar/{id}", method=RequestMethod.GET)
	    public String borrar(@PathVariable("id") long id, ModelMap mp){
	        uc.deleteById(id);
	        mp.put("enfermedades", uc.findAll());
	        return "enfermedades/lista";
	    }
	    
	    
	    @RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	    public String editar(@PathVariable("id") long id, ModelMap mp){
	        mp.put("enfermedad", uc.findById(id));
	        return "enfermedades/editar";
	    }
	     
	    @RequestMapping(value="/actualizar", method=RequestMethod.POST)
	    public String actualizar(@Valid Enfermedad enfermedad, BindingResult bindingResult, ModelMap mp){
	        if(bindingResult.hasErrors()){
	            mp.put("enfermedades", uc.findAll());
	        return "enfermedades/lista";
	        }
	        Enfermedad enf = uc.findById(enfermedad.getId()).orElse(null);
	        enf.setNombre(enfermedad.getNombre());
	        enf.setMedicamento_g(enfermedad.getMedicamento_g());
	        enf.setMedicamento_l(enfermedad.getMedicamento_l());
	        enf.setMedicamento_n(enfermedad.getMedicamento_n());
	        enf.setSintomas(enfermedad.getSintomas());
	        enf.setApto(enfermedad.getApto());
	        enf.setEnfermedad_img(enfermedad.getEnfermedad_img());
	        uc.save(enf);
	        mp.put("enfermedad", enf);
	        return "enfermedades/actualizado";
	    }    
	    
}