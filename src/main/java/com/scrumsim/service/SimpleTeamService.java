package com.scrumsim.service;

import com.scrumsim.model.Team;
import com.scrumsim.model.User;
import com.scrumsim.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

public class SimpleTeamService implements TeamService {

    private final TeamRepository teamRepository;

    public SimpleTeamService(TeamRepository teamRepository) {
        if (teamRepository == null) {
            throw new IllegalArgumentException("TeamRepository cannot be null");
        }
        this.teamRepository = teamRepository;
    }

    @Override
    public Team createTeam(String teamName, User creator) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null");
        }
        if (teamRepository.existsByName(teamName)) {
            throw new IllegalStateException("Team with name '" + teamName + "' already exists");
        }

        Team newTeam = new Team(teamName, creator);
        teamRepository.save(newTeam);
        return newTeam;
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> getTeamByName(String teamName) {
        return teamRepository.findByName(teamName);
    }

    @Override
    public boolean teamExists(String teamName) {
        return teamRepository.existsByName(teamName);
    }

    @Override
    public boolean deleteTeam(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        if (!teamRepository.existsByName(teamName)) {
            throw new IllegalStateException("Team with name '" + teamName + "' does not exist");
        }
        return teamRepository.delete(teamName);
    }
}