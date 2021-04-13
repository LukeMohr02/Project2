package com.fishinginstreams.controller;

import com.fishinginstreams.model.*;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.CatchRepo;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/catch")
public class CatchController {

    @Autowired
    CatchRepo repo;

    @Autowired
    AnglerRepo anglerRepo;

    @Autowired
    FishRepo fishRepo;

    @Autowired
    GearRepo gearRepo;

    @GetMapping
    public @ResponseBody Page<Catch> getAllCatches(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page, offset));
    }

    @GetMapping("/{id}")
    public @ResponseBody Catch getCatchById(@PathVariable(name = "id") int id) {
        return repo.getOne(id);
    }

    @PostMapping
    public @ResponseBody Catch save(@RequestBody PreCatch preCatch) {
        Catch c = new Catch();
        c.setAngler(anglerRepo.findByUsername(preCatch.getUsername()));
        c.setFish(fishRepo.findById(preCatch.getFishId()).get());
        c.setGear(gearRepo.findById(preCatch.getGearId()).get());
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Catch deleteCatch(@PathVariable(name = "id") int id) {
        Catch c = repo.getOne(id);
        repo.delete(c);
        return c;
    }
}
