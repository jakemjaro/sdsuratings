package com.sdsuratings.app.service;

import com.sdsuratings.app.model.Rating;
import com.sdsuratings.app.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addRating(Rating rating) {
        ratingRepository.create(rating);
    }

    public List<Rating> getRatingsForProfessor(int professorId) {
        return ratingRepository.findAllByProfessorId(professorId);
    }

    public List<Rating> getRatingsForProfessorCourse(int professorId, String course) {
        return ratingRepository.findAllByCourseByProfessorId(professorId, course);
    }
}