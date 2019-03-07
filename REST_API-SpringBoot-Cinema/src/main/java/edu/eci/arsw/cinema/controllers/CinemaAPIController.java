package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cristian
 */
@RestController // 1
@RequestMapping(value = "/cinemas") // 2
public class CinemaAPIController {

    @Autowired
    private CinemaServices cinemaServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cinema>> manejadorGetRecursoCinemas() {
        List<Cinema> data = cinemaServices.getAllCinemasList();
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Cinema> manejadorGetRecursoCinemaPorNombre(@PathVariable String name)
            throws ResourceNotFoundException {
        Cinema data;
        try {
            data = cinemaServices.getCinemaByName(name);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{name}/{date}", method = RequestMethod.GET)
    public ResponseEntity<List<CinemaFunction>> manejadorGetRecursoCinemaPorNombreFecha(@PathVariable String name,
            @PathVariable String date) throws ResourceNotFoundException {
        List<CinemaFunction> data;
        try {
            data = cinemaServices.getFunctionsbyCinemaAndDate(name, date);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{name}/{date}/{moviename}", method = RequestMethod.GET)
    public ResponseEntity<CinemaFunction> manejadorGetRecursoCinemaPorNombreFechaYPelicula(@PathVariable String name,
            @PathVariable String date, @PathVariable String moviename) throws ResourceNotFoundException {
        CinemaFunction data;
        try {
            data = cinemaServices.getFunctionbyCinemaDateAndMovie(name, date, moviename);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity<CinemaFunction> manejadorPostRecursoFuncion(@RequestBody CinemaFunction funcion, @PathVariable String name) throws ResourceNotFoundException {
        try {
            Cinema cinema = cinemaServices.getCinemaByName(name);
            List<CinemaFunction> funciones = cinema.getFunctions();
            funciones.add(funcion);
            cinema.setSchedule(funciones);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> manejadorPutRecursoFuncion(@RequestBody Cinema cinemaR, @PathVariable String name) throws ResourceNotFoundException {
        try {
            Cinema cinema = cinemaServices.getCinemaByName(name);
            cinema.setSchedule(cinemaR.getFunctions());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            try {
                cinemaServices.addNewCinema(cinemaR);
            } catch (CinemaException ex1) {
                //Esto nunca va a pasar, porque ya se ha verificado que el cine no existe.
                return new ResponseEntity<>(ex1.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}

/*
 * 1. Hace lo mismo que @Controller (hace que detecten las clases de
 * implementación automáticamente y necesita de response body para serializar
 * los objetos de retorno en el HTTPResponse) y no necesita de @ResponseBody,
 * pues lo serializa automáticamente.
 * 
 * 2. Es el valor del recurso que se va a traer. Es la URL raíz del recurso.
 * 
 * curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/cinemas/cineNoExiste -d '{"movie":{"name":"Venom","genre":"Action"},"seats":[[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true]],"date":"2019-03-20 05:20"}'
 */
