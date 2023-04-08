package com.dh.catalogservice.domain.repository;

import com.dh.catalogservice.domain.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepositoryMongo extends MongoRepository<Movie,Long> {
}
