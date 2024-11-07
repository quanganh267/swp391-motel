<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Room" %>
<%@ page import="model.Categories" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<div class="site-blocks-cover inner-page-cover overlay" style="background-image: url('images/hero_bg_1.jpg');"
     data-aos="fade" data-stellar-background-ratio="0.5" data-aos="fade">
    <div class="container">
        <div class="row align-items-center justify-content-center">
            <div class="col-md-7 text-center" data-aos="fade-up" data-aos-delay="400">
                <h1 class="text-white">Motel</h1>
            </div>
        </div>
    </div>
</div>

<section class="ftco-section bg-light">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 ftco-animate">
                <div class="room">
                    <c:forEach var="room" items="${rooms}">
                        <div class="text p-3">
                            <h3 class="mb-3"><c:out value="${room.motelName}" /></h3>
                            <img src="<c:out value='${room.imageUrl}' />" alt="Room Image" class="img-fluid mb-3" style="width: 100%; height: auto;" />
                            <ul class="list">
                                <li><strong>Room ID:</strong> <c:out value="${room.roomId}" /></li>
                                <li><strong>Motel Name:</strong> <c:out value="${room.motelName}" /></li>
                                <li><strong>Room Type:</strong> <c:out value="${room.typeName}" /></li>
                                <li><strong>Status:</strong> <c:out value="${room.status}" /></li> 
                                <li><strong>Price:</strong> $<c:out value="${room.price}" /></li> 
                                <li><strong>Categories:</strong>
                                    <ul>
                                        <c:forEach var="category" items="${room.categories}">
                                            <li><c:out value="${category.categoryName}" />: <c:out value="${category.description}" /></li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </ul>
                            <hr>
                            <p><a href="Rent?roomID=${room.roomId}" class="btn btn-primary py-3 px-5">Rent Now</a></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="footer.jsp" %>
