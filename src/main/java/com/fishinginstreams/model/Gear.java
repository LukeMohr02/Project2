package com.fishinginstreams.model;

import com.fishinginstreams.enums.*;
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
    private RodType rod;
    private FishHookType fishhook;
    private BaitType bait;
    private LureType lure;
    private BobberType bobber;
    private double sinkWeight;

}
