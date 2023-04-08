package com.dh.catalogservice.api.event;

import com.dh.catalogservice.api.config.RabbitMQConfig;
import com.dh.catalogservice.domain.model.Serie;
import com.dh.catalogservice.domain.repository.SerieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSeriesEventConsumer {

    private final SerieRepositoryMongo serieRepositoryMongo;

    public NewSeriesEventConsumer(SerieRepositoryMongo serieRepositoryMongo) {
        this.serieRepositoryMongo = serieRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void execute(NewSeriesEventConsumer.Data data) {
        Serie serieNew = new Serie();
        BeanUtils.copyProperties(data.getSerieDto(), serieNew);
        serieRepositoryMongo.deleteById(data.getSerieDto().id);
        serieRepositoryMongo.save(serieNew);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private SerieDto serieDto = new SerieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor

        public static class SerieDto {

            private String id;
            private String name;
            private String genre;
            private List<SeasonDto> seasons = new ArrayList<>();

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SeasonDto {

            private String id;
            private Integer seasonNumber;
            private String genre;
            private List<ChapterDto> chapters = new ArrayList<>();

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ChapterDto {

            private String id;
            private String name;
            private Integer number;
            private String urlStream;

        }
    }


}
