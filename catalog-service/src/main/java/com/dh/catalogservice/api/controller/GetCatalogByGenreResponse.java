package com.dh.catalogservice.api.controller;

import com.dh.catalogservice.domain.model.Movie;
import com.dh.catalogservice.domain.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCatalogByGenreResponse {
    private String genre;
    private List<Movie> movies =new ArrayList<>() ;
    private List<Serie> series= new ArrayList<>();
}
