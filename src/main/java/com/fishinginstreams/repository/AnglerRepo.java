package com.fishinginstreams.repository;

import com.fishinginstreams.model.Angler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnglerRepo extends JpaRepository<Angler, String> {

    //Why does this work?
    Angler findByUsername(String username);
}
