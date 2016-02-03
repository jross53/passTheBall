package com.passTheBall;

import java.sql.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passTheBall")
public class PassTheBallController {

    @Autowired
    TeamsService teamsService;

    @Autowired
    PlayersService playersService;

    @RequestMapping(value = "/getPlayersForAllTeams")
    public boolean getPlayersForAllTeams() throws SQLException {
        teamsService.getPlayersForAllTeams();
        return true;
    }

    @RequestMapping(value = "/getAllPlayers")
    public ArrayList<String> getAllPlayers() throws SQLException {
        return playersService.getAllPlayers();
    }

    @RequestMapping(value = "/newTeam")
    public boolean insertNewTeams() throws SQLException {
        teamsService.insertNewTeams();
        return true;
    }

    @RequestMapping(value = "/getTeamsForAllPlayers")
    public boolean getTeamsForAllPlayers() {
        return playersService.getTeamsForAllPlayers();
    }

    @RequestMapping(value = "/calculateRankings")
    public boolean calculateRankings() {
        return playersService.calculateRankings();
    }
}
