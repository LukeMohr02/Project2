package com.fishinginstreams.controller;

import com.fishinginstreams.model.Catch;
import com.fishinginstreams.repository.CatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/catch")
public class CatchController {

    @Autowired
    CatchRepo repo;

    @GetMapping
    public @ResponseBody List<Catch> getAllCatches() {
        return repo.findAll();
    }

    @PostMapping
    public @ResponseBody Catch save(Catch c) {
        return repo.save(c);
    }
}
