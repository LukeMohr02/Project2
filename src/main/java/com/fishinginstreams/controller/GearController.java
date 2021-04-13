package com.fishinginstreams.controller;

import com.fishinginstreams.model.Fish;
import com.fishinginstreams.model.Gear;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gear")
public class    GearController {

    @Autowired
    GearRepo repo;

    @GetMapping
    public @ResponseBody Page<Gear> getAllGears(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page, offset));
    }

    @GetMapping("/{id}")
    public @ResponseBody Gear getGearById(@PathVariable(name = "id") int id) {
        return repo.getOne(id);
    }

    @PostMapping
    public @ResponseBody Gear save(@RequestBody Gear g) {
        return repo.save(g);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Gear deleteGear(@PathVariable(name = "id") int id) {
        Gear gear = repo.getOne(id);
        repo.delete(gear);
        return gear;
    }
}
