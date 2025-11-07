package com.scrumsim.repository;

import com.scrumsim.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    List<Team> findAll();
    Optional<Team> findByName(String name);
    void save(Team team);
    boolean existsByName(String name);
}