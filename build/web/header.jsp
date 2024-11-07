<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Motel</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito+Sans:200,300,400,700,900|Oswald:400,700"> 
        <link rel="stylesheet" href="fonts/icomoon/style.css">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="css/mediaelementplayer.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">
        <link rel="stylesheet" href="css/fl-bigmug-line.css">


        <link rel="stylesheet" href="css/aos.css">

        <link rel="stylesheet" href="css/style.css">

    </head>
    <body>
        <div class="site-wrap">

            <div class="site-navbar mt-4">
                <div class="container py-1">
                    <div class="row align-items-center">
                        <div class="col-8 col-md-8 col-lg-4">
                            <h1 class="mb-0"><a href="home" class="text-white h2 mb-0"><strong>Motel<span class="text-primary">.</span></strong></a></h1>
                        </div>
                        <div class="col-4 col-md-4 col-lg-8">
                            <nav class="site-navigation text-right text-md-right" role="navigation">

                                <div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3"><a href="#" class="site-menu-toggle js-menu-toggle text-white"><span class="icon-menu h3"></span></a></div>

                                <ul class="site-menu js-clone-nav d-none d-lg-block">
                                    <li class="active">
                                        <a href="home">Home</a>
                                    </li>
                                    <li><a href="about">About</a></li>
                                    <li><a href="qanda">Q&A</a></li>
                                    <li class="has-children">
                                        <a href="Motel">Motel</a>
                                        <ul class="dropdown arrow-top">
                                            <li><a href="Motel">Motel</a></li>
                                            <li><a href="Room">Rooms</a></li>
                                            <li class="has-children">
                                            </li>
                                        </ul>
                                        

                                    </li>
                                    <c:if test="${not empty account}">
                                        <li class="has-children">
                                            <a href="profile">${account.name}</a>
                                            <ul class="dropdown arrow-top">
                                                <li><a href="#">Change password</a></li>
                                                <li><a href="Feedback.jsp">Feed back</a></li>
                                                <li><a href="logout">Logout</a></li>
                                            </ul>
                                        </li>
                                    </c:if>
                                    <c:if test="${ empty account}">
                                        <li><a href="login">Login</a></li>
                                        <li><a href="register">Register</a></li>
                                        </c:if>

                                </ul>
                            </nav>
                        </div>


                    </div>
                </div>
            </div>
        </div>

