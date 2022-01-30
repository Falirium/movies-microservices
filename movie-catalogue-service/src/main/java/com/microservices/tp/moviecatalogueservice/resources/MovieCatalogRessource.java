package com.microservices.tp.moviecatalogueservice.resources;


import java.util.List;
import java.util.stream.Collectors;

import com.microservices.tp.moviecatalogueservice.models.CatalogItem;
import com.microservices.tp.moviecatalogueservice.models.Movie;

import com.microservices.tp.moviecatalogueservice.models.UserRating;
import com.microservices.tp.moviecatalogueservice.proxy.MovieInfoProxy;
import com.microservices.tp.moviecatalogueservice.proxy.UserRatingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogRessource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieInfoProxy movieInfoProxy;

    @Autowired
    private UserRatingProxy userRatingProxy;

    @RequestMapping("/{userId}")
    public List<CatalogItem>getCatalog(@PathVariable("userId") String userId) {
        
        UserRating ratings = userRatingProxy.getItemCatalog(userId);
        /*
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("1265", 4)//array of bunch of ratings
        );*/
        return ratings.getRatings().stream().map(rating -> {
                return movieInfoProxy.getItemCatalog(rating) ;
                })
                 .collect(Collectors.toList());

    }
}
