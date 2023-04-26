package com.licenta2023.backend.domain;

import java.io.Serializable;

public class Review extends Entity implements Serializable {
    private Integer rating;

    private String text;

    private User reviewBy;

    private Integer movieId;

    public Review() {super();}

    public Review(Integer rating, String text, User reviewBy, Integer movieId) {
        this.rating = rating;
        this.text = text;
        this.reviewBy = reviewBy;
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(User reviewBy) {
        this.reviewBy = reviewBy;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
