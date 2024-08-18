package com.sdsuratings.app.controller;

import com.sdsuratings.app.model.Professor;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final PebbleEngine pebbleEngine;
    private ProfessorService professorService;
    private RatingService ratingService;

    public SearchController(PebbleEngine pebbleEngine, ProfessorService professorService, RatingService ratingService) {
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

    @GetMapping("/suggestion")
    @ResponseStatus(HttpStatus.OK)
    String searchSuggestion(Model model, @RequestParam("query") String query) throws IOException {
        if (query.isBlank()) {
            return "";
        }

        List<Professor> matches = professorService.searchProfessorsLimited(query, 5);
        model.addAttribute("matches", matches);

        return render(new StringWriter(), "dropdown", model.asMap());
    }

    @GetMapping("/results")
    @ResponseStatus(HttpStatus.OK)
    String searchResults(Model model, HttpServletRequest request, @RequestParam("query") String query) throws IOException {
        List<Professor> matches = Collections.emptyList();
        if (!query.isBlank()) {
            matches = professorService.searchProfessorsOffset(query, 0);
        }

        model.addAttribute("matches", matches);
        model.addAttribute("offset", Constants.OFFSET_FACTOR);
        model.addAttribute("query", query);

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "searchResultsPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullSearchResultsPage", model.asMap());
        }
    }

    @GetMapping("/results/{offset}")
    @ResponseStatus(HttpStatus.OK)
    String resultsOffset(Model model, @RequestParam("query") String query, @PathVariable int offset) throws IOException {
        List<Professor> matches = Collections.emptyList();
        if (!query.isBlank()) {
            matches = professorService.searchProfessorsOffset(query, offset);
        }

        model.addAttribute("matches", matches);
        model.addAttribute("offset", offset + Constants.OFFSET_FACTOR);
        model.addAttribute("query", query);

        return render(new StringWriter(), "searchResults", model.asMap());
    }
}