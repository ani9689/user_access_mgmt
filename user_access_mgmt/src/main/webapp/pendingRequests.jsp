<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Requests</title>

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
        .action-buttons button {
            margin: 5px;
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
        <h1>Pending Access Requests</h1>

        <!-- Display Success or Error Messages -->
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                ${param.error}
            </div>
        </c:if>
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">
                ${param.success}
            </div>
        </c:if>

        <!-- Display Pending Requests -->
        <div class="table-container">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Username</th>
                        <th>Software</th>
                        <th>Access Type</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${requestScope.pendingRequests}">
                        <tr>
                            <td>${request.username}</td>
                            <td>${request.softwareName}</td>
                            <td>${request.accessType}</td>
                            <td>${request.reason}</td>
                            <td>${request.status}</td>
                            <td class="action-buttons">
                                <form action="ApprovalServlet" method="post" class="form-inline">
                                    <input type="hidden" name="requestId" value="${request.id}">
                                    <button type="submit" name="action" value="approve" class="btn btn-success btn-sm">Approve</button>
                                    <button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

    <!-- Footer Link -->
    <footer>
        <p><a href="login.jsp">Back to Login page</a></p>
    </footer>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
