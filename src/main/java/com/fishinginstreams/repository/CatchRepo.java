package com.fishinginstreams.repository;

import com.fishinginstreams.model.Catch;
import com.fishinginstreams.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatchRepo extends JpaRepository<Catch, Integer> {

    List<Catch> findAll();
}
