package com.fishinginstreams.controller;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Fish;
import com.fishinginstreams.model.Gear;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

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

    @PutMapping
    public @ResponseBody Gear update(@RequestBody Gear g) {
        try {
            Gear original = repo.findById(g.getId()).get();
            int id = original.getId();

            original = g;
            original.setId(id);

            return repo.save(original);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("No entry found with gearId: " + g.getId());
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Gear deleteGear(@PathVariable(name = "id") int id) {
        Gear gear = repo.getOne(id);
        repo.delete(gear);
        return gear;
    }
}
