package com.sdsuratings.app.controller;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.service.ProfessorService;
import com.sdsuratings.app.service.RatingService;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
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

    @GetMapping("/profile/{id}")
    @ResponseStatus(HttpStatus.OK)
    String profile(Model model, @PathVariable("id") int id, HttpServletRequest request) throws IOException {
        model.addAttribute("professor", professorService.getProfessor(id));
        model.addAttribute("ratingList", ratingService.getRatingsForProfessor(id));

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorPage", model.asMap());
        }
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    String list(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessors());

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorListPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorListPage", model.asMap());
        }
    }

    @GetMapping("/list/sort")
    @ResponseStatus(HttpStatus.OK)
    String sort(Model model, @RequestParam("filter") int filter, @RequestParam("department") String department) throws IOException {
        model.addAttribute("professorList", professorService.getAllProfessorsSorted(filter, department));

        return render(new StringWriter(), "professorList", model.asMap());
    }

    @GetMapping("/form")
    @ResponseStatus(HttpStatus.OK)
    String form() throws IOException {
        return render(new StringWriter(), "addProfessorForm");
    }

    @GetMapping("/button")
    @ResponseStatus(HttpStatus.OK)
    String button() throws IOException {
        return render(new StringWriter(), "addProfessorButton");
    }

    @PostMapping("/add")
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
