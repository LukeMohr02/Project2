package com.fishinginstreams.repository;

import com.fishinginstreams.model.Catch;
import com.fishinginstreams.model.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearRepo extends JpaRepository<Gear, Integer> {

}
