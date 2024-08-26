package com.sdsuratings.app.controller;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.model.Rating;
import com.sdsuratings.app.service.ProfessorService;
import com.sdsuratings.app.service.RatingService;
import com.sdsuratings.app.util.Constants;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final PebbleEngine pebbleEngine;
    private ProfessorService professorService;
    private RatingService ratingService;

    public ProfessorController(PebbleEngine pebbleEngine, ProfessorService professorService, RatingService ratingService) {
        this.pebbleEngine = pebbleEngine;
        this.professorService = professorService;
        this.ratingService = ratingService;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    String profile(Model model, @PathVariable("id") int id, HttpServletRequest request) throws IOException {
        loadProfessorPageData(model, id);

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorPage", model.asMap());
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    String list(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessorsOffset(0));
        model.addAttribute("offset", Constants.OFFSET_FACTOR);

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorListPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorListPage", model.asMap());
        }
    }

    @GetMapping("/partial/{offset}")
    @ResponseStatus(HttpStatus.OK)
    String listOffset(Model model, @PathVariable int offset) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessorsOffset(offset));
        model.addAttribute("offset", offset + Constants.OFFSET_FACTOR);

        return render(new StringWriter(), "professorList", model.asMap());
    }

    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    String sort(Model model, @RequestParam("filter") int filter, @RequestParam("department") String department) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessorsSortedOffset(filter, department, 0));
        model.addAttribute("offset", Constants.OFFSET_FACTOR);
        model.addAttribute("filter", filter);
        model.addAttribute("department", department);

        return render(new StringWriter(), "professorListSorted", model.asMap());
    }

    @GetMapping("/sort/{offset}")
    @ResponseStatus(HttpStatus.OK)
    String sortOffset(Model model, @RequestParam("filter") int filter, @RequestParam("department") String department, @PathVariable int offset) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessorsSortedOffset(filter, department, offset));
        model.addAttribute("offset", offset + Constants.OFFSET_FACTOR);
        model.addAttribute("filter", filter);
        model.addAttribute("department", department);

        return render(new StringWriter(), "professorListSorted", model.asMap());
    }

    @GetMapping("/add-form")
    @ResponseStatus(HttpStatus.OK)
    String form() throws IOException {
        return render(new StringWriter(), "addProfessorForm");
    }

    @GetMapping("/add-button")
    @ResponseStatus(HttpStatus.OK)
    String button() throws IOException {
        return render(new StringWriter(), "addProfessorButton");
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    String add(Model model, @RequestParam("first-name") String firstName, @RequestParam("last-name") String lastName, @RequestParam("department") String department) throws IOException {
        String firstLetterFN = firstName.substring(0,1).toUpperCase();
        String formattedFN = firstLetterFN + firstName.substring(1);
        formattedFN = formattedFN.trim();

        String firstLetterLN = lastName.substring(0,1).toUpperCase();
        String formattedLN = firstLetterLN + lastName.substring(1);
        formattedLN = formattedLN.trim();

        boolean status = professorService.addProfessor(new Professor(-1, formattedFN, formattedLN, department));

        model.addAttribute("status", status);

        return render(new StringWriter(), "addProfessorStatus", model.asMap());
    }
}
