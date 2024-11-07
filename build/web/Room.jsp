<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Room" %>
<%@ page import="java.util.List" %>
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
        <!-- Advanced Search Form Section -->
        <div class="row justify-content-center mb-5 pb-3">
            <div class="col-md-8">
                <div class="search-wrap-1 bg-light">
                    <h3 class="heading mb-4">Advanced Search</h3>
                    <form method="post" action="Room">
                        <div class="row">
                            <div class="col-md-6 form-group">
                                <label for="motelId">Motel:</label>
                                <select id="motelId" name="motelId" class="form-control">
                                    <option value="">Select Motel</option>
                                    <option value="1">Quang Anh Motel</option>
                                    <option value="2">Hung brother motel</option>
                                    <option value="3">Grandma Thanh Motel</option>
                                    <option value="4">Mr.Duc motel</option>
                                </select>
                            </div>
                            <div class="col-md-6 form-group">
                                <label for="typeId">Room Type:</label>
                                <select id="typeId" name="typeName" class="form-control">
                                    <option value="">Select Room Type</option>
                                    <option value="Single Room">Single Room</option>
                                    <option value="Double Room">Double Room</option>
                                    <option value="Family Room">Family Room</option>
                                </select>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary py-3 px-5">Search</button>
                    </form>
                </div>
            </div>
        </div>

      <!-- Room List Section -->
<div class="row">
    <c:forEach var="room" items="${rooms}">
        <div class="col-md-12 col-lg-6 mb-4" data-aos="fade-up" data-aos-delay="100">
            <div class="room">
                <a href="/SingleRoom?roomID=${room.roomId}" class="img d-flex justify-content-center align-items-center"
                   style="background-image: url(${room.imageUrl}); background-size: cover; background-position: center; height: 300px;">
                    <div class="icon d-flex justify-content-center align-items-center">
                        <span class="icon-search2"></span>
                    </div>
                </a>
                <div class="text p-4 text-center">
                    <h3 class="mb-3"><a href="/SingleRoom?roomID=${room.roomId}">${room.roomId}</a></h3>
                    <ul class="list-unstyled">
                        <li><span>Motel:</span> <c:out value="${room.motelName}" /></li>
                        <li><span>Room Type:</span> <c:out value="${room.typeName}" /></li>
                        <li><span>Status:</span> <c:out value="${room.status}" /></li>
                        <li><span>Price:</span> $<c:out value="${room.price}" /></li>
                    </ul>
                    <hr>
                    <p class="pt-1">
                        <a href="/SingleRoom?roomID=${room.roomId}" class="btn-custom">Rent Now <span class="icon-long-arrow-right"></span></a>
                    </p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>


    </div>
</section>


<%@ include file="footer.jsp" %>
