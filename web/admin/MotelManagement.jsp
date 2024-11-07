<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"></jsp:include>
<div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
            <h1 class="h3 mb-2 text-gray-800">Motel Management</h1>
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
                    session.removeAttribute("notificationErr");
                %>
            </c:if>

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <button class="btn btn-primary" onclick="openAddMotelModal()">Add New Motel</button>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Motel ID</th>
                                    <th>Motel Name</th>
                                    <th>Description</th>
                                    <th>Address</th>
                                    <th>City</th>
                                    <th>Number of Rooms</th>
                                    <th>Image</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="motel" items="${motels}">
                                    <tr id="motel-${motel.motelId}">
                                        <td><c:out value="${motel.motelId}" /></td>
                                        <td><c:out value="${motel.motelName}" /></td>
                                        <td><c:out value="${motel.description}" /></td>
                                        <td><c:out value="${motel.address}" /></td>
                                        <td><c:out value="${motel.city}" /></td>
                                        <td><c:out value="${motel.numberOfRoom}" /></td>
                                        <td><img src="<c:out value='${motel.imageUrl}' />" alt="Motel Image" width="100" /></td>
                                        <td>
                                            <button class="btn btn-warning btn-sm" onclick="openEditMotelModal(${motel.motelId}, '${motel.motelName}', '${motel.description}', '${motel.address}', '${motel.city}', ${motel.numberOfRoom}, '${motel.imageUrl}')">Edit</button>
                                            <form action="<%= request.getContextPath() %>/admin/motel-management" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this motel?');">
                                                <input type="hidden" name="action" value="delete"/>
                                                <input type="hidden" name="motelId" value="${motel.motelId}"/>
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

<!-- Add Motel Modal -->
<div class="modal fade" id="addMotelModal" tabindex="-1" role="dialog" aria-labelledby="addMotelModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="addMotelForm" action="<%= request.getContextPath() %>/admin/motel-management" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="addMotelModalLabel">Add New Motel</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="add"/>
                    <div class="form-group">
                        <label for="addMotelName">Motel Name</label>
                        <input type="text" class="form-control" id="addMotelName" name="motelName" required>
                    </div>
                    <div class="form-group">
                        <label for="addDescription">Description</label>
                        <textarea class="form-control" id="addDescription" name="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="addAddress">Address</label>
                        <input type="text" class="form-control" id="addAddress" name="address" required>
                    </div>
                    <div class="form-group">
                        <label for="addCity">City</label>
                        <input type="text" class="form-control" id="addCity" name="city" required>
                    </div>
                    <div class="form-group">
                        <label for="addNumberOfRoom">Number of Rooms</label>
                        <input type="number" class="form-control" id="addNumberOfRoom" name="numberOfRoom" required>
                    </div>
                    <div class="form-group">
                        <label for="addImageUrl">Image URL</label>
                        <input type="text" class="form-control" id="addImageUrl" name="imageUrl" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add Motel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Motel Modal -->
<div class="modal fade" id="editMotelModal" tabindex="-1" role="dialog" aria-labelledby="editMotelModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="editMotelForm" action="<%= request.getContextPath() %>/admin/motel-management" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="editMotelModalLabel">Edit Motel</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="edit"/>
                    <input type="hidden" name="motelId" id="editMotelId"/>
                    <div class="form-group">
                        <label for="editMotelName">Motel Name</label>
                        <input type="text" class="form-control" id="editMotelName" name="motelName" required>
                    </div>
                    <div class="form-group">
                        <label for="editDescription">Description</label>
                        <textarea class="form-control" id="editDescription" name="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editAddress">Address</label>
                        <input type="text" class="form-control" id="editAddress" name="address" required>
                    </div>
                    <div class="form-group">
                        <label for="editCity">City</label>
                        <input type="text" class="form-control" id="editCity" name="city" required>
                    </div>
                    <div class="form-group">
                        <label for="editNumberOfRoom">Number of Rooms</label>
                        <input type="number" class="form-control" id="editNumberOfRoom" name="numberOfRoom" required>
                    </div>
                    <div class="form-group">
                        <label for="editImageUrl">Image URL</label>
                        <input type="text" class="form-control" id="editImageUrl" name="imageUrl" required>
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

<jsp:include page="footer.jsp"></jsp:include>

<script>
    function openAddMotelModal() {
        $('#addMotelModal').modal('show');
    }

    function openEditMotelModal(motelId, motelName, description, address, city, numberOfRoom, imageUrl) {
        document.getElementById('editMotelId').value = motelId;
        document.getElementById('editMotelName').value = motelName;
        document.getElementById('editDescription').value = description;
        document.getElementById('editAddress').value = address;
        document.getElementById('editCity').value = city;
        document.getElementById('editNumberOfRoom').value = numberOfRoom;
        document.getElementById('editImageUrl').value = imageUrl;
        $('#editMotelModal').modal('show');
    }
</script> 