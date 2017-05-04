<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Edit Scenario</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Edit Scenario</h4>
        </div>
        <div class="panel panel-body">
    <g:form controller="BPScenario" action="update" id="${BPScenario.id}" method="post">
        <input type="hidden" name="_method" value="PUT" id="_method" />
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${BPScenario.name}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" name="description" placeholder="Description" value="${BPScenario.description}">
            </div>
            <div class="form-group">
                <label for="startDate">Start Date</label>
                <joda:datePicker name="startDate" value="${BPScenario.startDate}"/>
            </div>
            <div class="form-group">
                <label for="endDate">End Date</label>
                <joda:datePicker name="endDate" value="${BPScenario.endDate}"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="save" class="save btn" value="Update" />
        </fieldset>
    </g:form>
        </div>
    </div>
</div>
<div class="container col-md-4"></div>
</body>
</html>
