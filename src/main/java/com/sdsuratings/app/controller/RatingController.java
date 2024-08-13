package com.sdsuratings.app.controller;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.model.Rating;
import com.sdsuratings.app.service.ProfessorService;
import com.sdsuratings.app.service.RatingService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
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

    @GetMapping("/form/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    String form(Model model, @PathVariable int professorId) throws IOException {
        model.addAttribute("professorId", professorId);

        return render(new StringWriter(), "ratingForm", model.asMap());
    }

    @PostMapping("/add/{professorId}")
    @ResponseStatus(HttpStatus.CREATED)
    String add(Model model, @PathVariable int professorId, @RequestParam("quality") double quality, @RequestParam("difficulty") double difficulty,
               @RequestParam("course") String course, @RequestParam("course-number") String courseNumber, @RequestParam("grade") String grade,
               @RequestParam("description") String description, @RequestParam("accessibility") double accessibility, @RequestParam("workload") String workload,
               @RequestParam("class-type") String classType) throws IOException {
        String fullCourse = course + "-" + courseNumber;

        ratingService.addRating(new Rating(-1, professorId, quality, difficulty, fullCourse, grade, LocalDate.now(), description, accessibility, workload, classType));

        List<Rating> ratingList = ratingService.getRatingsForProfessor(professorId);
        Professor professor = professorService.getProfessor(professorId);

        model.addAttribute("ratingList", ratingList);
        model.addAttribute("professor", professor);

        return render(new StringWriter(), "professorPage", model.asMap());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String delete(Model model, @PathVariable("id") int id) throws IOException {
        ratingService.deleteRating(id);

        return "";
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    String edit(Model model, @PathVariable int id, @RequestParam("quality") double quality, @RequestParam("difficulty") double difficulty,
                @RequestParam("course") String course, @RequestParam("course-number") String courseNumber, @RequestParam("grade") String grade,
                @RequestParam("description") String description, @RequestParam("accessibility") double accessibility, @RequestParam("workload") String workload,
                @RequestParam("class-type") String classType) throws IOException {

        return "";
    }
}
