<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp"></jsp:include>

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url('images/hero_bg_1.jpg');"
         data-aos="fade" data-stellar-background-ratio="0.5" data-aos="fade">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-md-7 text-center" data-aos="fade-up" data-aos-delay="400">
                    <h1 class="text-white">Reset Password</h1>
                    <p class="mb-5">Please enter the code sent to your email and your new password</p>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section border-bottom">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-6 mb-5">
                    <form action="reset-password" method="POST" class="contact-form" id="resetPasswordForm">
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label class="font-weight-bold" for="code">Verification Code</label>
                                <input type="text" id="code" name="code" class="form-control" placeholder="Enter verification code" required>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label class="font-weight-bold" for="newPassword">New Password</label>
                                <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="Enter new password" required>
                                <small id="passwordError" style="color:red; display:none;">Password cannot be empty.</small>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label class="font-weight-bold" for="confirmPassword">Confirm Password</label>
                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Re-enter new password" required>
                                <small id="confirmPasswordError" style="color:red; display:none;">Passwords do not match.</small>
                            </div>
                        </div>

                    <div class="row form-group">
                        <div class="col-md-12" style="text-align: center">
                            <input type="submit" value="Reset Password" class="btn btn-primary py-3 px-4">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
    <script>
        document.getElementById("resetPasswordForm").addEventListener("submit", function (event) {
            // Get the values of password and confirm password fields
            var password = document.getElementById("newPassword").value.trim();
            var confirmPassword = document.getElementById("confirmPassword").value.trim();

            var valid = true;

            // Check if password is empty
            if (password === "") {
                document.getElementById("passwordError").style.display = "block";
                valid = false;
            } else {
                document.getElementById("passwordError").style.display = "none";
            }

            // Check if confirm password matches password
            if (confirmPassword !== password) {
                document.getElementById("confirmPasswordError").style.display = "block";
                valid = false;
            } else {
                document.getElementById("confirmPasswordError").style.display = "none";
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
