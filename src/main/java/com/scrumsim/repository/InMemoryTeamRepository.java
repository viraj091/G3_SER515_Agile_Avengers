package com.scrumsim.repository;

import com.scrumsim.model.Team;
import com.scrumsim.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryTeamRepository implements TeamRepository {
    private final List<Team> teams;

    public InMemoryTeamRepository(User defaultScrumMaster) {
        this.teams = new CopyOnWriteArrayList<>();
        initializeDefaultTeams(defaultScrumMaster);
    }

    @Deprecated
    public InMemoryTeamRepository() {
        this.teams = new CopyOnWriteArrayList<>();
        initializeDefaultTeamsDeprecated();
    }

    private void initializeDefaultTeams(User scrumMaster) {
        teams.add(new Team("Team Alpha", scrumMaster));
        teams.add(new Team("Team Beta", scrumMaster));
        teams.add(new Team("Team Gamma", scrumMaster));
    }

    @Deprecated
    private void initializeDefaultTeamsDeprecated() {
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