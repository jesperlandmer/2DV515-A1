package com.lnu.http.jp222vqa1.resource;

import com.lnu.http.jp222vqa1.model.models.Movie;
import com.lnu.http.jp222vqa1.model.RecommendationFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class ApiResource {

    private String message = "Recommendation System API";

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public ResponseEntity<ResourceSupport> getAll() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(ApiResource.class).getAll()).withSelfRel());
        resource.add(linkTo(methodOn(ApiResource.class)
                .getUserBasedOpts())
                .withRel("user-based")
                .withTitle("Get user-based recommendations"));
        resource.add(linkTo(methodOn(ApiResource.class)
                .getItemBasedOpts())
                .withRel("item-based")
                .withTitle("Get item-based recommendations"));
        return new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/ub", method = RequestMethod.GET)
    public ResponseEntity<ResourceSupport> getUserBasedOpts() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(ApiResource.class)
                .getAll())
                .withSelfRel()
                .withTitle("user-based"));
        resource.add(linkTo(methodOn(ApiResource.class)
                .getUserBasedEuclideanMovieRec("userName"))
                .withRel("euclidean")
                .withTitle("Get movie recommendations for user using Euclidean"));
        resource.add(linkTo(methodOn(ApiResource.class)
                .getUserBasedPearsonMovieRec("userName"))
                .withRel("pearson")
                .withTitle("Get movie recommendations for user using Pearson"));
        return new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/ib", method = RequestMethod.GET)
    public ResponseEntity<ResourceSupport> getItemBasedOpts() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(ApiResource.class)
                .getAll())
                .withSelfRel()
                .withTitle("item-based"));
        resource.add(linkTo(methodOn(ApiResource.class)
                .getItemBasedEuclideanMovieRec("userName"))
                .withRel("euclidean")
                .withTitle("Get movie recommendations for user using Euclidean"));
        resource.add(linkTo(methodOn(ApiResource.class)
                .pregenerateEuclideanItemBasedData())
                .withRel("pregenerate")
                .withTitle("Generate data for item-based recommendations using Euclidean"));
        return new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/ub/euclidean", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public List<Movie> getUserBasedEuclideanMovieRec(@RequestParam(value = "_user") String user) {
        return new RecommendationFactory().getEuclideanUserBasedRecommendation(user);
    }

    @RequestMapping(value = "/api/ub/pearson", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public List<Movie> getUserBasedPearsonMovieRec(@RequestParam(value = "_user") String user) {
        return new RecommendationFactory().getPearsonUserBasedRecommendation(user);
    }

    @RequestMapping(value = "/api/ib/euclidean", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public List<Movie> getItemBasedEuclideanMovieRec(@RequestParam(value = "_user") String user) {
        return new RecommendationFactory().getItemBasedRecommendation(user);
    }

    @RequestMapping(value = "/api/ib/pregenerate", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public List<String> pregenerateEuclideanItemBasedData() {
        new RecommendationFactory().pregenerateEuclideanItemCollab();
        List<String> res = new ArrayList<String>();
        res.add("Data generated");
        return res;
    }
}