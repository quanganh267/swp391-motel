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

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url('images/hero_bg_1.jpg');"
         data-aos="fade" data-stellar-background-ratio="0.5" data-aos="fade">
        <div class="container">
            <a href="register.jsp"></a>
            <div class="row align-items-center justify-content-center">
                <div class="col-md-7 text-center" data-aos="fade-up" data-aos-delay="400">
                    <h1 class="text-white">Register</h1>
                    <p class="mb-5">Welcome to our system</p>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section border-bottom">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-6 mb-5">
                    <form action="register" method="POST" class="contact-form" id="registerForm">

                        <div class="row form-group">
                            <div class="col-md-12 mb-3 mb-md-0">
                                <label class="font-weight-bold" for="fullname">Full Name</label>
                                <input type="text" name="fullname" value="${fullname}" id="fullname" class="form-control" placeholder="Enter Your Full Name">
                            <small id="fullnameError" style="color:red; display:none;">Full name is required.</small>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-12">
                            <label class="font-weight-bold" for="email">Email</label>
                            <input type="email" name="email"value="${email}" id="email" class="form-control" placeholder="Enter Your Email Address">
                            <small id="emailError" style="color:red; display:none;">Please enter a valid email address.</small>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-12">
                            <label class="font-weight-bold" for="password">Password</label>
                            <input type="password" value="${password}" name="password" id="password" class="form-control" placeholder="Enter Your Password">
                            <small id="passwordError" style="color:red; display:none;">Password must be at least 6 characters long.</small>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-12">
                            <label class="font-weight-bold" for="re-password">Re-Password</label>
                           <input type="password" value="${re_password}" name="re_password" id="re-password" class="form-control" placeholder="Re-enter Your Password">
                            <small id="rePasswordError" style="color:red; display:none;">Passwords do not match.</small>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-12">
                            <label class="font-weight-bold" for="phone">Phone Number</label>
                            <input type="text" id="phone" value="${phone}"name="phone" class="form-control" placeholder="Enter Your Phone Number">
                            <small id="phoneError" style="color:red; display:none;">Phone number must be 10 digits and start with 0.</small>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-12" style="text-align: center">
                            <input type="submit" value="Register" class="btn btn-primary py-3 px-4">
                        </div>
                    </div>
                    <div class="row form-group">

                      
                        <div class="col-md-12" style="text-align: right">
                            Already have an account?  <a href="login">Login now</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script>
    document.getElementById("registerForm").addEventListener("submit", function (event) {
        var fullName = document.getElementById("fullname").value.trim();
        var email = document.getElementById("email").value.trim();
        var password = document.getElementById("password").value.trim();
        var rePassword = document.getElementById("re-password").value.trim();
        var phone = document.getElementById("phone").value.trim();

        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        var phonePattern = /^0\d{9}$/;  // Ensure phone number starts with 0 and is exactly 10 digits
        var valid = true;

        // Full Name validation
        if (fullName === "") {
            document.getElementById("fullnameError").style.display = "block";
            valid = false;
        } else {
            document.getElementById("fullnameError").style.display = "none";
        }

        // Email validation
        if (!emailPattern.test(email)) {
            document.getElementById("emailError").style.display = "block";
            valid = false;
        } else {
            document.getElementById("emailError").style.display = "none";
        }

        // Password validation
        if (password.length < 6) {
            document.getElementById("passwordError").style.display = "block";
            valid = false;
        } else {
            document.getElementById("passwordError").style.display = "none";
        }

        // Re-Password validation
        if (password !== rePassword) {
            document.getElementById("rePasswordError").style.display = "block";
            valid = false;
        } else {
            document.getElementById("rePasswordError").style.display = "none";
        }

        // Phone number validation
        if (!phonePattern.test(phone)) {
            document.getElementById("phoneError").style.display = "block";
            valid = false;
        } else {
            document.getElementById("phoneError").style.display = "none";
        }

        // Prevent form submission if validation fails
        if (!valid) {
            event.preventDefault();
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

