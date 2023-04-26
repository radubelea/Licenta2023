package com.licenta2023.backend.service;

import com.licenta2023.backend.domain.Review;

import java.util.List;

public interface IReviewService extends IService<Review> {
    public List<Review> getReviewsByUser(Long user);

    public List<Review> getReviewsByMovie(Integer movie);

    public float getAverageMovieScore(Integer movie);

    public Review findReviewByUserAndMovie(Long user, Integer movie);

}
