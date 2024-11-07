<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Feedback" %>
<%@ page import="dal.FeedbackDAO" %>
<%@ include file="header.jsp" %>

<%
    FeedbackDAO feedbackDAO = new FeedbackDAO();
    List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
    request.setAttribute("feedbackList", feedbackList);
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Feedback Page</title>
    <!-- Add Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Template Header -->
    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url('images/hero_bg_1.jpg');"
         data-stellar-background-ratio="0.5">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-md-7 text-center">
                    <h1 class="mb-0">Feedback</h1>
                </div>
            </div>
        </div>
    </div>

    <div class="container mt-5">
        <form action="FeedbackServlet" method="post" class="mb-4">
            <div class="form-group">
                <label for="userId">User ID:</label>
                <input type="number" id="userId" name="userId" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="feedbackText">Feedback:</label>
                <textarea id="feedbackText" name="feedbackText" class="form-control" required></textarea>
            </div>
            <div class="form-group">
                <label for="rating">Rating:</label>
                <input type="number" id="rating" name="rating" class="form-control" min="1" max="5" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit Feedback</button>
        </form>

        
    </div>

    <!-- Thank You Modal -->
    <c:if test="${param.feedbackSubmitted == 'true'}">
        <div class="modal fade" id="thankYouModal" tabindex="-1" role="dialog" aria-labelledby="thankYouModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="thankYouModalLabel">Thank you for your support</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">close</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <!-- Add Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            <% if (request.getParameter("feedbackSubmitted") != null) { %>
                $('#thankYouModal').modal('show');
            <% } %>
        });
    </script>
</body>
</html>
