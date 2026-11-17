package com.example.tourist.controller;

import com.example.tourist.dto.TouristRequest;
import com.example.tourist.entity.Tourist;
import com.example.tourist.service.TouristService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tourist> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Tourist getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tourist create(@Valid @RequestBody TouristRequest request) {
        return service.create(request);
    }

    /** PUT /api/tourists/{id}  (requires valid Bearer token) */
    @PutMapping("/{id}")
    public Tourist update(@PathVariable Long id, @Valid @RequestBody TouristRequest request) {
        return service.update(id, request);
    }
}
