package com.licenta2023.backend.repository.RepositoryInterfaces;

import com.licenta2023.backend.domain.Review;

import java.util.List;

public interface IReviewRepo extends IRepo<Review> {
    List<Review> getReviewsByUser(Long user);
    List<Review> getReviewsByMovie(Integer movie);

    Review findByUserAndMovie(Long user, Integer movie);
}
