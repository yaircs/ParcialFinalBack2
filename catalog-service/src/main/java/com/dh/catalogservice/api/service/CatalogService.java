package com.dh.catalogservice.api.service;

import com.dh.catalogservice.api.client.IMoviesServiceClient;
import com.dh.catalogservice.api.client.ISerieServiceClient;
import com.dh.catalogservice.api.controller.GetCatalogByGenreResponse;
import com.dh.catalogservice.domain.model.Movie;
import com.dh.catalogservice.domain.repository.MovieRepositoryMongo;
import com.dh.catalogservice.domain.repository.SerieRepositoryMongo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    public static Logger LOG = LoggerFactory.getLogger(CatalogService.class);
    private final MovieRepositoryMongo movieRepositoryMongo;
    private final SerieRepositoryMongo serieRepositoryMongo;
    private final IMoviesServiceClient iMoviesServiceClient;
    private final ISerieServiceClient iSerieServiceClient;


    public CatalogService(MovieRepositoryMongo movieRepositoryMongo, SerieRepositoryMongo serieRepositoryMongo, IMoviesServiceClient iMoviesServiceClient, ISerieServiceClient iSerieServiceClient) {
        this.movieRepositoryMongo = movieRepositoryMongo;
        this.serieRepositoryMongo = serieRepositoryMongo;
        this.iMoviesServiceClient = iMoviesServiceClient;
        this.iSerieServiceClient = iSerieServiceClient;
    }

    @Retry(name = "retryCatalog")
    @CircuitBreaker(name = "clientCatalog", fallbackMethod = "getCatalogByGenreFallBack")
    public GetCatalogByGenreResponse getCatalogByGenreResponseOnline(String genre) {
        LOG.info("Se busca el catalogo por tipo de genero: " + genre);
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        response.setMovies(iMoviesServiceClient.getMovieByGenre(genre));
        response.setSeries(iSerieServiceClient.getSerieByGenre(genre));
        LOG.info("La busqueda fue exitosa para el tipo de genero" + genre);
        return response;
    }

    /* Si falla getCatalogByGenreResponseOnline ejecutamos getCatalogByGenreFallBack que no hace un llamado a los
    microservicios api-movie ni api-series si no que directamente trae la respuesta de la Base de datos en Mongo */
    public GetCatalogByGenreResponse getCatalogByGenreFallBack(String genre, Throwable t) {
        LOG.info("se ejecuta el metodo getCatalogByGenreFallBack");
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        var movieFilter = movieRepositoryMongo.findAll().stream().filter(movie -> genre.equals(movie.getGenre())).collect(Collectors.toList());
        response.setMovies(movieFilter);
        var serieFilter = serieRepositoryMongo.findAll().stream().filter(serie -> genre.equals(serie.getGenre())).collect(Collectors.toList());
        response.setSeries(serieFilter);
        return response;

    }


    public GetCatalogByGenreResponse getCatalogByGenreResponseOffline(String genre) {
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        var movieFilter = movieRepositoryMongo.findAll().stream().filter(movie -> genre.equals(movie.getGenre())).collect(Collectors.toList());
        response.setMovies(movieFilter);

        var serieFilter = serieRepositoryMongo.findAll().stream().filter(serie -> genre.equals(serie.getGenre())).collect(Collectors.toList());
        response.setSeries(serieFilter);
        return response;
    }



    // private final RestTemplate clienteRest;

    //public CatalogService(RestTemplate clienteRest) {
    //    this.clienteRest = clienteRest;
   // }

    //@Override
    //public List<Movie> getMovieByGenre(String genre) {

      //  var url = String.format("http://localhost:8001/movies/%s", genre);

        //var response = clienteRest.exchange(url, HttpMethod.GET,
          //      null,
            //    new ParameterizedTypeReference<List<Movie>>() {
              //  });

        //return Objects.requireNonNull(response.getBody());
    //}
}
