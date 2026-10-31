package com.example.backend;

// Import Spring REST API annotations.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

// Import transaction support.
import org.springframework.transaction.annotation.Transactional;

// Import Map for JSON responses.
import java.util.Map;

// Marks this class as a REST API controller.
@RestController

// Allow requests from our React frontend.
@CrossOrigin(origins = "http://localhost:5173")

// All API endpoints start with /api/counter.
@RequestMapping("/api/counter")
public class CounterController {

    // Connect the controller to the repository.
    private final CounterRepository counterRepository;

    // Constructor injection: Spring provides the repository.
    public CounterController(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    // Fetch the current counter value.
    @GetMapping
    public Map<String, Integer> getCounter() {

        // Find the counter record or create it with value 2.
        Counter counter = counterRepository.findById(1L)
                .orElseGet(() -> counterRepository.save(new Counter(2)));

        return Map.of("value", counter.getValue());
    }

    // Increment the counter safely.
    @PostMapping("/increment")
    @Transactional
    public Map<String, Integer> incrementCounter() {

        // Create the initial record if it does not exist.
        counterRepository.findById(1L)
                .orElseGet(() -> counterRepository.save(new Counter(2)));

        // Ask PostgreSQL to increment the value atomically.
        counterRepository.incrementCounter(1L);

        // Read the updated value from PostgreSQL.
        Counter updatedCounter = counterRepository.findById(1L)
                .orElseThrow();

        return Map.of("value", updatedCounter.getValue());
    }
}