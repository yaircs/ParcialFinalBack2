package com.dh.catalogservice.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private Long id;

    private String name;

    private String genre;

    private String urlStream;
}
