package com.passTheBall;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Component
public class TeamsDAO {


    @Autowired
    DBHelper dbHelper;

    HashMap<Integer, String> getAllTeamsAndIds() throws SQLException {
        HashMap<Integer, String> teams = new HashMap<>();

        ResultSet rs = dbHelper.selectDataFromDatabase("SELECT * FROM teams");
        while (rs.next()) {
            int id = rs.getInt("teamId");
            String name = rs.getString("name");

            teams.put(id, name);
        }
        return teams;

    }

    public boolean getPlayersForAllTeams() throws SQLException{
        HashMap<Integer, String> teams = getAllTeamsAndIds();
        for(int teamId : teams.keySet()) {
            JSONObject jObject = dbHelper.getPlayers(teamId);
            if(jObject == null) {
                return false;
            }
            String[] players =
                    jObject.getJSONArray("resultSets").getJSONObject(0).getJSONArray("rowSet").toString().split("],");
            for(String player : players) {
                String[] areas = player.split(",");
                int id = Integer.parseInt(areas[areas.length-1].replaceAll("\\W+", ""));
                String[] names = areas[3].split(" ");
                String name = "";
                for (String name1 : names) {
                    name += name1.replaceAll("\\W+", "") + " ";
                }
                name = name.trim();

                dbHelper.insertPlayerIntoDb(id, name, teamId, 3, 10.0f);
            }

        }
        return true;
    }

    public boolean insertNewTeam(int id, String teamName) throws SQLException {
        return dbHelper.insertData("insert into teams (teamId, name) values('" + id + "','" + teamName + "')");
    }

    public void insertNewTeams() {
        String path = "D:\\Projects\\cloud9carousel\\cloud9carousel-gh-pages\\teamsAndIds.txt";

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String strs[] = line.split("=");
                String team = strs[1];
                String teamId = strs[0];
//                System.out.println("team: " + team + " id: " + teamId);
                try {
                    insertNewTeam(Integer.parseInt(teamId), team);
                } catch(SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
