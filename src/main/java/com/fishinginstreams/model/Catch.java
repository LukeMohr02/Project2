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
public class Catch {

    @Id
    @GeneratedValue
    private int id;
    //TODO: change from ints to objects
    private int userId;
    private int fishId;
    private int gearId;
}
