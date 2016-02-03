package com.passTheBall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TeamsService {

    @Autowired
    TeamsDAO teamsDAO;

    public boolean getPlayersForAllTeams() throws SQLException {
        teamsDAO.getPlayersForAllTeams();
        return true;
    }

    public boolean insertNewTeam(int id, String name) throws SQLException {
        teamsDAO.insertNewTeam(id, name);
        return true;
    }

    public boolean insertNewTeams() throws SQLException {
        teamsDAO.insertNewTeams();
        return true;
    }
}
