<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>BudgetPlanner - Tutorial</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>BudgetPlanner Tutorial</h1>
        <br/>
        <h4>Getting Started</h4>
        <br/>
        <p>
            From the home page either register as a new user or click the login button in the top right corner and use
            username "user", password "secret" for a user with pre-loaded data.
        </p>
        <br/>
        <h4>Scenarios</h4>
        <br/>
        <p>
            From here, click on the "Scenarios" tab to see your scenarios. Scenarios represent a version of a budget.
            You can have many scenarios. Click the plus sign to create a new scenario, or click on an existing scenario
            to see it in detail. The detailed scenario page is the main part of this app. You should see the "Overview",
            "Chart", "Accounts", "Transactions", and "Budget" panels. These panels are responsive.
        </p>
        <br/>
        <h4>Accounts</h4>
        <br/>
        <p>
            Accounts represent any account containing cash, positive or negative. You can have many accounts. Click the
            plus sign button to add a new account, or click the pencil or X buttons to edit or delete an account.
        </p>
        <br/>
        <h4>Transactions</h4>
        <br/>
        <p>
            Transactions represent money leaving and/or entering 1 or 2 accounts at a specific time. Transactions must
            have at least one of the FromAccount or ToAccount fields populated, and the date must be within the scenario
             start date and end date. There are two types of transactions, one-time transactions and repeating transactions.
            Click the plus sign button on either type of transaction to create a new one, or click the pencil or X buttons
            to edit or delete the transaction. Repeating transactions have a start date, an interval number, and an interval type.
            To create a monthly transaction on the 1st of the month, set the start date to a date with the 1st of a given
            month, set the interval value to 1, and the interval type to MONTH.
        </p>
        <br/>
        <h4>Chart</h4>
        <br/>
        <p>
            The chart panel contains line plots of your account balances over time. Click on the corresponding tab to
            see the chart. As you create/update/delete accounts and transactions, these charts will update!
        </p>
        <br/>
        <h4>Budget</h4>
        <br/>
        <p>
            The budget panel contains a table showing all transactions over time with the account balances after each
            transaction has been executed. As you create/update/delete accounts and transactions, this table will update!
        </p>
    </div>
</div>
</body>
</html>
