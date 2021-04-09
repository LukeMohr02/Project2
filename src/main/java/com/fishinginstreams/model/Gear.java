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
    int id;
    Enum rod;
    Enum fishhook;
    Enum bait;
    Enum lure;
    Enum bobber;
    double sinkWeight;

}
