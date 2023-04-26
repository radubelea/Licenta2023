package com.licenta2023.backend.domain;

public class MovieReview extends Entity {
    public Long reviewID;
    public Integer movieID;

    public MovieReview(Long reviewID, Integer movieID) {
        this.reviewID = reviewID;
        this.movieID = movieID;
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }
}
