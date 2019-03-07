/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.controllers.ResourceNotFoundException;
import edu.eci.arsw.cinema.filter.Filter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class InMemoryCinemaPersistence implements CinemaPersitence {

    //private final Map<String, Cinema> cinemas = new HashMap<>();    
    private volatile ConcurrentHashMap<String, Cinema> cinemas = new ConcurrentHashMap<>();

    public InMemoryCinemaPersistence() {
        // load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("cinemaX", functions);
        cinemas.put("cinemaX", c);

        String functionDate2 = "2019-02-28 10:30";
        List<CinemaFunction> functions2 = new ArrayList<>();
        CinemaFunction funct3 = new CinemaFunction(new Movie("Zootopia", "Aventura"), functionDate2);
        CinemaFunction funct4 = new CinemaFunction(new Movie("La Propuesta", "Comedia"), functionDate2);
        functions2.add(funct3);
        functions2.add(funct4);
        Cinema c2 = new Cinema("cinemaPlazaAmericas", functions2);
        cinemas.put("cinemaPlazaAmericas", c2);

        String functionDate3 = "2019-12-14 11:45";
        List<CinemaFunction> functions3 = new ArrayList<>();
        CinemaFunction funct5 = new CinemaFunction(new Movie("Venom", "Action"), functionDate3);
        CinemaFunction funct6 = new CinemaFunction(new Movie("Frozen2", "Aventura"), functionDate3);
        functions3.add(funct5);
        functions3.add(funct6);
        Cinema c3 = new Cinema("cinemaSantaFe", functions3);
        cinemas.put("cinemaSantaFe", c3);
    }

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        List<CinemaFunction> funciones = cinemas.get(cinema).getFunctions();
        for (CinemaFunction cf : funciones) {
            if (cf.getDate().equals(date) && cf.getMovie().equals(movieName)) {
                cf.buyTicket(row, col);
            }
        }
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException {
        if (!cinemas.containsKey(cinema)) {
            throw new CinemaPersistenceException("El cine " + cinema + " no existe.");
        }
        List<CinemaFunction> funciones = cinemas.get(cinema).getFunctions();
        if (funciones.isEmpty()) {
            throw new CinemaPersistenceException("No existen funciones para el cine " + cinema);
        }
        List<CinemaFunction> funcionesCinemaDate = new ArrayList<>();
        for (CinemaFunction cf : funciones) {
            System.out.println("date parametro: " + date);
            String fecha = cf.getDate().substring(0, 10);
            if (fecha.equals(date.substring(0, 10))) {
                funcionesCinemaDate.add(cf);
            }
        }
        if (funcionesCinemaDate.isEmpty()) {
            throw new CinemaPersistenceException("No existen funciones para el cine " + cinema + " en la fecha " + date);
        }
        return funcionesCinemaDate;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())) {
            throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
        } else {
            cinemas.put(c.getName(), c);
        }
    }

    @Override
    public Cinema getCinemaByName(String name) throws CinemaPersistenceException {
        if (!cinemas.containsKey(name)) {
            throw new CinemaPersistenceException("The given cinema not exists: " + name);
        }
        return cinemas.get(name);
    }

    @Override
    public Map<String, Cinema> getCinemas() {
        return cinemas;
    }

    @Override
    public CinemaFunction getFunctionbyCinemaDateAndMovie(String cinema, String date, String movie) throws CinemaPersistenceException {
        CinemaFunction funcion = null;
        if (!cinemas.containsKey(cinema)) {
            throw new CinemaPersistenceException("El cine " + cinema + " no existe.");
        }
        List<CinemaFunction> funciones = cinemas.get(cinema).getFunctions();
        if (funciones.isEmpty()) {
            throw new CinemaPersistenceException("No existen funciones para el cine " + cinema);
        }
        for (CinemaFunction cf : funciones) {
            System.out.println("fecha bsuqueda: " + date);
            System.out.println("movi1: " + movie + " movie encontrada : " + cf.getMovie().getName());
            if (cf.getDate().equals(date) && cf.getMovie().getName().equals(movie)) {
                System.out.println("fecha encontrada: " + cf.getDate());
                funcion = cf;

            }
        }
        if (funcion == null) {
            throw new CinemaPersistenceException("No existe alguna funcion en el cine " + cinema + " en la fecha " + date + " que presente la película: " + movie);
        }
        return funcion;
    }

}
