package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fish {

    @Id
    @GeneratedValue
    int id;
    String species;
    double length;
    double weight;
    Enum habitat;
    Enum dangerLevel;
    URI image;
}
