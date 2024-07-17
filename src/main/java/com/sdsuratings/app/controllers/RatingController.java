package com.sdsuratings.app.controllers;

import com.sdsuratings.app.models.ProfessorRepository;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RatingController {

    private final PebbleEngine pebbleEngine;
    private ProfessorRepository professorRepository;

    public RatingController(PebbleEngine pebbleEngine, ProfessorRepository professorRepository) {
        this.pebbleEngine = pebbleEngine;
        this.professorRepository = professorRepository;
    }

    private String render(Writer writer, String templateName, Map<String, Object> data) throws IOException {
        PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer, data);
        writer.append('\n');

        return writer.toString();
    }

    private String render(Writer writer, String templateName) throws IOException {
        PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer, new HashMap<>());
        writer.append('\n');

        return writer.toString();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    String home() throws IOException {
        return render(new StringWriter(), "home");
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    String loginPage() throws IOException {
        return render(new StringWriter(), "login");
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    String results(Model model, @RequestParam("professorName") String professorName) throws IOException {
        if (professorName.isBlank()) {
            return "";
        }

        List<String> matches = professorRepository.getMatchingProfessors(professorName);
        model.addAttribute("matches", matches);

        return render(new StringWriter(), "searchResult", model.asMap());
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    String test(Model model) throws IOException {
        return render(new StringWriter(), "professor", model.asMap());
    }

    @GetMapping("/searchSuggestion")
    @ResponseStatus(HttpStatus.OK)
    String searchSuggestion(Model model, @RequestParam("professorName") String professorName) throws IOException {
        if (professorName.isBlank()) {
            return "";
        }

        List<String> matches = professorRepository.getMatchingProfessors(professorName, 5);

        model.addAttribute("matches", matches);

        return render(new StringWriter(), "dropdown", model.asMap());
    }

    @GetMapping("/professorPage")
    @ResponseStatus(HttpStatus.OK)
    String professorPage(Model model) throws IOException {
        int[] myList = new int[10];

        model.addAttribute("myList", myList);

        return render(new StringWriter(), "professorPage", model.asMap());
    }

}
