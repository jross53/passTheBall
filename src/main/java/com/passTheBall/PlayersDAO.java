package com.passTheBall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.*;

@Component
public class PlayersDAO {
    @Autowired
    DBHelper dbHelper;

    public HashMap<String, String> getAllDistinctPlayerIdsFromDb() throws SQLException {
        HashMap<String, String> playersAndIds = new HashMap<>();

        ResultSet rs = dbHelper.selectDataFromDatabase("SELECT DISTINCT(playerId) ,name FROM players");
        while (rs.next()) {
            String id = rs.getString("playerId");
            String name = rs.getString("name");
            playersAndIds.put(id, name);
        }
        return playersAndIds;
    }

    public ArrayList<String> getAllPlayers() throws SQLException {
        ResultSet rs = dbHelper.selectDataFromDatabase("SELECT * FROM players");
        ArrayList<String> players = new ArrayList<>();
        String separator = "|";

        if(!rs.isClosed()) {
            while(rs.next()) {
                String playerId = rs.getString("playerId");
                String name = rs.getString("name");
                String teamId = rs.getString("teamId");
                String rank = rs.getString("rank");
                String player = playerId + separator + name + separator + teamId + separator + rank;
                players.add(player);
            }
        }

        return players;
    }

    public boolean insertAllTeamsForAllPlayers() {
        //you'll want to make sure you know what you're doing when you call this

        /*HashMap<String, String> ids = null;
        try {
            ids = getAllDistinctPlayerIdsFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        int count = 0;

        for (String id : ids.keySet()) {
            Set<String> teamIds = new TreeSet<>();
            String[] seasonStats = getStatsForAllSeasons(id);
            for (String seasonStat : seasonStats) {
                String[] sts = seasonStat.split(",");
                if (sts.length < 3) {
                    System.out.println("Length was less than 3 for player: " + ids.get(id));
                    continue;
                }
                String teamId = sts[3].replaceAll("\\W+", "");
                teamIds.add(teamId);
            }
            String name = ids.get(id);
            for (String teamId : teamIds) {
                try {
                    dbHelper.insertPlayerIntoDb(Integer.parseInt(id), name, Integer.parseInt(teamId), 3, 10.0f);
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
            }
            count++;
            System.out.println("count: " + count);
        }*/


        return true;
    }







}
