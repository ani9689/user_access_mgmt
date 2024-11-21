import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_mgmt";

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Root@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Retrieve login credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // SQL query to check user credentials
        String sql = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";
        try {
            // Load PostgreSQL driver explicitly
            Class.forName("org.postgresql.Driver");
            
            // Establish the connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // Other code follows...
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("PostgreSQL Driver not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set query parameters
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // User found, create session and redirect based on role
                int userId = rs.getInt("id");
                String role = rs.getString("role");

                // Create a session for the user
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                // Redirect based on role
                
                if ("admin".equals(role)) {
                    response.sendRedirect("createSoftware.jsp");  // Admin's page
                } else if ("manager".equals(role)) {
                    response.sendRedirect("ApprovalServlet");  // Manager's page
                } else {
                    response.sendRedirect("RequestServlet");  // Employee's page
                }
            } else {
                // Invalid credentials, redirect to login page with an error message
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
