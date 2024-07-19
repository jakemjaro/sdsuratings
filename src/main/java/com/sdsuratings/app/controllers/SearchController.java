package com.sdsuratings.app.controllers;

import com.sdsuratings.app.models.ProfessorRepository;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final PebbleEngine pebbleEngine;
    private ProfessorRepository professorRepository;

    public SearchController(PebbleEngine pebbleEngine, ProfessorRepository professorRepository) {
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
        compiledTemplate.evaluate(writer);
        writer.append('\n');

        return writer.toString();
    }

    @GetMapping("/results")
    @ResponseStatus(HttpStatus.OK)
    String searchResults(Model model, @RequestParam("professorName") String professorName) throws IOException {
        if (professorName.isBlank()) {
            return "";
        }

        List<String> matches = professorRepository.getMatchingProfessors(professorName);
        model.addAttribute("matches", matches);

        return render(new StringWriter(), "searchResults", model.asMap());
    }

    @GetMapping("/suggestion")
    @ResponseStatus(HttpStatus.OK)
    String searchSuggestion(Model model, @RequestParam("professorName") String professorName) throws IOException {
        if (professorName.isBlank()) {
            return "";
        }

        List<String> matches = professorRepository.getMatchingProfessors(professorName, 5);

        model.addAttribute("matches", matches);

        return render(new StringWriter(), "dropdown", model.asMap());
    }
}
