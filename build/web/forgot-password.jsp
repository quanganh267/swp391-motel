

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp"></jsp:include>

    <div class="site-mobile-menu">
        <div class="site-mobile-menu-header">
            <div class="site-mobile-menu-close mt-3">
                <span class="icon-close2 js-menu-toggle"></span>
            </div>
        </div>
        <div class="site-mobile-menu-body"></div>
    </div> <!-- .site-mobile-menu -->

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url('images/hero_bg_1.jpg'); "
         data-aos="fade" data-stellar-background-ratio="0.5" data-aos="fade">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-md-7 text-center" data-aos="fade-up" data-aos-delay="400">
                    <h1 class="text-white">Forgot Password</h1>
                    <p class="mb-5">Welcome to our system</p>
                </div>
            </div>
        </div>
    </div>


    <div class="site-section border-bottom">
        <div class="container">
            <div class="row justify-content-center" style="display: flex; justify-content: center;">
                <div class="col-md-6 col-lg-6 mb-5" style="width: 60%;">
                    <form action="forgot-password" method="POST" class="contact-form" id="loginForm">
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label class="font-weight-bold" for="email">Email</label>
                                <input type="email" id="email" name="email" class="form-control" placeholder="Enter your email address">
                                <small id="emailError" style="color:red; display:none;">Please enter a valid email address.</small>
                            </div>
                        </div>
                        <hr>

                        <div class="row form-group">
                            <div class="col-md-12" style="text-align: right">
                                Don't have account?  <a href="register">Create new</a>
                            </div>
                        </div>
                        <hr>
                        <div class="row form-group">
                            <div style="text-align: center" class="col-md-12">
                                <input type="submit" value="Submit" class="btn btn-primary py-3 px-4">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div><!-- Include Toastify CSS and JS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>


    <script>
        document.getElementById("loginForm").addEventListener("submit", function (event) {
            var email = document.getElementById("email").value;
            var emailError = document.getElementById("emailError");
            var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

            if (!emailPattern.test(email)) {
                // Show error message
                emailError.style.display = "block";
                // Prevent form submission
                event.preventDefault();
            } else {
                // Hide error message
                emailError.style.display = "none";
            }
        });

    <% if (session.getAttribute("notificationErr") != null) { %>
        Toastify({
            text: "<%= session.getAttribute("notificationErr") %>",
            duration: 4000, // 4 seconds
            close: true,
            gravity: "top", // Display at the top
            position: "right", // Right side of the screen
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)"
        }).showToast();
    <% session.removeAttribute("notificationErr"); %><!-- Clear session attribute after showing the toast -->
    <% } else if (session.getAttribute("notification") != null) { %>
        Toastify({
            text: "<%= session.getAttribute("notification") %>",
            duration: 4000, // 4 seconds
            close: true,
            gravity: "top", // Display at the top
            position: "right", // Right side of the screen
            backgroundColor: "linear-gradient(to right, #4CAF50, #81C784)" // Success message color
        }).showToast();
    <% session.removeAttribute("notification"); %><!-- Clear session attribute after showing the toast -->
    <% } %>
</script>
<jsp:include page="footer.jsp"></jsp:include>