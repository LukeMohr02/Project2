package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Angler {

    @Id
    @GeneratedValue
    int id;
    String username;
    String password;
    String firstName;
    String lastName;
    int age;
    String state;

    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL)
    List<Catch> catches;

    {
        catches = new ArrayList<>();
    }

}
