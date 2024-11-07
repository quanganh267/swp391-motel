<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<title>Contract Management</title>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Contract Management</h1>

    <!-- Notification for success/error -->
    <c:if test="${not empty sessionScope.notification}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${sessionScope.notification}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <%
            session.removeAttribute("notification");
        %>
    </c:if>
    <c:if test="${not empty sessionScope.notificationErr}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${sessionScope.notificationErr}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <%
            session.removeAttribute("notificationErr");
        %>
    </c:if>

    <!-- Add New Contract Button -->
    <button type="button" class="btn btn-primary mb-4" data-toggle="modal" data-target="#addContractModal">Add New Contract</button>

    <!-- Contract List Table -->
    <div class="table-responsive">
        <table class="table table-bordered" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Motel</th>
                    <th>Room</th>
                    <th>User</th>
                    <th>Created date</th>
                    <th>Payment Date</th>
                    <th>Total Price</th>
                    <th>Type</th>
                    <th>Payment Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contract" items="${contracts}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${contract.motel.motelName}</td>
                        <td>${contract.room.roomType.name}</td>
                        <td>${contract.user.name}</td>
                          <td><fmt:formatDate value="${contract.createAt}" pattern="dd/MM/yyyy"/></td>
                          <td><fmt:formatDate value="${contract.paymentDate}" pattern="dd/MM/yyyy"/></td>
                        <td>${contract.totalPrice}</td>

                        <!-- Contract Type with Color -->
                        <td>
                            <span style="color: ${contract.typeOfContract == 'Long-Term' ? 'green' : 'orange'};">
                                ${contract.typeOfContract}
                            </span>
                        </td>

                        <!-- Payment Status with Color -->
                        <td>
                            <span style="color: ${contract.paymentStatus == 'Paid' ? 'green' : 'red'};">
                                ${contract.paymentStatus}
                            </span>
                        </td>
                        <td>
                            <!-- Edit Button opens edit modal -->
                            <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#editContractModal${contract.id}">Edit</button>

                            <!-- Delete Button with Confirmation -->
                            <form action="contract-management" method="POST" style="display:inline;" onsubmit="return confirmDelete()">
                                <input type="hidden" name="contractId" value="${contract.id}"/>
                                <input type="hidden" name="action" value="delete"/>
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>

                            <script>
                                function confirmDelete() {
                                    return confirm("Are you sure you want to delete this contract?");
                                }
                            </script>

                        </td>
                    </tr>

                    <!-- Edit Contract Modal (for each contract) -->
                    <!-- Edit Contract Modal (for each contract) -->
                <div class="modal fade" id="editContractModal${contract.id}" tabindex="-1" role="dialog" aria-labelledby="editContractModalLabel${contract.id}" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form action="contract-management" method="POST" onsubmit="return validateEditContractForm(${contract.id})"> <!-- Pass contract id to validation function -->
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="contractId" value="${contract.id}">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editContractModalLabel${contract.id}">Edit Contract</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <!-- Room Selection -->
                                    <div class="form-group">
                                        <label for="editRoomId${contract.id}">Select Room</label>
                                        <select id="editRoomId${contract.id}" name="roomId" class="form-control" required>
                                            <option value="">-- Select Room --</option>
                                            <c:forEach var="room" items="${rooms}">
                                                <option value="${room.roomId}" ${room.roomId == contract.room.roomId ? 'selected' : ''}>${roomType.name} - ${motel.motelName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- User Selection -->
                                    <div class="form-group">
                                        <label for="editUserId${contract.id}">Select User</label>
                                        <select id="editUserId${contract.id}" name="userId" class="form-control" required>
                                            <option value="">-- Select User --</option>
                                            <c:forEach var="user" items="${users}">
                                                <option value="${user.id}" ${user.id == contract.user.id ? 'selected' : ''}>${user.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Total Price -->
                                    <div class="form-group">
                                        <label for="editTotalPrice${contract.id}">Total Price</label>
                                        <input type="number" id="editTotalPrice${contract.id}" name="totalPrice" class="form-control" value="${contract.totalPrice}" required/>
                                    </div>

                                    <!-- Payment Method -->
                                    <div class="form-group">
                                        <label for="editPaymentMethod${contract.id}">Payment Method</label>
                                        <select id="editPaymentMethod${contract.id}" name="paymentMethod" class="form-control" required>
                                            <option value="Credit Card" ${contract.paymentMethod == 'Credit Card' ? 'selected' : ''}>Credit Card</option>
                                            <option value="Debit Card" ${contract.paymentMethod == 'Debit Card' ? 'selected' : ''}>Debit Card</option>
                                            <option value="Cash" ${contract.paymentMethod == 'Cash' ? 'selected' : ''}>Cash</option>
                                        </select>
                                    </div>

                                    <!-- Payment Status -->
                                    <div class="form-group">
                                        <label for="editPaymentStatus${contract.id}">Payment Status</label>
                                        <select id="editPaymentStatus${contract.id}" name="paymentStatus" class="form-control" required>
                                            <option value="Paid" ${contract.paymentStatus == 'Paid' ? 'selected' : ''}>Paid</option>
                                            <option value="Unpaid" ${contract.paymentStatus == 'Unpaid' ? 'selected' : ''}>Unpaid</option>
                                        </select>
                                    </div>

                                    <!-- Contract Type -->
                                    <div class="form-group">
                                        <label for="editType${contract.id}">Contract Type</label>
                                        <select id="editType${contract.id}" name="type" class="form-control" required>
                                            <option value="Short-Term" ${contract.typeOfContract == 'Short-Term' ? 'selected' : ''}>Short-Term</option>
                                            <option value="Long-Term" ${contract.typeOfContract == 'Long-Term' ? 'selected' : ''}>Long-Term</option>
                                        </select>
                                    </div>

                                    <!-- Payment Date -->
                                    <div class="form-group">
                                        <label for="editPaymentDate${contract.id}">Payment Date</label>
                                        <input type="date" id="editPaymentDate${contract.id}" name="paymentDate" class="form-control" value="${contract.paymentDate}" required/>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Update Contract</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <nav class="mt-3">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>
            <c:forEach var="pageNum" begin="1" end="${totalPages}">
                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                    <a class="page-link" href="?page=${pageNum}">${pageNum}</a>
                </li>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

<!-- Add Contract Modal -->
<div class="modal fade" id="addContractModal" tabindex="-1" role="dialog" aria-labelledby="addContractModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="contract-management" method="POST" onsubmit="return validateAddContractForm()">
                <div class="modal-header">
                    <h5 class="modal-title" id="addContractModalLabel">Add New Contract</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- Room Selection -->
                    <div class="form-group">
                        <label for="roomId">Select Room</label>
                        <select id="roomId" name="roomId" class="form-control" required>
                            <option value="">-- Select Room --</option>
                            <c:forEach var="room" items="${rooms}">
                                <option value="${room.id}">${room.roomType.name} - ${room.motel.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- User Selection -->
                    <div class="form-group">
                        <label for="userId">Select User</label>
                        <select id="userId" name="userId" class="form-control" required>
                            <option value="">-- Select User --</option>
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}">${user.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Total Price -->
                    <div class="form-group">
                        <label for="totalPrice">Total Price</label>
                        <input type="number" id="totalPrice" name="totalPrice" class="form-control" required/>
                        <input type="hidden" name="action" value="add"/>
                    </div>

                    <!-- Payment Method -->
                    <div class="form-group">
                        <label for="paymentMethod">Payment Method</label>
                        <select id="paymentMethod" name="paymentMethod" class="form-control" required>
                            <option value="Credit Card">Credit Card</option>
                            <option value="Debit Card">Debit Card</option>
                            <option value="Cash">Cash</option>
                        </select>
                    </div>

                    <!-- Payment Status -->
                    <div class="form-group">
                        <label for="paymentStatus">Payment Status</label>
                        <select id="paymentStatus" name="paymentStatus" class="form-control" required>
                            <option value="Paid">Paid</option>
                            <option value="Unpaid">Unpaid</option>
                        </select>
                    </div>

                    <!-- Contract Type -->
                    <div class="form-group">
                        <label for="type">Contract Type</label>
                        <select id="type" name="type" class="form-control" required>
                            <option value="Short-Term">Short-Term</option>
                            <option value="Long-Term">Long-Term</option>
                        </select>
                    </div>

                    <!-- Payment Date -->
                    <div class="form-group">
                        <label for="paymentDate">Payment Date</label>
                        <input type="date" id="paymentDate" name="paymentDate" class="form-control" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add Contract</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<!-- JavaScript for Validation -->
<script>
// Add Contract Form Validation
    function validateAddContractForm() {
        return validateFormFields('roomId', 'userId', 'totalPrice', 'paymentMethod', 'paymentStatus', 'paymentDate', 'type');
    }

// Edit Contract Form Validation
    function validateEditContractForm() {
        return validateFormFields('editRoomId', 'editUserId', 'editTotalPrice', 'editPaymentMethod', 'editPaymentStatus', 'editPaymentDate', 'editType');
    }

// Common Validation Function
    function validateFormFields(roomId, userId, totalPrice, paymentMethod, paymentStatus, paymentDate, type) {
        let isValid = true;

        clearErrors();

        if (document.getElementById(roomId).value === "") {
            showError(roomId, "Please select a room.");
            isValid = false;
        }
        if (document.getElementById(userId).value === "") {
            showError(userId, "Please select a user.");
            isValid = false;
        }
        if (document.getElementById(totalPrice).value === "" || isNaN(document.getElementById(totalPrice).value) || document.getElementById(totalPrice).value <= 0) {
            showError(totalPrice, "Please enter a valid total price.");
            isValid = false;
        }
        if (document.getElementById(paymentMethod).value === "") {
            showError(paymentMethod, "Please select a payment method.");
            isValid = false;
        }
        if (document.getElementById(paymentStatus).value === "") {
            showError(paymentStatus, "Please select a payment status.");
            isValid = false;
        }
        if (document.getElementById(paymentDate).value === "") {
            showError(paymentDate, "Please select a payment date.");
            isValid = false;
        }
        if (document.getElementById(type).value === "") {
            showError(type, "Please select a contract type.");
            isValid = false;
        }

        return isValid;
    }

// Show error message below the field
    function showError(inputId, message) {
        const input = document.getElementById(inputId);
        const error = document.createElement("div");
        error.className = "error-message";
        error.style.color = "red";
        error.innerText = message;
        input.parentElement.appendChild(error);
    }

// Clear all previous error messages
    function clearErrors() {
        const errorMessages = document.querySelectorAll(".error-message");
        errorMessages.forEach(function (error) {
            error.remove();
        });
    }
</script>
