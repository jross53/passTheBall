package com.passTheBall;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Service
public class PlayersService {

    @Autowired
    PlayersDAO playersDAO;
    @Autowired
    DBHelper dbHelper;

    public ArrayList<String> getAllPlayers() throws SQLException {
        return playersDAO.getAllPlayers();
    }

    public boolean getTeamsForAllPlayers() {
        return playersDAO.insertAllTeamsForAllPlayers();
    }

    public float getEfficiency(float pts, float reb, float ast, float stl, float blk, float missedFg, float missedFt,
                               float turnovers, float gamesPlayed) {
        return (pts + reb + ast + stl + blk - missedFg - missedFt - turnovers) / gamesPlayed;
    }

    private Float averageEfficiencies(ArrayList<Float> efficienciesForPlayer) {
        return null;
    }

    public boolean calculateRankings() {
        HashMap<String, Float> efficiencies = new HashMap<>();
        try {
            Set<String> ids = playersDAO.getAllDistinctPlayerIdsFromDb().keySet();
            for (String id : ids) {
                ArrayList<Float> efficienciesForPlayer = new ArrayList<>();
                String[] yearlyStats = getStatsForAllSeasons(id);

                for (String yearlyStat : yearlyStats) {
                    String[] stats = yearlyStat.split(",");
                    if(stats.length < 26) {
                        System.out.println("Length was less than 26 for player id: " + id);
                    }
                    float pts = Float.parseFloat(stats[26]);
                    float reb = Float.parseFloat(stats[20]);
                    float ast = Float.parseFloat(stats[21]);
                    float stl = Float.parseFloat(stats[22]);
                    float blk = Float.parseFloat(stats[23]);
                    float fgA = Float.parseFloat(stats[10]);
                    float fgM = Float.parseFloat(stats[9]);
                    float missedFg = fgA - fgM;
                    float ftA = Float.parseFloat(stats[16]);
                    float ftM = Float.parseFloat(stats[15]);
                    float missedFt = ftA - ftM;
                    float turnovers = Float.parseFloat(stats[24]);
                    float gamesPlayed = Float.parseFloat(stats[6]);
                    float efficiency = getEfficiency(pts, reb, ast, stl, blk, missedFg, missedFt, turnovers, gamesPlayed);
                    efficienciesForPlayer.add(efficiency);
                }

                efficiencies.put(id, averageEfficiencies(efficienciesForPlayer));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String[] getStatsForAllSeasons(String playerId) {
        try {
            JSONObject stats = new HttpHelper().sendPostToGetUserCareerStats(playerId);
            return stats.getJSONArray("resultSets").getJSONObject(0).getJSONArray("rowSet").toString().split("],");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertNewPlayer(int playerId, String name, int teamId, int rank, float efficiency) throws SQLException {
        dbHelper.insertPlayerIntoDb(playerId, name, teamId, rank, efficiency);
    }
}
