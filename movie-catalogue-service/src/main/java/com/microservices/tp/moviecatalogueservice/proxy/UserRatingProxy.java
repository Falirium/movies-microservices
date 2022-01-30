package com.microservices.tp.moviecatalogueservice.proxy;

import java.util.Arrays;

import com.microservices.tp.moviecatalogueservice.models.Rating;
import com.microservices.tp.moviecatalogueservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRatingProxy {
    
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    public UserRating getItemCatalog(String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    public UserRating getFallBackUserRating(String userId) {
        UserRating userRating = new UserRating(userId, Arrays.asList(new Rating("8", 0)));
        return userRating;
    }
}