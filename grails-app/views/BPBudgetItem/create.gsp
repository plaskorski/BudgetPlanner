<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Create One-Time Transaction</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Create One-Time Transaction</h4>
        </div>
        <div class="panel panel-body">
    <g:form controller="BPBudgetItem" action="save" method="post">
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name">
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <joda:datePicker name="date" value="${BPBudgetItem.scenario.startDate}"/>
            </div>
            <div class="form-group">
                <label for="amount">Amount</label>
                <input type="text" class="form-control" id="amount" name="amount" placeholder="Amount">
            </div>
            <div class="form-group">
                <label for="fromAccount">FromAccount</label>
                <g:select name="fromAccount" from="${BPBudgetItem.scenario.accounts}" optionKey="id" optionValue="name" noSelection="${['null':'NA']}"/>
            </div>
            <div class="form-group">
                <label for="toAccount">ToAccount</label>
                <g:select name="toAccount" from="${BPBudgetItem.scenario.accounts}"  optionKey="id" optionValue="name" noSelection="${['null':'NA']}"/>
            </div>
            <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${params.user.id}">
            <input type="hidden" class="form-control" name="scenario.id" id="scenario" placeholder="Scenario" value="${params.scenario.id}">
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
