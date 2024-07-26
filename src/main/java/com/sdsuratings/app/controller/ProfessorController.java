package com.sdsuratings.app.controller;

import com.sdsuratings.app.repository.ProfessorRepository;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final PebbleEngine pebbleEngine;
    private ProfessorRepository professorRepository;

    public ProfessorController(PebbleEngine pebbleEngine, ProfessorRepository professorRepository) {
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

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    String profile(Model model, HttpServletRequest request) throws IOException {
        int[] myList = new int[10];

        model.addAttribute("myList", myList);

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorPage", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorPage", model.asMap());
        }
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    String all(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("allProfessors", professorRepository.getAllProfessors());

        if (request.getHeader("HX-request") != null) {
            return render(new StringWriter(), "professorList", model.asMap());
        } else {
            return render(new StringWriter(), "fullProfessorList", model.asMap());
        }
    }
}
