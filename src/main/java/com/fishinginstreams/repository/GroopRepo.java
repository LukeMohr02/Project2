package com.fishinginstreams.repository;

import com.fishinginstreams.model.Groop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroopRepo extends JpaRepository<Groop, Integer> {

    Groop findByName(String name);
}
