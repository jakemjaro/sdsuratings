package com.sdsuratings.app.controller;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@RestController
@RequestMapping("")
public class HomeController {
    private final PebbleEngine pebbleEngine;

    public HomeController(PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
    }

    private String render(Writer writer, String templateName) throws IOException {
        PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer);
        writer.append('\n');

        return writer.toString();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    String home() throws IOException {
        return render(new StringWriter(), "home");
    }
}