package com.jvilchez.aplicacion.repository;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;


import com.jvilchez.aplicacion.entity.User;


@Repository  
public class AuthenticationRepository {

private static final Logger logger = LoggerFactory.getLogger(AuthenticationRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User login(String correo, String clave) throws BadCredentialsException {
		logger.info("login("+correo+", "+clave+")");
		
		String sql = "select count(*) from  user where correo=? and clave=?";
		Integer exists=0;
		exists = jdbcTemplate.queryForObject(sql, Integer.class, correo, clave);
		
		if(exists == 0) {
			throw new BadCredentialsException("Correo y/o clave invalido");
		}
		
		return findByCorreo(correo); 
	}
	
	public User findByCorreo(String correo) throws EmptyResultDataAccessException {
		//logger.info("findByCorreo("+correo+")");
		
		String sql = "select * from user where correo=?";
		
		User user = jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				//user.setId(rs.getInt("id"));
				user.setCorreo(rs.getString("correo"));
				user.setNombres(rs.getString("nombres"));
				user.setApellidos(rs.getString("apellidos"));
				user.setDni(rs.getString("dni"));
				user.setEdad(rs.getString("edad"));
				user.setAlergia(rs.getString("alergia"));
				user.setEstado(rs.getString("estado"));
				
				
				return user;
			} 
		}, correo);
		
		//logger.info("user: " + user);
		
		return user;
	}

}
