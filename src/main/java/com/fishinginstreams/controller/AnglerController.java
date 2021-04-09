package com.fishinginstreams.controller;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/angler")
public class AnglerController {

    @Autowired
    AnglerRepo repo;

    @GetMapping
    public @ResponseBody Page<Angler> getAllAngler(@RequestParam("page") int page, @RequestParam("offset") int offset){
        return repo.findAll(PageRequest.of(page, offset));
    }

    @GetMapping
    public @ResponseBody Angler getAnglerByUsername(@RequestParam("username") String username){
        return repo.findByUsername(username);
    }
}
