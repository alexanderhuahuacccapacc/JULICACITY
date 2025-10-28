package com.juliaca.routes.msroutes.controller;

import com.juliaca.routes.msroutes.model.Line;
import com.juliaca.routes.msroutes.service.LineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LineController {

    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/lines")
    public List<Line> getLines() {
        return lineService.findAll();
    }

    @PostMapping("/lines")
    public Line createLine(@RequestBody Line line) {
        return lineService.save(line);
    }
}
