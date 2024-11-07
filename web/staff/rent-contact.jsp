<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="header.jsp"></jsp:include>
<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Rent Contact Management</h1>

    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Rent Contact List</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Guest Name</th>
                            <th>Room ID</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Phone Number</th>
                            <th>Email</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contact" items="${rentContacts}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${contact.guestName}</td>
                                <td>${contact.roomId}</td>
                                <td><fmt:formatDate value="${contact.startDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${contact.endDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${contact.phoneNumber}</td>
                                <td>${contact.email}</td>
                                <td>${contact.address}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include> 