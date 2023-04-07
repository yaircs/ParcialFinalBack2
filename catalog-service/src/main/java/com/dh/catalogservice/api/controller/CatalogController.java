package com.dh.catalogservice.api.controller;

import com.dh.catalogservice.api.client.IMoviesServiceClient;
import com.dh.catalogservice.api.service.CatalogService;
import com.dh.catalogservice.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/catalog")
public class CatalogController {




    private final IMoviesServiceClient iMoviesServiceClient;

   // public CatalogController(CatalogService service) {
     //  this.service = service;
    //}
   public CatalogController(IMoviesServiceClient iMoviesServiceClient) {
       this.iMoviesServiceClient = iMoviesServiceClient;
   }
/*
    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getGenre(@PathVariable String genre) {

        return iMoviesServiceClient.getMovieByGenre(genre);
    }
*/
    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getGenre(@PathVariable String genre) {
        return ResponseEntity.ok(iMoviesServiceClient.getMovieByGenre(genre));
    }
}
