package org.paulnikepro.hw4;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Default constructor
    public Role() { }

    // Constructor with parameters
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
