<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RentContact" %>
<%@ page import="dal.RentContactDAO" %>
<%@ include file="header.jsp" %>
<html lang="en">
<head>
    <title>Rent Room</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Rent Room</h1>
        <form action="${pageContext.request.contextPath}/Rent" method="Post" class="needs-validation" novalidate>
            <div class="form-group">
                <label for="guest_name">Guest Name:</label>
                <input type="text" class="form-control" id="guest_name" name="guest_name" required>
                <div class="invalid-feedback">Please enter your name.</div>
            </div>
            
            <div class="form-group">
                <label for="room_id">Select Room:</label>
                <select class="form-control" id="room_id" name="room_id" required>
                    <option value="">Choose a room...</option>
                    <c:forEach items="${rooms}" var="room">
                        <option value="${room.roomId}" ${room.roomId == roomID ? 'selected' : ''}>
                            Room ${room.roomId} - ${room.motelName} - ${room.typeName} - $${room.price}
                        </option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">Please select a room.</div>
            </div>
            
            <div class="form-group">
                <label for="start_date">Start Date:</label>
                <input type="date" class="form-control" id="start_date" name="start_date" required>
                <div class="invalid-feedback">Please select a start date.</div>
            </div>
            
            <div class="form-group">
                <label for="end_date">End Date:</label>
                <input type="date" class="form-control" id="end_date" name="end_date" required>
                <div class="invalid-feedback">Please select an end date.</div>
            </div>
            
            <div class="form-group">
                <label for="phone_number">Phone Number:</label>
                <input type="text" class="form-control" id="phone_number" name="phone_number" pattern="^0\d{9}$" required>
                <div class="invalid-feedback">Please enter a valid 10-digit phone number starting with 0.</div>
            </div>
            
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" class="form-control" id="address" name="address" required>
                <div class="invalid-feedback">Please enter your address.</div>
            </div>
            
            <button type="submit" class="btn btn-primary btn-block">Rent Now</button>
            <a href="${pageContext.request.contextPath}/Room" class="btn btn-secondary btn-block">Back</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
    <!-- Add SweetAlert2 CSS and JS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.32/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.32/dist/sweetalert2.all.min.js"></script>

    <script>
        // Check session status on page load
        const status = '${sessionScope.status}';
        if (status === '1') {
            ${sessionScope.remove("status")}
            Swal.fire({
                title: 'Success!',
                text: 'Room rented successfully!',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '${pageContext.request.contextPath}/';
                }
            });
        }
    </script>
</body>
</html>
