package com.fishinginstreams.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Catch {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Angler angler;
    @NotNull
    @OneToOne
    private Fish fish;
    @NotNull
    @OneToOne
    private Gear gear;
}
