/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos Medina - Amalia Alfonso
 */
public interface Filter {
    
    public List<Movie> filterBy(Cinema cinema1, String date, String filter) throws CinemaPersistenceException;
}
