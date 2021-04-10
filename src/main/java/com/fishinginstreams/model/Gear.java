package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gear {

    @Id
    @GeneratedValue
    private int id;
    private Enum rod;
    private Enum fishhook;
    private Enum bait;
    private Enum lure;
    private Enum bobber;
    private double sinkWeight;

}
