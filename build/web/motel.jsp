<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Motel" %>
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
                    <form method="post" action="Motel">
                        <div class="row">
                            <div class="col-md-6 form-group">
                                <label for="city">City:</label>
                                <select id="city" name="city" class="form-control">
                                    <option value="">Select City</option>
                                    <option value="City1">City1</option>
                                    <option value="City2">City2</option>
                                    <option value="City3">City3</option>
                                </select>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary py-3 px-5">Search</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Motel List Section -->
        <div class="row">
            <c:forEach var="motel" items="${motels}">
                <div class="col-md-12 col-lg-6 mb-4" data-aos="fade-up" data-aos-delay="100">
                    <div class="room">
                        <a href="Motel?motelID=${motel.motelId}" class="img d-flex justify-content-center align-items-center"
                           style="background-image: url(${motel.imageUrl}); background-size: cover; background-position: center; height: 300px;">
                            <div class="icon d-flex justify-content-center align-items-center">
                                <span class="icon-search2"></span>
                            </div>
                        </a>
                        <div class="text p-4 text-center">
                            <h3 class="mb-3"><a href="/SingleMotel?motelID=${motel.motelId}">${motel.motelName}</a></h3>
                            <ul class="list-unstyled">
                                <li><span>Description:</span> <c:out value="${motel.description}" /></li>
                                <li><span>Address:</span> <c:out value="${motel.address}" /></li>
                                <li><span>City:</span> <c:out value="${motel.city}" /></li>
                                <li><span>Number of Rooms:</span> <c:out value="${motel.numberOfRoom}" /></li>
                            </ul>
                            <hr>
                            <p class="pt-1">
                                <a href="/SingleMotel?motelID=${motel.motelId}" class="btn-custom">View Details <span class="icon-long-arrow-right"></span></a>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<%@ include file="footer.jsp" %>
