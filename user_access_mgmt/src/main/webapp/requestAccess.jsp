<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Software Access</title>

    <!-- Bootstrap CDN for styling -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin-top: 50px;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        .error-message {
            color: red;
            font-weight: bold;
        }
        footer {
            text-align: center;
            margin-top: 30px;
        }
        footer a {
            color: #007bff;
        }
        footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="form-container">
            <h1>Request Software Access</h1>

            <!-- Display Error Message if no software available -->
            <c:if test="${empty softwareList}">
                <div class="error-message">
                    No software available to request access for.
                </div>
            </c:if>

            <!-- Request Access Form -->
            <form action="RequestServlet" method="post">
                
                <!-- Software Dropdown -->
                <div class="form-group">
                    <label for="softwareId">Software:</label>
                    <select name="softwareId" id="softwareId" class="form-control" required>
                        <option value="">Select Software</option>
                        <c:forEach var="software" items="${softwareList}">
                            <option value="${software.id}">${software.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Access Type Dropdown -->
                <div class="form-group">
                    <label for="accessType">Access Type:</label>
                    <select name="accessType" id="accessType" class="form-control" required>
                        <option value="">Select Access Type</option>
                        <option value="Read">Read</option>
                        <option value="Write">Write</option>
                        <option value="Admin">Admin</option>
                    </select>
                </div>

                <!-- Reason Textarea -->
                <div class="form-group">
                    <label for="reason">Reason:</label>
                    <textarea name="reason" id="reason" class="form-control" required></textarea>
                </div>

                <!-- Submit Button -->
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">Submit Request</button>
                </div>
            </form>

        </div>
    </div>

    <!-- Footer Link to Dashboard -->
    <footer>
        <p><a href="login.jsp">Back to Login page</a></p>
    </footer>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
