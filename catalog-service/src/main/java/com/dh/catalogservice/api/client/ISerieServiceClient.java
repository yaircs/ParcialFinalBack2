package com.dh.catalogservice.api.client;

import com.dh.catalogservice.domain.model.Serie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="serie-service")
public interface ISerieServiceClient {
    @GetMapping("/api/v1/series/{genre}")
    List<Serie> getSerieByGenre(@PathVariable(value = "genre") String genre);

}
