<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Edit One-Time Transaction</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Edit One-Time Transaction</h4>
        </div>
        <div class="panel panel-body">
    <g:form controller="BPBudgetItem" action="update" id="${BPBudgetItem.id}" method="post">
        <input type="hidden" name="_method" value="PUT" id="_method" />
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${BPBudgetItem.name}">
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <joda:datePicker name="date" value="${BPBudgetItem.date}"/>
            </div>
            <div class="form-group">
                <label for="amount">Amount</label>
                <input type="text" class="form-control" id="amount" name="amount" placeholder="Amount" value="${BPBudgetItem.amount}">
            </div>
            <div class="form-group">
                <label for="fromAccount">FromAccount</label>
                <g:select name="fromAccount" from="${BPBudgetItem.scenario.accounts}" value="${BPBudgetItem.fromAccount?.id}" optionKey="id" optionValue="name" noSelection="${['null':'NA']}" />
            </div>
            <div class="form-group">
                <label for="toAccount">ToAccount</label>
                <g:select name="toAccount" from="${BPBudgetItem.scenario.accounts}"  value="${BPBudgetItem.toAccount?.id}" optionKey="id" optionValue="name" noSelection="${['null':'NA']}" />
            </div>
            <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${BPBudgetItem.user.id}">
            <input type="hidden" class="form-control" name="scenario.id" id="scenario" placeholder="Scenario" value="${BPBudgetItem.scenario.id}">
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
