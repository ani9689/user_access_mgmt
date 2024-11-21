import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import DAO.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;


@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_mgmt";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Root@123";

    // Handles GET requests: Fetch all pending requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT r.id, u.username, s.name AS software_name, r.access_type, r.reason, r.status " +
                     "FROM requests r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "JOIN software s ON r.software_id = s.id " +
                     "WHERE r.status = 'Pending'";

        List<Request> pendingRequests = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Request requestObj = new Request();
                requestObj.setId(rs.getInt("id"));
                requestObj.setUsername(rs.getString("username"));
                requestObj.setSoftwareName(rs.getString("software_name"));
                requestObj.setAccessType(rs.getString("access_type"));
                requestObj.setReason(rs.getString("reason"));
                requestObj.setStatus(rs.getString("status"));
                pendingRequests.add(requestObj);
            }

            request.setAttribute("pendingRequests", pendingRequests);
            RequestDispatcher dispatcher = request.getRequestDispatcher("pendingRequests.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }

    // Handles POST requests: Approve or Reject request
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestIdStr = request.getParameter("requestId");
        String action = request.getParameter("action");

        if (requestIdStr == null || action == null) {
            response.sendRedirect("pendingRequests.jsp?error=Invalid+Request+Parameters");
            return;
        }

        int requestId;
        try {
            requestId = Integer.parseInt(requestIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("pendingRequests.jsp?error=Invalid+Request+ID");
            return;
        }

        String newStatus = "Rejected";
        if ("approve".equals(action)) {
            newStatus = "Approved";
        }

        // SQL query to update the status of the request
        String sql = "UPDATE requests SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, requestId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("ApprovalServlet?success=Request+" + newStatus);
            } else {
                response.sendRedirect("ApprovalServlet?error=Failed+to+Update+Request+Status");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("pendingRequests.jsp?error=Database+Error:+" 
                    + e.getMessage().replace(" ", "+"));
        }
    }
}
