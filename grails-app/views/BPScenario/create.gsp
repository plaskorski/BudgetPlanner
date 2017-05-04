<%@ page import="org.joda.time.LocalDate" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Create Scenario</title>
    </head>
    <body>
    <div class="container col-md-4"></div>
    <div class="container col-md-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>Create Scenario</h4>
            </div>
            <div class="panel panel-body">
            <g:form controller="BPScenario" action="save" method="post">
                <fieldset class="form">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                </div>
                <div class="form-group">
                    <label for="startDate">Start Date</label>
                    <joda:datePicker name="startDate" value="${new LocalDate()}"/>
                    </div>
                <div class="form-group">
                    <label for="endDate">End Date</label>
                    <joda:datePicker name="endDate" value="${new LocalDate()}"/>
                </div>
                <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${params.user.id}">
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
