<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"></jsp:include>

<div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
            <h1 class="h3 mb-2 text-gray-800">Guest Management</h1>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown no-arrow">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="mr-2 d-none d-lg-inline text-gray-600 small">${accounts.username}</span>
                        <img class="img-profile rounded-circle" src="${pageContext.request.contextPath}/admin/img/undraw_profile.svg">
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                         aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="#">
                            <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                            Profile
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/logout" data-toggle="modal" data-target="#logoutModal">
                            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                            Logout
                        </a>
                    </div>
                </li>
            </ul>
        </nav>

        <div class="container-fluid">
            <c:if test="${not empty sessionScope.notification}">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notification}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("notification");
                %>
            </c:if>

            <c:if test="${not empty sessionScope.notificationErr}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notificationErr}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("notificationErr");
                %>
            </c:if>

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <button class="btn btn-primary" onclick="openAddGuestModal()">Add New Guest</button>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Guest ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone Number</th>
                                    <th>Identity ID</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="guest" items="${guests}">
                                    <tr id="guest-${guest.guestId}">
                                        <td><c:out value="${guest.guestId}" /></td>
                                        <td><c:out value="${guest.name}" /></td>
                                        <td><c:out value="${guest.email}" /></td>
                                        <td><c:out value="${guest.phoneNum}" /></td>
                                        <td><c:out value="${guest.identityId}" /></td>
                                        <td>
                                            <button class="btn btn-warning btn-sm" onclick="openEditGuestModal(${guest.guestId}, '${guest.name}', '${guest.email}', '${guest.phoneNum}', '${guest.identityId}')">Edit</button>
                                            <form action="<%= request.getContextPath() %>/admin/guest-management" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this guest?');">
                                                <input type="hidden" name="action" value="delete"/>
                                                <input type="hidden" name="guestId" value="${guest.guestId}"/>
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

<!-- Add Guest Modal -->
<div class="modal fade" id="addGuestModal" tabindex="-1" role="dialog" aria-labelledby="addGuestModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="addGuestForm" action="<%= request.getContextPath() %>/admin/guest-management" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="addGuestModalLabel">Add New Guest</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="add"/>
                    <div class="form-group">
                        <label for="addGuestName">Name</label>
                        <input type="text" class="form-control" id="addGuestName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="addGuestEmail">Email</label>
                        <input type="email" class="form-control" id="addGuestEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="addGuestPhoneNum">Phone Number</label>
                        <input type="text" class="form-control" id="addGuestPhoneNum" name="phoneNum" required>
                    </div>
                    <div class="form-group">
                        <label for="addGuestIdentityId">Identity ID</label>
                        <input type="text" class="form-control" id="addGuestIdentityId" name="identityId" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add Guest</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Guest Modal -->
<div class="modal fade" id="editGuestModal" tabindex="-1" role="dialog" aria-labelledby="editGuestModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="editGuestForm" action="<%= request.getContextPath() %>/admin/guest-management" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="editGuestModalLabel">Edit Guest</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="edit"/>
                    <input type="hidden" id="editGuestId" name="guestId"/>
                    <div class="form-group">
                        <label for="editGuestName">Name</label>
                        <input type="text" class="form-control" id="editGuestName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="editGuestEmail">Email</label>
                        <input type="email" class="form-control" id="editGuestEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="editGuestPhoneNum">Phone Number</label>
                        <input type="text" class="form-control" id="editGuestPhoneNum" name="phoneNum" required>
                    </div>
                    <div class="form-group">
                        <label for="editGuestIdentityId">Identity ID</label>
                        <input type="text" class="form-control" id="editGuestIdentityId" name="identityId" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function openAddGuestModal() {
        $('#addGuestModal').modal('show');
    }

    function openEditGuestModal(guestId, name, email, phoneNum, identityId) {
        document.getElementById('editGuestId').value = guestId;
        document.getElementById('editGuestName').value = name;
        document.getElementById('editGuestEmail').value = email;
        document.getElementById('editGuestPhoneNum').value = phoneNum;
        document.getElementById('editGuestIdentityId').value = identityId;
        $('#editGuestModal').modal('show');
    }
</script>