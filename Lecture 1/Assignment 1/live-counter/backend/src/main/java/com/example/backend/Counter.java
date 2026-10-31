package com.example.backend;

// Marks this class as a database entity.
import jakarta.persistence.Entity;

// Defines the primary key of the table.
import jakarta.persistence.Id;

// Automatically generates the primary key.
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// Specifies the database table name.
import jakarta.persistence.Table;

// Represents a database table named "counter".
@Entity
@Table(name = "counter")
public class Counter {

    // Primary key for the counter table.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stores the actual counter value.
    private int value;

    // Required by JPA to create objects from database records.
    public Counter() {
    }

    // Constructor used when creating a new counter.
    public Counter(int value) {
        this.value = value;
    }

    // Returns the counter value.
    public int getValue() {
        return value;
    }

    // Updates the counter value.
    public void setValue(int value) {
        this.value = value;
    }

    // Returns the database record ID.
    public Long getId() {
        return id;
    }
}