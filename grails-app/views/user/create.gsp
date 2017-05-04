<%@ page import="org.joda.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Register</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Register</h4>
        </div>
        <div class="panel panel-body">
            <g:form controller="User" action="save" method="post">
                <fieldset class="form">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="birthDate">Birth Date</label>
                        <joda:datePicker name="birthDate" value="${new LocalDate()}"/>
                    </div>
                    <div class="form-group">
                        <label for="username">User Name</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="User Name">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save btn" value="Create" />
                </fieldset>
            </g:form>
        </div>
    </div>
</div>
<div class="container col-md-4"></div>
</body>
</html>
