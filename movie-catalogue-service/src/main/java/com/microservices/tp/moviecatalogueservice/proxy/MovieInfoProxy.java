package com.microservices.tp.moviecatalogueservice.proxy;

import com.microservices.tp.moviecatalogueservice.models.CatalogItem;
import com.microservices.tp.moviecatalogueservice.models.Movie;
import com.microservices.tp.moviecatalogueservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoProxy {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        }
    )
    public CatalogItem getItemCatalog(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating()) ;
    }

    public CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Title is not found, the movie-info-service is down", "Overview is not found, the movie-info-service is down", rating.getRating());
    }
}
