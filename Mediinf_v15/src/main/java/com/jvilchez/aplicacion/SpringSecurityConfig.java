package com.jvilchez.aplicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;//añd
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;//añad
import com.jvilchez.aplicacion.service.UserDetailsServiceImpl;//añd

import com.jvilchez.aplicacion.filters.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
	String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
//,"/signup","/enfermedades/lista","/enfermedades/nuevo","/enfermedades/crear","/enfermedades/editar/{id}","/enfermedades/actualizar","/enfermedades/borrar/{id}","/api/enfermedades","/api/enfermedad/{id}","/api/enfermedad/crear","/api/enfermedad/eliminar/{id}","/api/enfermedad/editar/{id}"	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .authorizeRequests()
	        .antMatchers(resources).permitAll()
	        .antMatchers("/" /*, "/**"*/).permitAll()
	        .antMatchers("/","/index").permitAll()
	        .antMatchers("/auth/login").permitAll()//Aqui todo mi trabajo
	        .and().csrf().disable()
	        
	        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/userForm")
            .failureUrl("/login?error=true")
            .usernameParameter("correo")
            .passwordParameter("clave")//rara exception..Clave 
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            .logoutSuccessUrl("/login?logout");
    

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter("/api/**");
    }
    
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
//ya funciona apis enfer y logeo
}

