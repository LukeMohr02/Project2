package com.fishinginstreams.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Gear {

    @Id
    @GeneratedValue
    int id;
    Enum rod;
    Enum fishhook;
    Enum bait;
    Enum lure;
    Enum bobber;
    double sinkWeight;

    public Gear() {
    }

    public Gear(Enum rod, Enum fishhook, Enum bait, Enum lure, Enum bobber, double sinkWeight) {
        this.rod = rod;
        this.fishhook = fishhook;
        this.bait = bait;
        this.lure = lure;
        this.bobber = bobber;
        this.sinkWeight = sinkWeight;
    }

    // (not sure how getters and setters work for enums, but those go here)

    public double getSinkWeight() {
        return sinkWeight;
    }

    public void setSinkWeight(double sinkWeight) {
        this.sinkWeight = sinkWeight;
    }
}
