package com.fishinginstreams.model;

import com.fishinginstreams.enums.HabitatType;
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
    private int id;
    private String species;
    private double length;
    private double weight;
    private HabitatType habitat;
    private String dangerLevel;
    private URI image;
}
