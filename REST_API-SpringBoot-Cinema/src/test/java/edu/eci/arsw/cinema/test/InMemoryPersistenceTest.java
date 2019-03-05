package edu.eci.arsw.cinema.test;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import edu.eci.arsw.cinema.filter.AvailabilityFilter;
import edu.eci.arsw.cinema.filter.GenreFilter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws CinemaPersistenceException {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);
        ipct.saveCinema(c);
        
        assertNotNull("Loading a previously stored cinema returned null.", ipct.getCinemaByName(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.", ipct.getCinemaByName(c.getName()), c);
    }
    
    @Test
    public void saveExistingCinemaTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        
        List<CinemaFunction> functions2 = new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3", "Action"), functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3", "Horror"), functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2 = new Cinema("Movies Bogotá", functions2);
        try {
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        } catch (CinemaPersistenceException ex) {
        }
    }
    
    @Test
    public void deberiaComprarUnTicketTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            ipct.buyTicket(0, 0, "Movies Bogotá", functionDate, "SuperHeroes Movie 2");
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        } catch (CinemaException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Test
    public void noDeberiaComprarUnTicketSiAlguienYaLoReservoTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            ipct.buyTicket(0, 0, "Movies Bogotá", functionDate, "SuperHeroes Movie 2");
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        } catch (CinemaException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            ipct.buyTicket(0, 0, "Movies Bogotá", functionDate, "SuperHeroes Movie 2");
        } catch (CinemaException ex) {
            assertEquals("Seat booked", ex.getMessage());
        }
    }
    
    @Test
    public void deberiaObtenerLasFuncionesPorElCinemaYLaFechaTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("Frozen", "Aventura"), functionDate);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Anabelle", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        functions.add(funct4);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        List<CinemaFunction> funcionesExtraidas = new ArrayList<>();
        try {
            ipct.saveCinema(c);
            funcionesExtraidas = ipct.getFunctionsbyCinemaAndDate("Movies Bogotá", functionDate);
            if (funcionesExtraidas.size() == functions.size()) {
                for (CinemaFunction cf : functions) {
                    if (!funcionesExtraidas.contains(cf)) {
                        fail("La función " + cf.toString() + " no se encuentra.");
                    }
                }
            } else {
                fail("No hay funciones en el cinema " + c.getName());
            }
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
    }
    
    @Test
    public void deberiaObtenerUnCinemaPorElNombreTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("Frozen", "Aventura"), functionDate);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Anabelle", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        functions.add(funct4);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            Cinema cinemaObtenido = ipct.getCinemaByName("Movies Bogotá");
            assertEquals(c.getName(), cinemaObtenido.getName());
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
    }
    /*
    @Test
    public void deberiaFiltrarLasPeliculasPorGenero() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        ipct.setFiltro(new GenreFilter());
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions1 = new ArrayList<>();
        Movie movie1 = new Movie("SuperHeroes Movie 2", "Aventura");
        Movie movie2 = new Movie("The Night 2", "Horror");
        Movie movie3 = new Movie("Frozen", "Aventura");
        Movie movie4 = new Movie("Anabelle", "Horror");
        CinemaFunction funct3 = new CinemaFunction(movie1, functionDate);
        CinemaFunction funct4 = new CinemaFunction(movie2, functionDate);
        CinemaFunction funct5 = new CinemaFunction(movie3, functionDate);
        CinemaFunction funct6 = new CinemaFunction(movie4, functionDate);
        functions1.add(funct3);
        functions1.add(funct4);
        functions1.add(funct5);
        functions1.add(funct6);
        Cinema c1 = new Cinema("Movies Bogotá", functions1);
        
        List<Movie> moviesEsperdas = Arrays.asList(movie1, movie3);
        List<Movie> moviesRta = new ArrayList<>();
        
        try {
            ipct.saveCinema(c1);            
            moviesRta = ipct.filterBy("Movies Bogotá", functionDate, "Aventura");
            for (Movie m : moviesRta) {
                if (!moviesEsperdas.contains(m)) {
                    fail("Las películas no se filtraron correctamente.");
                }
            }            
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
    }
    
    @Test
    public void deberiaFiltrarLasPeliculasPorDisponibilidad() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
        ipct.setFiltro(new AvailabilityFilter());
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions1 = new ArrayList<>();
        Movie movie1 = new Movie("SuperHeroes Movie 2", "Aventura");
        Movie movie2 = new Movie("The Night 2", "Horror");
        Movie movie3 = new Movie("Frozen", "Aventura");
        CinemaFunction funct3 = new CinemaFunction(movie1, functionDate);        
        CinemaFunction funct4 = new CinemaFunction(movie2, functionDate);
        CinemaFunction funct5 = new CinemaFunction(movie3, functionDate);
        functions1.add(funct3);
        functions1.add(funct4);
        functions1.add(funct5);
        Cinema c1 = new Cinema("Movies Bogotá", functions1);
        
        List<Movie> moviesEsperdas = Arrays.asList(movie1, movie2, movie3);
        List<Movie> moviesRta = new ArrayList<>();
        
        try {
            ipct.saveCinema(c1);            
            funct3.buyTicket(1, 1);
            funct3.buyTicket(0, 2);
            funct4.buyTicket(0, 11);
            funct4.buyTicket(6, 11);
            
            moviesRta = ipct.filterBy("Movies Bogotá", functionDate, "81");            
            for (Movie m : moviesRta) {
                if (!moviesEsperdas.contains(m)) {
                    fail("Las películas no se filtraron correctamente.");
                }
            }
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        } catch (CinemaException ex) {
            System.out.println(ex.getMessage());
        }
    }
    */
}
