package com.licenta2023.backend.service;

import com.licenta2023.backend.domain.Review;
import com.licenta2023.backend.repository.RepositoryInterfaces.IReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService{
    @Autowired
    public IReviewRepo reviewRepo;

    public ReviewService(IReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Review findOne(Long id) {
        return reviewRepo.findOne(id);
    }

    @Override
    public Iterable<Review> findAll() {
        return reviewRepo.findAll();
    }

    @Override
    public void save(Review review) {
        reviewRepo.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepo.delete(id);
    }

    @Override
    public void update(Review entity) {

    }

    @Override
    public List<Review> getReviewsByUser(Long user){
        return reviewRepo.getReviewsByUser(user);
    }

    @Override
    public List<Review> getReviewsByMovie(Integer movie) {
        return reviewRepo.getReviewsByMovie(movie);
    }

    @Override
    public float getAverageMovieScore(Integer movie) {
        float rating = 0;
        List<Review> reviews = reviewRepo.getReviewsByMovie(movie);
        if(reviews != null && reviews.size() != 0) {
            for (Review r : reviews) {
                rating += r.getRating();
            }
            return rating / reviews.size();
        }
        return 0;
    }

    @Override
    public Review findReviewByUserAndMovie(Long user, Integer movie) {
        return reviewRepo.findByUserAndMovie(user, movie);
    }
}
