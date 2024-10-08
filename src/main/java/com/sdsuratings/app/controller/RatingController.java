package com.sdsuratings.app.controller;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.model.Rating;
import com.sdsuratings.app.service.ProfessorService;
import com.sdsuratings.app.service.RatingService;
import com.sdsuratings.app.util.Constants;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final PebbleEngine pebbleEngine;
    private RatingService ratingService;
    private ProfessorService professorService;

    public RatingController(PebbleEngine pebbleEngine, RatingService ratingService, ProfessorService professorService) {
        this.pebbleEngine = pebbleEngine;
        this.ratingService = ratingService;
        this.professorService = professorService;
    }

    private String render(Writer writer, String templateName, Map<String, Object> data) throws IOException {
        PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer, data);
        writer.append('\n');

        return writer.toString();
    }

    private String render(Writer writer, String templateName) throws IOException {
        PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer);
        writer.append('\n');

        return writer.toString();
    }

    public void loadProfessorPageData(Model model, int id) {
        Professor professor = professorService.getProfessor(id);
        List<Rating> ratingList = ratingService.getRatingsForProfessorOffset(id, 0);

        HashSet<String> courseList = new HashSet<>();
        for (Rating r : ratingService.getRatingsForProfessor(id)) {
            courseList.add(r.getCourse());
        }

        model.addAttribute("professor", professor);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("offset", Constants.OFFSET_FACTOR);
        model.addAttribute("professorId", id);
    }

    @GetMapping("/add-form/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    String form(Model model, @PathVariable int professorId) throws IOException {
        model.addAttribute("professorId", professorId);

        return render(new StringWriter(), "ratingForm", model.asMap());
    }

    @PostMapping("/{professorId}")
    @ResponseStatus(HttpStatus.CREATED)
    String add(Model model, @PathVariable int professorId, @RequestParam("quality") double quality, @RequestParam("difficulty") double difficulty,
               @RequestParam("course-dept") String courseDept, @RequestParam("course-number") String courseNumber, @RequestParam("grade") String grade,
               @RequestParam("description") String description, @RequestParam("accessibility") double accessibility, @RequestParam("workload") String workload,
               @RequestParam("class-type") String classType) throws IOException {
        String fullCourse = courseDept + "-" + courseNumber;

        ratingService.addRating(new Rating(-1, professorId, quality, difficulty, fullCourse, grade, LocalDate.now(), description, accessibility, workload, classType));

        loadProfessorPageData(model, professorId);

        return render(new StringWriter(), "professorPage", model.asMap());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    String delete(Model model, @PathVariable int id) throws IOException {
        int professorId = ratingService.getRating(id).getProfessorId();

        ratingService.deleteRating(id);

        loadProfessorPageData(model, professorId);

        return render(new StringWriter(), "professorPage", model.asMap());
    }

    @GetMapping("/edit-form/{id}")
    @ResponseStatus(HttpStatus.OK)
    String editForm(Model model, @PathVariable int id) throws IOException {
        model.addAttribute("rating", ratingService.getRating(id));

        return render(new StringWriter(), "ratingEditForm", model.asMap());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    String edit(Model model, @PathVariable int id, @RequestParam("quality") double quality, @RequestParam("difficulty") double difficulty,
                @RequestParam("course-dept") String courseDept, @RequestParam("course-number") String courseNumber, @RequestParam("grade") String grade,
                @RequestParam("description") String description, @RequestParam("accessibility") double accessibility, @RequestParam("workload") String workload,
                @RequestParam("class-type") String classType) throws IOException {
        int professorId = ratingService.getRating(id).getProfessorId();

        String fullCourse = courseDept + "-" + courseNumber;

        ratingService.updateRating(new Rating(id, professorId, quality, difficulty, fullCourse, grade, LocalDate.now(), description, accessibility, workload, classType));

        loadProfessorPageData(model, professorId);

        return render(new StringWriter(), "professorPage", model.asMap());
    }

    @GetMapping("/{professorId}/{offset}")
    @ResponseStatus(HttpStatus.OK)
    String allOffset(Model model, @PathVariable("professorId") int professorId, @PathVariable("offset") int offset) throws IOException {
        model.addAttribute("ratingList", ratingService.getRatingsForProfessorOffset(professorId, offset));
        model.addAttribute("offset", offset + Constants.OFFSET_FACTOR);
        model.addAttribute("professorId", professorId);

        return render(new StringWriter(), "ratingList", model.asMap());
    }

    @GetMapping("/filter/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    String filter(Model model, @PathVariable int professorId, @RequestParam("course") String course) throws IOException {
        model.addAttribute("ratingList", ratingService.getRatingsForProfessorCourseOffset(professorId, course, 0));
        model.addAttribute("offset", Constants.OFFSET_FACTOR);
        model.addAttribute("course", course);
        model.addAttribute("professorId", professorId);

        return render(new StringWriter(), "ratingListSorted", model.asMap());
    }

    @GetMapping("/filter/{professorId}/{offset}")
    @ResponseStatus(HttpStatus.OK)
    String filterOffset(Model model, @PathVariable("professorId") int professorId, @RequestParam("course") String course, @PathVariable("offset") int offset) throws IOException {
        model.addAttribute("ratingList", ratingService.getRatingsForProfessorCourseOffset(professorId, course, offset));
        model.addAttribute("offset", offset + Constants.OFFSET_FACTOR);
        model.addAttribute("course", course);
        model.addAttribute("professorId", professorId);

        return render(new StringWriter(), "ratingListSorted", model.asMap());
    }
}
