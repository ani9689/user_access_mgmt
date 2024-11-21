import model.Request; // Assuming you have a Request model class
import model.Software;
import util.DBConnection;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_mgmt";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Root@123";

    // Handles GET requests: Fetch all software data for displaying in request form
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT id, name FROM software";
        List<Software> softwareList = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Software software = new Software();
                software.setId(rs.getInt("id"));
                software.setName(rs.getString("name"));
                softwareList.add(software);
            }

            // Forward software list to JSP for rendering
            request.setAttribute("softwareList", softwareList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("requestAccess.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }

    // Handles POST requests: Process access request submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String softwareIdStr = request.getParameter("softwareId");
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");
        String username = (String) request.getSession().getAttribute("username"); // Logged-in user's username

        // Validate input parameters
        if (softwareIdStr == null || accessType == null || reason == null || username == null) {
            response.sendRedirect("RequestServlet?error=Invalid+Request+Parameters");
            return;
        }

        int softwareId;
        try {
            softwareId = Integer.parseInt(softwareIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("RequestServlet?error=Invalid+Software+ID");
            return;
        }

        // SQL query to insert new access request into the database
        String sql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) " +
                     "VALUES ((SELECT id FROM users WHERE username = ?), ?, ?, ?, 'Pending')";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set query parameters
            pstmt.setString(1, username);
            pstmt.setInt(2, softwareId);
            pstmt.setString(3, accessType);
            pstmt.setString(4, reason);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("RequestServlet?success=Request+Submitted+Successfully");
            } else {
                response.sendRedirect("RequestServlet?error=Failed+to+Submit+Request");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("RequestServlet?error=Database+Error:+" + e.getMessage().replace(" ", "+"));
        }
    }
}
