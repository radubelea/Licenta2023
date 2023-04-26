package com.licenta2023.backend.controller;

import com.licenta2023.backend.domain.Review;
import com.licenta2023.backend.domain.User;
import com.licenta2023.backend.domain.review.ReviewRequest;
import com.licenta2023.backend.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.licenta2023.backend.utils.JwtTokenUtil.getTokenPayloadAsObject;
import static com.licenta2023.backend.utils.JwtTokenUtil.isValid;

@CrossOrigin
@RestController()
public class ReviewController {

    private static final String REVIEW_ALREADY_EXISTS_ERROR_MESSAGE = "User already left a review on this movie!";
    private static final String SUCCESS_MESSAGE = "Success!";
    private static final String INVALID_ID_ERROR_MESSAGE = "Invalid id!";

    @Autowired
    private IReviewService reviewService;

    /**
     * url:    http://localhost:8080/licenta2023/review/create
     * <p>
     * Create a new Review with provided parameters
     *
     * @param request - the request object that needs to contain following fields
     *                rating, text, userId, movieId ,  as JSON object
     * @return -
     */
    @PostMapping(path = "review/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String token) {

        if(token == null || !isValid(token)){
            return new ResponseEntity<>("Authorization is required!", HttpStatus.FORBIDDEN);
        }
        User user = (User) getTokenPayloadAsObject(token);
        System.out.println(user.getID() + " " + request.getMovieId());
        //see if a review from the same user on the same movie already exists
        if (reviewService.findReviewByUserAndMovie(user.getID(), request.getMovieId()) != null) {
            return new ResponseEntity<>(REVIEW_ALREADY_EXISTS_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        System.out.println(reviewService.findReviewByUserAndMovie(user.getID(), request.getMovieId()));
        Review review = new Review(
                request.getRating(),
                request.getText(),
                user,
                request.getMovieId()
        );

        reviewService.save(review);
        System.out.println("Successfully added review from user" + user.getName());
        return new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK);

    }

    @GetMapping(path = "reviews/{movieId}")
    public ResponseEntity<?> getReviewsOfMovie(@PathVariable Integer movieId) {
        List<Review> reviews = (ArrayList<Review>) reviewService.getReviewsByMovie(movieId);
        if (reviews != null && reviews.size() > 0) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(INVALID_ID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "reviews/average/{movieId}")
    public ResponseEntity<?> getAverageScore(@PathVariable Integer movieId) {
        float avgScore =  reviewService.getAverageMovieScore(movieId);
        return new ResponseEntity<>(avgScore, HttpStatus.OK);
    }

    @DeleteMapping(path = "review/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        System.out.println("Id = " + id);
        Review r = reviewService.findOne(id);
        if (r != null) {
            reviewService.delete(id);
            return new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK);
        }
        return new ResponseEntity<>(INVALID_ID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

}
