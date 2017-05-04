<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><g:layoutTitle default="Grails"/></title>
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="fixedTop.css"/>
    <asset:stylesheet src="sticky-footer-navbar.css"/>
    <g:layoutHead/>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbarText" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <g:link class="navbar-brand" url="/">
            <asset:image src="dollars-512.png" width="30" height="30" class="d-inline-block align-top" alt="dollar bill icon"/>
        </g:link>
    </div>
    <div class="collapse navbar-collapse" id="navbarText">
        <ul class="nav navbar-nav">
            <li><g:link class="nav-link" url="/">Home</g:link></li>
            <sec:ifAllGranted roles="ROLE_ADMIN">
                <li><g:link class="nav-link" controller="User" action="index">Users</g:link></li>
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_ADMIN">
                <sec:ifLoggedIn>
                    <li><g:link class="nav-link" controller="BPScenario" action="index">Scenarios</g:link></li>
                </sec:ifLoggedIn>
                <li><g:link class="nav-link" url="/about">About</g:link></li>
                <li><g:link class="nav-link" url="/blog">Blog</g:link></li>
                <li><g:link class="nav-link" url="/tutorial">Tutorial</g:link></li>
            </sec:ifNotGranted>
        </ul>
        <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <sec:ifLoggedIn>
                    <form name="logout" method="POST" action="${createLink(controller:'logout') }">
                        <input type="submit" class="btn btn-default navbar-btn" value="Logout">
                    </form>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <form name="login" action="${createLink(controller:'login') }">
                        <input type="submit" class="btn btn-default navbar-btn" value="Login">
                    </form>
                </sec:ifNotLoggedIn>
            </li>
        </ul>

    </div>
    </div>
</nav>
<g:layoutBody/>
<asset:javascript src="jquery-2.2.0.min.js"/>
<asset:javascript src="bootstrap.min.js"/>
<asset:javascript src="custom.js"/>
</body>
</html>