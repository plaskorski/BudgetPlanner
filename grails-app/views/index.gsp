<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>BudgetPlanner</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">
            <h1>Welcome to BudgetPlanner!</h1>
            <p>
                BudgetPlanner is a web application that tracks your income and expenses, allowing you to create budgets
                down to the daily level. Check out the tutorial section, or click Register to get started!
            </p>
            <form name="register" action="${createLink(controller:'user',action:'create') }">
                <input type="submit" class="btn btn-default btn-primary" value="Register">
            </form>
    </div>
</div>
</body>
</html>
