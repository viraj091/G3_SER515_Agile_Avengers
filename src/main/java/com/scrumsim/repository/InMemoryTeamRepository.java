package com.scrumsim.repository;

import com.scrumsim.model.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryTeamRepository implements TeamRepository {
    private final List<Team> teams;

    public InMemoryTeamRepository() {
        this.teams = new CopyOnWriteArrayList<>();
        initializeDefaultTeams();
    }

    private void initializeDefaultTeams() {
        teams.add(new Team("Team Alpha", "Scrum Master"));
        teams.add(new Team("Team Beta", "Developer"));
        teams.add(new Team("Team Gamma", "Product Owner"));
    }

    @Override
    public List<Team> findAll() {
        return new ArrayList<>(teams);
    }

    @Override
    public Optional<Team> findByName(String name) {
        return teams.stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public void save(Team team) {
        teams.add(team);
    }

    @Override
    public boolean existsByName(String name) {
        return teams.stream()
                .anyMatch(team -> team.getName().equalsIgnoreCase(name));
    }
}