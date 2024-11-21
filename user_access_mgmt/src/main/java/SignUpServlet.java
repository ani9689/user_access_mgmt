import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_mgmt";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Root@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Load the PostgreSQL driver (only needed if not using Maven)
        try {
            Class.forName("org.postgresql.Driver"); // Ensure this driver is available
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: PostgreSQL JDBC Driver not found.");
            return;
        }

        // Retrieve form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Set default role if not provided
        if (role == null || role.isEmpty()) {
            role = "employee"; // Default role is "employee"
        }

        // Database query
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Redirect to login page upon successful sign-up
                response.sendRedirect("login.jsp");
            } else {
                response.getWriter().println("Error: Unable to sign up. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
