package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");  // Determine the action (login or signup)

        if ("signup".equals(action)) {
            // Handle Signup Logic
            handleSignup(request, response);
        } else if ("login".equals(action)) {
            // Handle Login Logic
            handleLogin(request, response);
        }
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // You can add more validation or logic here before saving the user (e.g., check for existing username)

        // If everything is valid, create the user and store them in the database
        // After successful signup, redirect to login page
        // Assuming you have a SignUpService or DAO for signup logic
        //SignUpService signUpService = new SignUpService();
        //boolean isSignupSuccessful = signUpService.signup(username, password);

        if (isSignupSuccessful) {
            response.sendRedirect("login.jsp?message=Signup successful, please log in.");
        } else {
            response.sendRedirect("signup.jsp?error=Username already exists.");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // You can add validation here (check if username and password are correct)
        LoginService loginService = new LoginService();
        boolean isLoginSuccessful = loginService.login(username, password);

        if (isLoginSuccessful) {
            // Assuming you have a way to determine the user role (Employee, Manager, Admin)
            String role = loginService.getUserRole(username);  // Get the role after successful login

            // Set session attribute for user role
            request.getSession().setAttribute("role", role);
            // Redirect to the appropriate page based on role
            if ("Employee".equals(role)) {
                response.sendRedirect("requestAccess.jsp");
            } else if ("Manager".equals(role)) {
                response.sendRedirect("pendingRequests.jsp");
            } else if ("Admin".equals(role)) {
                response.sendRedirect("createSoftware.jsp");
            }
        } else {
            // If login fails, redirect to the login page with an error
            response.sendRedirect("login.jsp?error=Invalid credentials.");
        }
    }
}
