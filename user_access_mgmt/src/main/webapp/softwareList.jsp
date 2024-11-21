<%@ page import="model.Software" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Software List</title>

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
        .alert {
            margin-bottom: 20px;
        }
        .table-container {
            margin-top: 20px;
        }
        .table th, .table td {
            text-align: center;
        }
        .btn-create {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Software List</h1>

        <!-- Display Success or Error Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                ${successMessage}
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
        </c:if>

        <!-- Display Software List -->
        <div class="table-container">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Software ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Access Levels</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="software" items="${softwareList}">
                        <tr>
                            <td>${software.id}</td>
                            <td>${software.name}</td>
                            <td>${software.description}</td>
                            <td>${software.accessLevels}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Link to Create New Software -->
        <a href="createSoftware.jsp" class="btn btn-primary btn-create">Create New Software</a>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
