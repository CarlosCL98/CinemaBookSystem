package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Medina - Amalia Alfonso
 */
//@Service
public class GenreFilter implements Filter{
    
    public GenreFilter() {}

    @Override
    public List<Movie> filterBy(Cinema cinema, String date, String filter) throws CinemaPersistenceException {    	
        List<CinemaFunction> cfs = cinema.getFunctions();      
        List<Movie> movies = new ArrayList<>();
        Movie m = null;
        for (CinemaFunction cf : cfs) {
            m = cf.getMovie();
            if (cf.getDate().equals(date) && m.getGenre().equals(filter)) {            	
                movies.add(m);
            }
        }
        return movies;
    }
    
}
