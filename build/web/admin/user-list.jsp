 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>${title}</title>
<jsp:include page="header.jsp"></jsp:include>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                <form class="form-inline">
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>
                </form>

                <h1 class="h3 mb-2 text-gray-800">User Management</h1>

                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </li>
                    <div class="topbar-divider d-none d-sm-block"></div>
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

            <div class="card shadow mb-4 ">
                <div class="card-header py-3 row">
                    <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search col-md-6" action="user-management" method="GET">
                        <div class="input-group">
                            <input type="text" class="form-control bg-light border-0 small" placeholder="Enter name to search..."
                                   aria-label="Search" aria-describedby="basic-addon2" name="search" value="${param.search}">
                            <input type="hidden" name="status" value="${param.status}">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                    <ul class="nav nav-pills">
                        <li class="nav-item">
                            <a class="nav-link ${param.status == null || param.status == '' ? 'active' : ''}" href="?search=${param.search}">ALL</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.status == 'ACTIVE' ? 'active' : ''}" href="?status=ACTIVE&search=${param.search}">ACTIVE</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.status == 'INACTIVE' ? 'active' : ''}" href="?status=INACTIVE&search=${param.search}">INACTIVE</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.status == 'BLOCKED' ? 'active' : ''}" href="?status=BLOCKED&search=${param.search}">BLOCKED</a>
                        </li>
                    </ul>

                </div>

                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered"  width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="a" items="${list}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>

                                        <td>${a.name}</td>
                                        <td>${a.email}</td>
                                        <td>${a.phone}</td>
                                        <td>${a.role}</td>
                                        <td>
                                            <span class="
                                                  ${a.status == 'ACTIVE' ? 'badge badge-success' : ''}
                                                  ${a.status == 'INACTIVE' ? 'badge badge-warning' : ''}
                                                  ${a.status == 'BLOCKED' ? 'badge badge-danger' : ''}
                                                  ">
                                                ${a.status}
                                            </span>
                                        </td>
                                        <td>
                                            <!-- Block/Unblock Button with hidden form -->
                                            <form action="user-management" method="POST">
                                                <input type="hidden" name="userId" value="${a.id}">
                                                <input type="hidden" name="action" value="${a.status == 'ACTIVE' ? 'BLOCK' : 'UNBLOCK'}">

                                                <!-- Button triggers modal -->
                                                <button type="button" class="btn ${a.status == 'ACTIVE' ? 'btn-danger' : 'btn-success'}" data-toggle="modal" 
                                                        data-target="#confirmModal${a.id}">
                                                    ${a.status == 'ACTIVE' ? '<i class="fas fa-lock"></i>' : '<i class="fas fa-lock-open"></i>'}
                                                </button>

                                                <!-- Modal Confirmation -->
                                                <div class="modal fade" id="confirmModal${a.id}" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel${a.id}" aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="confirmModalLabel${a.id}">Confirm ${a.status == 'ACTIVE' ? 'Block' : 'Unblock'} User</h5>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                Are you sure you want to ${a.status == 'ACTIVE' ? 'block' : 'unblock'} this user?
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                <!-- Submit the form to perform the action -->
                                                                <button type="submit" class="btn ${a.status == 'ACTIVE' ? 'btn-danger' : 'btn-success'}">
                                                                    Yes, ${a.status == 'ACTIVE' ? 'Block' : 'Unblock'}
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </td>

                                    </tr>
                                <div class="modal fade" id="confirmBlockModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Confirm Action</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p id="modalMessage">Are you sure you want to <span id="modalAction"></span> the user <strong><span id="modalUsername"></span></strong>?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form action="user-management" method="POST">
                                                    <input type="hidden" name="userId" id="modalUserId">
                                                    <input type="hidden" name="action" id="modalActionInput">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                    <button type="submit" class="btn btn-primary" id="confirmButton">Confirm</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                            </tbody>
                        </table>

                        <nav class="mt-3">
                            <ul class="pagination justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage - 1}&status=${param.status}&search=${param.search}">Previous</a>
                                    </li>
                                </c:if>
                                <c:forEach var="pageNum" begin="1" end="${totalPages}">
                                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${pageNum}&status=${param.status}&search=${param.search}">${pageNum}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage + 1}&status=${param.status}&search=${param.search}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="footer.jsp"></jsp:include>

