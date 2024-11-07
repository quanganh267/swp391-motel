<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Q&A Management</h1>

    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Q&A List</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Guest Name</th>
                            <th>Email</th>
                            <th>Question</th>
                            <th>Answer</th>
                            <th>Created At</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="qanda" items="${qAndAList}">
                            <tr>
                                <td>${qanda.guestName}</td>
                                <td>${qanda.email}</td>
                                <td>${qanda.question}</td>
                                <td>
                                    <form action="/staff/qanda-management" method="post">
                                        <input type="hidden" name="qaId" value="${qanda.qaId}" />
                                        <textarea name="answer" class="form-control">${qanda.answer}</textarea>
                                        <button type="submit" class="btn btn-primary mt-2">Submit Answer</button>
                                    </form>
                                </td>
                                <td><fmt:formatDate value="${qanda.createdAt}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 