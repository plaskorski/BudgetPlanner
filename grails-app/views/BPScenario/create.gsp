<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Create Scenario</title>
    </head>
    <body>
        <div class="container-fluid">
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
                    <g:datePicker name="startDate" value="${new Date()}" precision="day" noSelection="['':'-Choose-']"/>
                </div>
                <div class="form-group">
                    <label for="endDate">End Date</label>
                    <g:datePicker name="endDate" value="${new Date()+1}" precision="day" noSelection="['':'-Choose-']"/>
                </div>
                <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${params.user.id}">
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save btn" value="Create" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
