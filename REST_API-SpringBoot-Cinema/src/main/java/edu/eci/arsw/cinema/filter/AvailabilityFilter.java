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
@Service
public class AvailabilityFilter implements Filter {

    @Override
    public List<Movie> filterBy(Cinema cinema1, String date, String filter) throws CinemaPersistenceException {
        List<CinemaFunction> cfs = cinema1.getFunctions();
        List<Movie> movies = new ArrayList<>();
        List<List<Boolean>> seats;
        int numeroEmptySeats = 0;
        for (CinemaFunction cf : cfs) {
            if (cf.getDate().equals(date)) {
                seats = cf.getSeats();
                for (int row = 0; row < seats.size(); row++) {
                    for (int col = 0; col < seats.get(0).size(); col++) {
                        if (seats.get(row).get(col).equals(true)) {
                            numeroEmptySeats++;
                        }
                    }
                }
                if (numeroEmptySeats > Integer.parseInt(filter)) {
                    movies.add(cf.getMovie());
                }
                numeroEmptySeats = 0;
            }
        }
        return movies;
    }

}
