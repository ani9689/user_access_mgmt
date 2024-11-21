
import model.Software; // Assuming `Software` is part of your `model` package
import util.DBConnection; // Utility class for database connection management
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle POST requests to create a new software entry
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String softwareName = request.getParameter("softwareName");
        String description = request.getParameter("description");
        String[] accessLevelsArray = request.getParameterValues("accessLevels");
        String accessLevels = (accessLevelsArray != null) ? String.join(", ", accessLevelsArray) : "";

        String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, softwareName);
            pstmt.setString(2, description);
            pstmt.setString(3, accessLevels);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                request.setAttribute("successMessage", "Software created successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to create software.");
            }

        } catch (SQLException e) {
            log("Database error in doPost", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        // Redirect to the software list page after creation
        response.sendRedirect(request.getContextPath() + "/SoftwareServlet");
    }

    // Handle GET requests to list all software
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT id, name, description, access_levels FROM software";
        List<Software> softwareList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Software software = new Software();
                software.setId(rs.getInt("id"));
                software.setName(rs.getString("name"));
                software.setDescription(rs.getString("description"));
                software.setAccessLevels(rs.getString("access_levels"));
                softwareList.add(software);
            }

            // Set software list as an attribute to be displayed in the JSP
            request.setAttribute("softwareList", softwareList);

        } catch (SQLException e) {
            log("Database error in doGet", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        // Forward the request to the JSP page for display (softwareList.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("softwareList.jsp");
        dispatcher.forward(request, response);
    }
}
