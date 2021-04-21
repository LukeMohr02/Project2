package com.fishinginstreams.controller;

import com.fishinginstreams.exception.NoCatchFoundByIdException;
import com.fishinginstreams.model.*;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.CatchRepo;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import com.fishinginstreams.service.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@CrossOrigin
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

//    @GetMapping("/leaderboard")
//    public @ResponseBody Page<Catch> getLeaderboardCatches(
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
//        Page<Catch> catches = repo.findAll(PageRequest.of(page, offset));
//        catches.get().sorted(Comparator.comparingDouble(fishRepo.findById(katch -> katch.getFish())));
//        return catches;
//    }

    @GetMapping("/{id}")
    public @ResponseBody Catch getCatchById(@PathVariable(name = "id") int id) {
        return repo.getOne(id);
    }

    @PostMapping
    public @ResponseBody Catch save(@RequestBody PreCatch preCatch) {
        Catch c = new Catch();
        String username = preCatch.getUsername();
        c.setAngler(anglerRepo.findByUsername(username));
        ExceptionHandler eh = new ExceptionHandler();

        eh.NotNullConstraintViolation(new String[]{preCatch.getUsername()},
                                      new String[]{"username"});
        eh.NotNullConstraintViolation(new Integer[]{preCatch.getFishId(), preCatch.getGearId()},
                                      new String[]{"fishId", "gearId"});

        try {
            c.setFish(fishRepo.findById(preCatch.getFishId()).get());
            c.setGear(gearRepo.findById(preCatch.getGearId()).get());
        } catch (NoSuchElementException e) {
            // Empty catch
        };

        eh.EntityNotFound(new Object[]{c.getAngler(), c.getFish(), c.getGear()},
                          new String[]{"username", "fishId","gearId"});

        return repo.save(c);

    }

    @PutMapping
    public @ResponseBody Catch update(@RequestBody Catch c) {

        try {
            Catch original = repo.findById(c.getId()).get();
            int id = original.getId();

            original = c;
            original.setId(id);

            return repo.save(original);
        } catch (NoSuchElementException e) {
            throw new NoCatchFoundByIdException(c.getId());
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Catch deleteCatch(@PathVariable(name = "id") int id) {
        Catch c = repo.getOne(id);
        repo.delete(c);
        return c;
    }
}
