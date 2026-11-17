package com.example.tourist.service;

import com.example.tourist.dto.TouristRequest;
import com.example.tourist.entity.Tourist;
import com.example.tourist.exception.ResourceNotFoundException;
import com.example.tourist.repository.TouristRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<Tourist> findAll() {
        return repository.findAll();
    }

    public Tourist findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id " + id));
    }

    public Tourist create(TouristRequest req) {
        Tourist t = new Tourist();
        apply(t, req);
        return repository.save(t);
    }

    /** STEP 2 - fetch existing record, modify fields, save back. */
    @Transactional
    public Tourist update(Long id, TouristRequest req) {
        Tourist existing = findById(id);   // fetch
        apply(existing, req);              // modify
        return repository.save(existing);  // save back
    }

    private void apply(Tourist t, TouristRequest req) {
        t.setName(req.getName());
        t.setEmail(req.getEmail());
        t.setPhone(req.getPhone());
        t.setCountry(req.getCountry());
        t.setPassportNumber(req.getPassportNumber());
    }
}
