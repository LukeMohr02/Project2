package com.fishinginstreams.repository;

import com.fishinginstreams.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FishRepo extends JpaRepository<Fish, Integer> {

}
