package noah.noahMiningV2.data;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.events.custom.OreBreak;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class PlayerData {
    private Connection connection;
    private String dbURL;
    private File dataFile = new File(NoahMiningV2.INSTANCE.getDataFolder() + "/data.db");
    private UUID playerUUID;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.dbURL = "jdbc:sqlite:" + dataFile.getPath();
        connect();
        createTable();
        addPlayer();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            if (!dataFile.getParentFile().exists())
                dataFile.getParentFile().mkdirs();
            if (!dataFile.exists())
                dataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_data ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "pUUID TEXT UNIQUE,"
                + "souls INTEGER DEFAULT 0"
                + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean addPlayer() {
        if (playerExists())
            return false;
        String insertSQL = "INSERT INTO player_data (pUUID, souls) VALUES (?, 0);";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, playerUUID.toString());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removePlayer() {
        if (!playerExists())
            return false;
        String deleteSQL = "DELETE FROM player_data WHERE pUUID = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, playerUUID.toString());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerExists() {
        String querySQL = "SELECT 1 FROM player_data WHERE pUUID = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(querySQL)) {
            pstmt.setString(1, playerUUID.toString());
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getSouls() {
        if (!playerExists())
            return 0;
        String querySQL = "SELECT souls FROM player_data WHERE pUUID = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(querySQL)) {
            pstmt.setString(1, playerUUID.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("souls");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean setSouls(int souls) {
        if (!playerExists())
            return false;
        String updateSQL = "UPDATE player_data SET souls = ? WHERE pUUID = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, souls);
            pstmt.setString(2, playerUUID.toString());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSouls(int amount) {
        if (!playerExists())
            return false;
        return setSouls(getSouls() + amount);
    }

    public boolean removeSouls(int amount) {
        if (!playerExists())
            return false;
        return setSouls(Math.max(0, getSouls() - amount));
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
