/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.ui;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author 2125262
 */
public class Main {
	/*
    public static void main(String a[]) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cs = ac.getBean(CinemaServices.class);
                
        String functionDate1 = "2019-2-18 10:30";
        String functionDate2 = "2019-2-19 19:30";
        List<CinemaFunction> functions = new ArrayList<>();
        Movie movie1 = new Movie("SuperHeroes Movie 2", "Aventura");
        Movie movie2 = new Movie("The Night 2", "Horror");
        Movie movie3 = new Movie("Frozen", "Aventura");
        Movie movie4 = new Movie("Anabelle", "Horror");        
        Movie movie5 = new Movie("El despertar de Amalia", "Ficción");
        Movie movie6 = new Movie("Pikachu", "Animación"); 
        CinemaFunction funct1 = new CinemaFunction(movie5, functionDate1);
        CinemaFunction funct2 = new CinemaFunction(movie6, functionDate2);
        CinemaFunction funct3 = new CinemaFunction(movie1, functionDate1);
        CinemaFunction funct4 = new CinemaFunction(movie2, functionDate1);
        CinemaFunction funct5 = new CinemaFunction(movie3, functionDate1);
        CinemaFunction funct6 = new CinemaFunction(movie4, functionDate1);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        functions.add(funct4);
        functions.add(funct5);
        functions.add(funct6);       
        Cinema c = new Cinema("Movie Plutón", functions);
        try {
            //Registrar un cinema.
            System.out.println("--------------- Registrar un cinema ---------------");
            cs.addNewCinema(c);
            System.out.println(cs.getCinemaByName("Movie Plutón").getName());
            //Consultar cinemas.
            System.out.println("--------------- Consultar cinemas ---------------");
            Map<String, Cinema> cinemas = cs.getAllCinemas();
            for (String ci : cinemas.keySet()) {
                System.out.println(cinemas.get(ci).getName());  
            }            
            //Obtener las funciones de un cinema.
            System.out.println("--------------- Obtener funciones de un cinema por la fecha, ---------------");
            List<CinemaFunction> cf = cs.getFunctionsbyCinemaAndDate("Movie Plutón", functionDate1);
            for (CinemaFunction cinemaFunc : cf) {
                System.out.println("Movie: "+cinemaFunc.getMovie().getName()+" - Date: "+cinemaFunc.getDate());  
            } 
            cf = cs.getFunctionsbyCinemaAndDate("Movie Plutón", functionDate2);
            for (CinemaFunction cinemaFunc : cf) {
                System.out.println("Movie: "+cinemaFunc.getMovie().getName()+" - Date: "+cinemaFunc.getDate());
            }
            //Comprar o reservas un ticket
            System.out.println("--------------- Comprar tiquetes ---------------");
            cs.buyTicket(0, 0, "Movie Plutón", functionDate1, "El despertar de Amalia");
            cs.buyTicket(0, 1, "Movie Plutón", functionDate1, "El despertar de Amalia");
            System.out.println("----> Compra exitosa.");
            //Filtrar por género
            /*
            System.out.println("--------------- Filtro por género ---------------");
            List<Movie> moviesEsperdas = Arrays.asList(movie1, movie3);
            List<Movie> moviesRta = new ArrayList<>();            
            moviesRta = cs.getFilmsFilter("Movie Plutón", functionDate1, "Aventura");
            for (Movie m : moviesRta) {
                if (!moviesEsperdas.contains(m)) {
                    System.out.println("Las películas no se filtraron correctamente.");
                } else {
                    System.out.println("Movie: "+m.getName());
                }
            }            
            //Filtrar por disponibilidad
            System.out.println("--------------- Filtro por disponibilidad ---------------");
            List<Movie> moviesEsperdas2 = Arrays.asList(movie3, movie4, movie5);            
            funct3.buyTicket(1, 1);
            funct3.buyTicket(0, 2);
            funct4.buyTicket(0, 11);
            funct4.buyTicket(6, 11);            
            List<Movie> moviesRta2 = cs.getFilmsFilter("Movie Plutón", functionDate1, "82");            
            for (Movie m : moviesRta2) {
                if (!moviesEsperdas2.contains(m)) {
                    System.out.println("Las películas no se filtraron correctamente.");
                } else {
                    System.out.println("Movie: "+m.getName());
                }
            }
        } catch (CinemaPersistenceException ex) {
            
            System.out.println(ex.getMessage());
        } catch (CinemaException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
	*/
    
}
