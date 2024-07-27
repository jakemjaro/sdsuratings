package com.sdsuratings.app.controller;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final PebbleEngine pebbleEngine;

    public RatingController(PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
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

    @GetMapping("/form")
    @ResponseStatus(HttpStatus.OK)
    String form() throws IOException {
        return render(new StringWriter(), "ratingForm");
    }
}
