package com.fishinginstreams.controller;

import com.fishinginstreams.model.Fish;
import com.fishinginstreams.repository.FishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/fish")
public class FishController {

    @Autowired
    FishRepo repo;

    @GetMapping
    public @ResponseBody Page<Fish> getAllFish(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page, offset));
    }

    @GetMapping("/{id}")
    public @ResponseBody Fish getFishById(@PathVariable(name = "id") int id) {
        return repo.getOne(id);
    }

    @PostMapping
    public @ResponseBody Fish save(@RequestBody Fish f) {
        return repo.save(f);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Fish deleteFish(@PathVariable("id") int id) {
        Fish fish = repo.getOne(id);
        repo.delete(fish);
        return fish;
    }
}
