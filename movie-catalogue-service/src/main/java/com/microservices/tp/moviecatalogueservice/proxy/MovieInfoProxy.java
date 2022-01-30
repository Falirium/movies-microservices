package com.microservices.tp.moviecatalogueservice.proxy;

import com.microservices.tp.moviecatalogueservice.models.CatalogItem;
import com.microservices.tp.moviecatalogueservice.models.Movie;
import com.microservices.tp.moviecatalogueservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoProxy {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
    public CatalogItem getItemCatalog(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating()) ;
    }

    public CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Title is not found, the movie-info-service is down", "Overview is not found, the movie-info-service is down", rating.getRating());
    }
}