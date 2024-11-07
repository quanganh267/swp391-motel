<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"></jsp:include>

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
    <h1>Q&A</h1>
    <form action="qanda" method="post">
        <input type="hidden" name="guestId" value="1" /> <!-- Example guest ID -->
        <div class="form-group">
            <label for="guestName">Guest Name:</label>
            <input type="text" class="form-control" id="guestName" name="guestName" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="question">Question:</label>
            <textarea class="form-control" id="question" name="question" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <!-- New section to display all questions and answers -->
    <div class="mt-5">
        <h2>Questions and Answers</h2>
        <c:forEach var="qanda" items="${qAndAList}">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Question:</h5>
                    <p class="card-text">${qanda.question}</p>
                    <h5 class="card-title">Answer:</h5>
                    <p class="card-text">${qanda.answer}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include> 