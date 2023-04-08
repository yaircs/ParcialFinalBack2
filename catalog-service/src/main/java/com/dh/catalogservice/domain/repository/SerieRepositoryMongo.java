package com.dh.catalogservice.domain.repository;

import com.dh.catalogservice.domain.model.Serie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SerieRepositoryMongo extends MongoRepository<Serie,String> {
}
