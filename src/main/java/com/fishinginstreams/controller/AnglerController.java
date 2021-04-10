package com.fishinginstreams.controller;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/angler")
public class AnglerController {

    @Autowired
    AnglerRepo repo;

    @GetMapping
    public @ResponseBody List<Angler> getAllAnglers() {
        return repo.findAll();
    }

    @PostMapping
    public @ResponseBody Angler save(Angler a) {
        return repo.save(a);
    }
}
