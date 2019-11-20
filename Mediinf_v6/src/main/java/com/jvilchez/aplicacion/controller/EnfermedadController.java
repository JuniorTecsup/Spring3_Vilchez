package com.jvilchez.aplicacion.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvilchez.aplicacion.entity.Enfermedad;
import com.jvilchez.aplicacion.repository.EnfermedadRepository;

@Controller
@RequestMapping("/enfermedades")
public class EnfermedadController {
		 
	    @Autowired
	    private EnfermedadRepository uc;

	 
	    @RequestMapping(value="lista", method = RequestMethod.GET)// se añada or defecto "/crud"
	    public String listaEnfermedades(ModelMap mp){
	        mp.put("enfermedades", uc.findAll());
	        return "enfermedades/lista";
	    }
	 
	    @RequestMapping(value="/nuevo", method=RequestMethod.GET)
	    public String nuevo(ModelMap mp){
	        mp.put("enfermedad", new Enfermedad());
	        return "enfermedades/nuevo";
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
	        Enfermedad user = uc.findById(enfermedad.getId()).orElse(null);
	        user.setNombre(enfermedad.getNombre());
	        user.setMedicamento_g(enfermedad.getMedicamento_g());
	        user.setMedicamento_l(enfermedad.getMedicamento_l());
	        user.setMedicamento_n(enfermedad.getMedicamento_n());
	        user.setSintomas(enfermedad.getSintomas());
	        user.setApto(enfermedad.getApto());
	        uc.save(user);
	        mp.put("enfermedad", user);
	        return "enfermedades/actualizado";
	    }    
	    
}