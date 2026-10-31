package com.example.backend;

// Import JPA repository functionality.
import org.springframework.data.jpa.repository.JpaRepository;

// Import annotation to define custom SQL queries.
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// Import annotation to bind query parameters.
import org.springframework.data.repository.query.Param;

// Repository for database operations on Counter.
public interface CounterRepository extends JpaRepository<Counter, Long> {

    // Atomically increase the counter value in PostgreSQL.
    // PostgreSQL performs the increment directly in the database.
    @Modifying
    @Query("UPDATE Counter c SET c.value = c.value + 1 WHERE c.id = :id")
    int incrementCounter(@Param("id") Long id);
}