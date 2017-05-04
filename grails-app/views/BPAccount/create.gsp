<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Create Account</title>
</head>
<body>
<div class="container-fluid">

    <g:form controller="BPAccount" action="save" method="post">
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name">
            </div>
            <div class="form-group">
                <label for="type">Type</label>
                <select name="type" required="" id="type" >
                    <option value="SAVINGS" >SAVINGS</option>
                    <option value="CHECKING" >CHECKING</option>
                    <option value="LOAN" >LOAN</option>
                    <option value="CREDIT" >CREDIT</option>
                    <option value="ASSET" >ASSET</option>
                </select>
            </div>
            <div class="form-group">
                <label for="balance">Balance</label>
                <input type="text" class="form-control" id="balance" name="balance" placeholder="Balance">
            </div>

            <input type="hidden" class="form-control" name="scenario.id" id="scenario" placeholder="Scenario" value="${params.scenario.id}">
            <input type="hidden" class="form-control" name="user.id" id="user" placeholder="User" value="${params.user.id}">
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save btn" value="Create" />
        </fieldset>
    </g:form>

</div>
</body>
</html>
