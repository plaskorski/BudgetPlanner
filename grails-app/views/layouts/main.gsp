<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="fixedTop.css"/>
    <g:layoutHead/>
</head>
<body>
<nav class="navbar fixed-top navbar-toggleable navbar-inverse bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <g:link class="navbar-brand" url="/">
        <asset:image src="dollars-512.png" width="30" height="30" class="d-inline-block align-top" alt="dollar bill icon"/>
        BudgetPlanner
    </g:link>
    <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
            <sec:ifLoggedIn>
                <li class="nav-item">
                    <g:link class="nav-link" controller="BPScenario" action="index">Scenarios</g:link>
                </li>
            </sec:ifLoggedIn>
            <li class="nav-item">
                <g:link class="nav-link" url="/about">About</g:link>
            </li>
            <li class="nav-item">
                <g:link class="nav-link" url="/blog">Blog</g:link>
            </li>
            <li class="nav-item">
                <g:link class="nav-link" url="/tutorial">Tutorial</g:link>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item">
                <sec:ifLoggedIn>
                    <form name="logout" method="POST" action="${createLink(controller:'logout') }">
                        <input type="submit" class="btn btn-link nav-link" value="Logout">
                    </form>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <form name="logout" action="${createLink(controller:'login') }">
                        <input type="submit" class="btn btn-link nav-link" value="Login">
                    </form>
                </sec:ifNotLoggedIn>
            </li>
        </ul>
    </div>
</nav>
<g:layoutBody/>
<asset:javascript src="jquery-2.2.0.min.js"/>
<asset:javascript src="bootstrap.min.js"/>
<asset:javascript src="custom.js"/>
</body>
</html>