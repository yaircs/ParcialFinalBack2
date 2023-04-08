package com.dh.movieservice.event;

import com.dh.movieservice.config.RabbitMQConfig;
import com.dh.movieservice.domain.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class NewMovieEventProducer {

    private final RabbitTemplate rabbitTemplate;


    public NewMovieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void execute(Movie movie) {
        NewMovieEventProducer.Data data = new NewMovieEventProducer.Data();
        BeanUtils.copyProperties(movie, data.getMovieDto());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_MOVIE, data);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private MovieDto movieDto = new MovieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor

        public static class MovieDto {

            private Long id;
            private String name;
            private String genre;
            private String urlStream;

        }

    }
}
