package com.juliaca.routes.msroutes.service;

import com.juliaca.routes.msroutes.model.Line;
import com.juliaca.routes.msroutes.repository.LineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    private final LineRepository repository;

    public LineService(LineRepository repository) {
        this.repository = repository;
    }

    public List<Line> findAll() {
        return repository.findAll();
    }

    public Line save(Line line) {
        return repository.save(line);
    }
}
