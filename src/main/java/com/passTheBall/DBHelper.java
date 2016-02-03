package com.passTheBall;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class DBHelper {
    public boolean insertData(String query) throws SQLException {
        Connection c = null;
        try {
            c = getConnection();
            if(c == null) {
                System.err.println("There was an error getting connection to database. Insert cannot be completed.");
                return false;
            }
            Statement stmt = c.createStatement();

            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            c.close();
            return true;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        } finally {
            if(c != null) {
                c.close();
            }
        }
    }

    private Connection getConnection() {
        Connection c;
        try {
            Class.forName(DbConstants.MY_SQL_DRIVER);
            c = DriverManager.getConnection(DbConstants.MY_SQL_CONNECTION_STRING,
                    DbConstants.USERNAME, DbConstants.PASSWORD);
            c.setAutoCommit(false);
            return c;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    public ResultSet selectDataFromDatabase(String query) throws SQLException {
        Connection c = null;
        try {
            c = getConnection();
            Statement stmt;
            if (c != null) {
                stmt = c.createStatement();
                return stmt.executeQuery(query);
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        finally {
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public boolean insertPlayerIntoDb(int playerId, String name, int teamId, int rank, float efficiency) throws SQLException {
        insertData("insert into players (playerId, name, teamId, rank, efficiency) values('" + playerId + "','" + name +
                "','" + teamId + "', " + rank + ", " + efficiency + ")");
        return true;
    }

    public JSONObject getPlayers(int teamId) {
        HttpHelper httpHelper = new HttpHelper();
        try {
            return httpHelper.sendPostToGetTeamRoster(teamId);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }
}


//************DEPRECATED***********************
/*public ResultSet selectDataFromSqlite(String query) {
        try {
            return selectDataFromDatabase("org.sqlite.JDBC", "jdbc:sqlite:passTheBall.db", query);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return null;
    }

    @RequestMapping(value = "/getAllPlayersSqlite")
    public ArrayList<String> getAllPlayersSqlite() throws SQLException {
        return playersDAO.getAllPlayersSqlite();
    }

    public ArrayList<String> getAllPlayersSqlite() throws SQLException {
        ResultSet rs = dbHelper.selectDataFromSqlite("SELECT * FROM players");
        ArrayList<String> players = new ArrayList<>();
        String separator = "|";

        if(rs != null) {
            try {
                while(rs.next()) {
                    String playerId = rs.getString("playerId");
                    String name = rs.getString("name");
                    String teamId = rs.getString("teamId");
                    String rank = rs.getString("rank");
                    String player = playerId + separator + name + separator + teamId + separator + rank;
                    players.add(player);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return players;
    }

    */