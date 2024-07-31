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

    public List<Rating> getRatingsForProfessor(int id) {
        return null;
    }

    public List<Rating> getRatingsForProfessorCourse(int id, String course) {
        return null;
    }

    public double getAverageQualityForProfessor(int id) {
        return 0.0;
    }

    public double getAverageDifficultyForProfessor(int id) {
        return 0.0;
    }
}