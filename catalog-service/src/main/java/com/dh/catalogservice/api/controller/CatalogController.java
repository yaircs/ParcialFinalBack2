package com.dh.catalogservice.api.controller;

import com.dh.catalogservice.api.client.IMoviesServiceClient;
import com.dh.catalogservice.api.service.CatalogService;
import com.dh.catalogservice.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/catalog")
public class CatalogController {




   // private final IMoviesServiceClient iMoviesServiceClient;
    private final CatalogService catalogService;

   // public CatalogController(CatalogService service) {
     //  this.service = service;
    //}
   public CatalogController(IMoviesServiceClient iMoviesServiceClient, CatalogService catalogService) {
       this.catalogService = catalogService;
   }
/*
    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getGenre(@PathVariable String genre) {

        return iMoviesServiceClient.getMovieByGenre(genre);
    }
*/

    //online
    @GetMapping("/{genre}")
    public ResponseEntity<GetCatalogByGenreResponse> getCatalogByGenreResponseOnline(@PathVariable String genre) {
        return ResponseEntity.ok(catalogService.getCatalogByGenreResponseOnline(genre));
    }

    //offline
    @GetMapping("/offline/{genre}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GetCatalogByGenreResponse> getCatalogByGenreResponseOffline(@PathVariable String genre) {
        return ResponseEntity.ok(catalogService.getCatalogByGenreResponseOffline(genre));
    }
}
