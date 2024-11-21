package DAO;
import java.sql.*;
import java.util.*;
import model.*;



public class SoftwareDAO {
    private Connection connection;

    public SoftwareDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addSoftware(String softwareName, String version) {
        String sql = "INSERT INTO software (name, version) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, softwareName);
            pstmt.setString(2, version);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Successfully added software
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Failed to add software
        }
    }

    public void updateSoftware(int id, String name, String version) {
        String sql = "UPDATE software SET name = ?, version = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, version);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteSoftware(int id) {
        String sql = "DELETE FROM software WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Successfully deleted software
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Failed to delete software
        }
    }
}
