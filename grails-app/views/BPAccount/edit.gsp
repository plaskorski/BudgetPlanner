<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Edit Account</title>
</head>
<body>
<div class="container col-md-4"></div>
<div class="container col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Edit Account</h4>
        </div>
        <div class="panel panel-body">
    <g:form controller="BPAccount" action="update" id="${BPAccount.id}" method="post">
        <input type="hidden" name="_method" value="PUT" id="_method" />
        <fieldset class="form">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${BPAccount.name}">
            </div>
            <div class="form-group">
                <label for="type">Type</label>
                <select name="type" required="" id="type" value="${BPAccount.type}">
                    <option value="SAVINGS" >SAVINGS</option>
                    <option value="CHECKING" >CHECKING</option>
                    <option value="LOAN" >LOAN</option>
                    <option value="CREDIT" >CREDIT</option>
                    <option value="ASSET" >ASSET</option>
                </select>
            </div>
            <div class="form-group">
                <label for="balance">Balance</label>
                <input type="text" class="form-control" id="balance" name="balance" placeholder="Balance" value="${BPAccount.balance}">
            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="save" class="save btn" value="Update" />
        </fieldset>
    </g:form>
        </div>
</div>
<div class="container col-md-4"></div>
</body>
</html>
