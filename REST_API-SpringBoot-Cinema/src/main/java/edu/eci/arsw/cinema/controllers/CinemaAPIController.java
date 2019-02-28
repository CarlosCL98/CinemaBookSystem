/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cristian
 */
@RestController // 1
@RequestMapping(value = "/cinema") // 2
public class CinemaAPIController {

	@Autowired
	private CinemaServices cinemaServices;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Cinema>> manejadorGetRecursoCinemas() {
		List<Cinema> data = cinemaServices.getAllCinemasList();
		return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<Cinema> manejadorGetRecursoCinema(@PathVariable String name) throws ResourceNotFoundException {
		Cinema data;
		ResponseEntity<Cinema> respose;
		try {
			data = cinemaServices.getCinemaByName(name);
			respose = new ResponseEntity<>(data, HttpStatus.ACCEPTED);
		} catch (CinemaPersistenceException ex) {
			throw new ResourceNotFoundException(ex.getMessage());
		}
		return respose;
	}

}

/*
 * 1. Hace lo mismo que @Controller (hace que detecten las clases de
 * implementación automáticamente y necesita de response body para serializar
 * los objetos de retorno en el HTTPResponse) y no necesita de @ResponseBody,
 * pues lo serializa automáticamente.
 * 
 * 2. Es el valor del recurso que se va a traer. Es la URL raíz del recurso.
 */
