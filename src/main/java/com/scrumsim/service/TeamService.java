package com.scrumsim.service;

import com.scrumsim.model.Team;
import com.scrumsim.model.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    Team createTeam(String teamName, User creator);

    List<Team> getAllTeams();

    Optional<Team> getTeamByName(String teamName);

    boolean teamExists(String teamName);

    boolean deleteTeam(String teamName);
}