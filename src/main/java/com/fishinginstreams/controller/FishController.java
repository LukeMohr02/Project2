package com.fishinginstreams.controller;

import com.fishinginstreams.model.Fish;
import com.fishinginstreams.repository.FishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/fish")
public class FishController {

    @Autowired
    FishRepo repo;

    @GetMapping
    public @ResponseBody List<Fish> getAllFish() {
        return repo.findAll();
    }

    @PostMapping
    public @ResponseBody Fish save(Fish f) {
        return repo.save(f);
    }
}
