<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Software</title>

    <!-- Bootstrap CDN for styling -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Create New Software</h1>

        <!-- Display Success or Error Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="SoftwareServlet" method="post">
            <div class="form-group">
                <label for="softwareName">Software Name:</label>
                <input type="text" id="softwareName" name="softwareName" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" class="form-control" required></textarea>
            </div>

            <div class="form-group">
                <label>Access Levels:</label><br>
                <input type="checkbox" id="read" name="accessLevels" value="Read"> Read<br>
                <input type="checkbox" id="write" name="accessLevels" value="Write"> Write<br>
                <input type="checkbox" id="admin" name="accessLevels" value="Admin"> Admin<br>
            </div>

            <button type="submit" class="btn btn-primary">Create Software</button>
        </form>

        <br>
        <a href="SoftwareServlet" class="btn btn-link">Go to Software List</a>
        <a href="ApprovalServlet" class="btn btn-link">Go to Pending request By employee</a>
        <a href="login.jsp" class="btn btn-link">Go to Login page</a>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
