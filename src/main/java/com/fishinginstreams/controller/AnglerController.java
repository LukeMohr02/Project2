package com.fishinginstreams.controller;

import com.fishinginstreams.exception.NoAnglerFoundByUsername;
import com.fishinginstreams.exception.NotNullConstraintViolationException;
import com.fishinginstreams.exception.UniqueConstraintViolationException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Catch;
import com.fishinginstreams.model.Groop;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.GroopRepo;
import org.hibernate.PropertyValueException;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/angler")
public class AnglerController {

    @Autowired
    AnglerRepo repo;

    @GetMapping
    public @ResponseBody Page<Angler> getAllAnglers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page,offset));
    }

    @GetMapping("/{username}")
    public @ResponseBody Angler getAnglerById(@PathVariable("username") String username) {
        return repo.findByUsername(username);
    }

    @PostMapping
    public @ResponseBody Angler save(@RequestBody Angler a) {
        try {
            return repo.save(a);
        } catch (PropertyValueException e) {
            throw new NotNullConstraintViolationException("username");
        } catch (Exception e) {
            throw new UniqueConstraintViolationException("username", a.getUsername());
        }
    }

    @PutMapping
    public @ResponseBody Angler update(@RequestBody Angler a) {
        Angler original = repo.findByUsername(a.getUsername());

        if (original == null) {
            throw new NoAnglerFoundByUsername(a.getUsername());
        }

        int id = original.getId();

        original = a;
        original.setId(id);

        return repo.save(original);
    }

    @DeleteMapping("/{username}")
    public Angler deleteAnglerById(@PathVariable("username") String username) {
        Angler angler = repo.findByUsername(username);

        if (angler == null) {
            throw new NoAnglerFoundByUsername(username);
        }

        repo.delete(angler);
        return angler;
    }

}
