package com.fishinginstreams.controller;

import com.fishinginstreams.exception.NoFishFoundByIdException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Fish;
import com.fishinginstreams.repository.FishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
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

    @PutMapping
    public @ResponseBody Fish update(@RequestBody Fish f) {

        try {
            Fish original = repo.findById(f.getId()).get();
            int id = original.getId();

            original = f;
            original.setId(id);

            return repo.save(original);
        } catch (NoSuchElementException e) {
            throw new NoFishFoundByIdException(f.getId());
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Fish deleteFish(@PathVariable("id") int id) {
        Fish fish = repo.getOne(id);
        repo.delete(fish);
        return fish;
    }
}
