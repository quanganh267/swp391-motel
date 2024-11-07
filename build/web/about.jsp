<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Post" %>
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

<div class="container">
    <c:forEach var="post" items="${posts}">
        <div class="featured-property-half d-flex">
            <div class="image" style="background-image: url('images/hero_bg_1.jpg')"></div>
            <div class="text">
                <h2>Property Information</h2>
                <p class="mb-5">${post.content}</p>
                <ul class="property-list-details mb-5">
                    <li class="text-black">Property Name: <strong class="text-black">${post.title}</strong></li>
                    <li>Room: <strong>2</strong></li>
                    <li>Total Area: <strong>482 Square Feets</strong></li>
                    <li>Category: <strong>${post.category}</strong></li>
                </ul>
<!--                <p><a href="#" class="btn btn-primary px-4 py-3">Get Details</a></p>-->
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jsp" %> 