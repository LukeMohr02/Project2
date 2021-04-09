package com.fishinginstreams.controller;

import com.fishinginstreams.model.Fish;
import com.fishinginstreams.model.Gear;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/gear")
public class GearController {

    @Autowired
    GearRepo repo;

    @GetMapping
    public @ResponseBody List<Gear> getAllGears() {
        return repo.findAll();
    }

    @PostMapping
    public @ResponseBody Gear save(Gear g) {
        return repo.save(g);
    }
}
