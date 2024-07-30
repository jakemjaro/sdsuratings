package com.sdsuratings.app.service;

import com.sdsuratings.app.model.Rating;
import com.sdsuratings.app.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addRating(Rating rating) {
        return;
    }

    public List<Rating> getRatingsForProfessor(String name) {
        return null;
    }

    public List<Rating> getRatingsForProfessorCourse(String name, String course) {
        return null;
    }

    public double getAverageQualityForProfessor(String name) {
        return 0.0;
    }

    public double getAverageDifficultyForProfessor(String name) {
        return 0.0;
    }
}