package com.dh.catalogservice.api.event;

import com.dh.catalogservice.api.config.RabbitMQConfig;
import com.dh.catalogservice.domain.model.Movie;
import com.dh.catalogservice.domain.repository.MovieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
public class NewMovieEventConsumer {

    private final MovieRepositoryMongo movieRepositoryMongo;

    public NewMovieEventConsumer(MovieRepositoryMongo movieRepositoryMongo) {
        this.movieRepositoryMongo = movieRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void execute(NewMovieEventConsumer.Data data) {
        Movie movieNew = new  Movie();
        BeanUtils.copyProperties(data.getMovieDto(), movieNew);
        movieRepositoryMongo.deleteById(data.getMovieDto().getId());
        movieRepositoryMongo.save(movieNew);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        private MovieDto movieDto = new MovieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor

        public static class MovieDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;


            private Long id;
            private String name;
            private String genre;
            private String urlStream;

        }

    }

}
