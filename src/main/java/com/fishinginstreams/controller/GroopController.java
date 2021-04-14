package com.fishinginstreams.controller;

import com.fishinginstreams.exception.AlreadyInGroupException;
import com.fishinginstreams.exception.NotInGroupException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Groop;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.GroopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroopController {

    @Autowired
    GroopRepo repo;

    @Autowired
    AnglerRepo anglerRepo;

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

    @PutMapping
    public @ResponseBody Groop update(@RequestBody Groop g) {
        Groop original = repo.findById(g.getId()).get();
        int id = original.getId();

        original = g;
        original.setId(id);

        return repo.save(original);
    }

    @PutMapping("/{group}/join")
    public @ResponseBody Groop joinGroup(@PathVariable("group") String g, @RequestBody Angler a) {
        Groop group = repo.findByName(g);
        Angler angler = anglerRepo.findByUsername(a.getUsername());

        List<Angler> anglers = group.getAnglers();
        List<Groop> groups = angler.getGroups();

        if (anglers.contains(angler) || groups.contains(group)) {
            throw new AlreadyInGroupException(angler.getUsername(), g);
        }

        anglers.add(angler);
        group.setAnglers(anglers);

        groups.add(group);
        angler.setGroups(groups);

        anglerRepo.save(angler);
        return repo.save(group);
    }

    @PutMapping("/{group}/leave")
    public @ResponseBody Groop leaveGroup(@PathVariable("group") String g, @RequestBody Angler a) {
        Groop group = repo.findByName(g);
        Angler angler = anglerRepo.findByUsername(a.getUsername());

        List<Angler> anglers = group.getAnglers();
        List<Groop> groups = angler.getGroups();

        if (!anglers.contains(angler) || !groups.contains(group)) {
            throw new NotInGroupException(angler.getUsername(), g);
        }

        anglers.remove(angler);
        group.setAnglers(anglers);

        groups.remove(group);
        angler.setGroups(groups);

        anglerRepo.save(angler);
        return repo.save(group);
    }

    @DeleteMapping("/{id}")
    public Groop deleteAnglerById(@PathVariable("id") int id) {
        Groop groop = repo.getOne(id);
        repo.delete(groop);
        return groop;
    }

    //TODO: postmapping for object mapping
}
