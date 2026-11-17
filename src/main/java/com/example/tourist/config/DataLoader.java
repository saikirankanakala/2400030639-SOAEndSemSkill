package com.example.tourist.config;

import com.example.tourist.entity.Tourist;
import com.example.tourist.repository.TouristRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Seeds two tourists so that PUT /api/tourists/1 works right away. */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seed(TouristRepository repo) {
        return args -> {
            repo.save(new Tourist("Ravi Kumar", "ravi@example.com", "9876543210", "India", "P1234567"));
            repo.save(new Tourist("Emma Watson", "emma@example.com", "4412345678", "UK", "G7654321"));
        };
    }
}
