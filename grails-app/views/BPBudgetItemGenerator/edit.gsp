<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Edit Repeat Transaction</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Edit One-Time Transaction</h4>
        </div>
        <div class="panel panel-body">
    <g:form controller="BPBudgetItemGenerator" action="update" id="${BPBudgetItemGenerator.id}" method="post">
        <input type="hidden" name="_method" value="PUT" id="_method" />
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${BPBudgetItemGenerator.name}">
            </div>

            <div class="form-group">
                <label for="startDate">Start Date</label>
                <joda:datePicker name="startDate" value="${BPBudgetItemGenerator.startDate}"/>
            </div>
            <div class="form-group">
                <label for="endDate">End Date</label>
                <joda:datePicker name="endDate" value="${BPBudgetItemGenerator.endDate}"/>
            </div>
            <div class="form-group">
                <label for="intervalValue">Every</label>
                <g:select id="intervalValue" name="intervalValue" from="${1..5}" value="${BPBudgetItemGenerator.intervalValue}"/>
            </div>
            <div class="form-group">
                <label for="intervalType">Interval</label>
                <g:select id="intervalType" name="intervalType" from="${budgetplanner.BPBudgetItemGenerator.IntervalType}" value="${BPBudgetItemGenerator.intervalType}" />
            </div>
            <div class="form-group">
                <label for="amount">Amount</label>
                <input type="text" class="form-control" id="amount" name="amount" placeholder="Amount" value="${BPBudgetItemGenerator.amount}">
            </div>
            <div class="form-group">
                <label for="fromAccount">FromAccount</label>
                <g:select name="fromAccount" from="${BPBudgetItemGenerator.scenario.accounts}" value="${BPBudgetItemGenerator.fromAccount?.id}" optionKey="id" optionValue="name" noSelection="${['null':'NA']}" />
            </div>
            <div class="form-group">
                <label for="toAccount">ToAccount</label>
                <g:select name="toAccount" from="${BPBudgetItemGenerator.scenario.accounts}"  value="${BPBudgetItemGenerator.toAccount?.id}" optionKey="id" optionValue="name" noSelection="${['null':'NA']}" />
            </div>
            <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${BPBudgetItemGenerator.user.id}">
            <input type="hidden" class="form-control" name="scenario.id" id="scenario" placeholder="Scenario" value="${BPBudgetItemGenerator.scenario.id}">
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save btn" value="Update" />
        </fieldset>
    </g:form>
        </div>
    </div>
</div>
<div class="container col-md-4"></div>
</body>
</html>
