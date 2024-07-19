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
@RequestMapping("/rating")
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
        compiledTemplate.evaluate(writer);
        writer.append('\n');

        return writer.toString();
    }
}
