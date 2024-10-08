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
        if (course.equals("all")) {
            return getRatingsForProfessor(professorId);
        }

        return ratingRepository.findAllByCourseByProfessorId(professorId, course);
    }

    public Rating getRating(int id) {
        return ratingRepository.findById(id);
    }

    public double getAverageQualityForProfessor(int professorId) {
        List<Rating> allRatings = ratingRepository.findAllByProfessorId(professorId);
        OptionalDouble optionalDouble = allRatings.stream()
                .mapToDouble(rating -> rating.getQuality())
                .average();

        return (optionalDouble.isPresent()) ? optionalDouble.getAsDouble() : -1.0;
    }

    public double getAverageDifficultyForProfessor(int professorId) {
        List<Rating> allRatings = ratingRepository.findAllByProfessorId(professorId);
        OptionalDouble optionalDouble = allRatings.stream()
                .mapToDouble(rating -> rating.getDifficulty())
                .average();

        return (optionalDouble.isPresent()) ? optionalDouble.getAsDouble() : -1.0;
    }

    public void deleteRating(int id) {
        ratingRepository.deleteById(id);
    }

    public void updateRating(Rating rating) {
        ratingRepository.update(rating);
    }

    public List<Rating> getRatingsForProfessorOffset(int professorId, int offset) {
        return ratingRepository.findAllByProfessorIdOffset(professorId, offset);
    }

    public List<Rating> getRatingsForProfessorCourseOffset(int professorId, String course, int offset) {
        if (course.equals("all")) {
            return getRatingsForProfessorOffset(professorId, offset);
        }

        return ratingRepository.findAllByCourseByProfessorIdOffset(professorId, course, offset);
    }
}