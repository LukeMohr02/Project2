package com.fishinginstreams.controller;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Groop;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.GroopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/group")
public class GroopController {

    @Autowired
    GroopRepo repo;

    @GetMapping
    public @ResponseBody Page<Groop> getAllGroops(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page,offset));
    }

    @GetMapping("/{id}")
    public @ResponseBody Groop getAnglerById(@PathVariable("id") int id) {
        return repo.getOne(id);
    }

    @PostMapping
    public @ResponseBody Groop save(@RequestBody Groop g) {
        return repo.save(g);
    }

    @DeleteMapping("/{id}")
    public Groop deleteAnglerById(@PathVariable("id") int id) {
        Groop groop = repo.getOne(id);
        repo.delete(groop);
        return groop;
    }

    //TODO: postmapping for object mapping
}
