package com.khlebtsov.kalories.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
