package com.dh.movieservice.api.service;

import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.repository.MovieRepository;
import com.dh.movieservice.event.NewMovieEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final NewMovieEventProducer newMovieEventProducer;

    public MovieService(MovieRepository repository, NewMovieEventProducer newMovieEventProducer) {
        this.repository = repository;
        this.newMovieEventProducer = newMovieEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return repository.findByGenre(genre);
    }

    public void save(Movie movie) {
        repository.save(movie);
        newMovieEventProducer.execute(movie);

    }
}
