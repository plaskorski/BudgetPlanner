<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>BudgetPlanner - Notes</title>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>BudgetPlanner Notes</h1>
        <br/>
        <h4>Implementation Details</h4>
        <br/>
        <ul>
            <li><p>Grails 3.2.6 (to avoid issue with spring security)</p></li>
            <li><p>Spring Security</p></li>
            <li><p>Jodatime Grails plugin for easy calendar math</p></li>
            <li><p>Bootstrap for front end</p></li>
            <li><p>Plotly.js for responsive, interactive charts</p></li>
        </ul>
        <br/>
        <h4>Challenges</h4>
        <br/>
        <ul>
            <li><p>Grails is a powerful black box. Error messages are mostly useless.</p></li>
            <li><p>Java/Groovy Date class is terrible. I had to refactor to Jodatime.</p></li>
            <li><p>Spring security gave me issues when assigning role to user in the save action of the controller. I had to upgrade my grails version.</p></li>
            <li><p>Difficult to modify controllers to restrict to user-owned data. There must be a better way to do this.</p></li>
        </ul>
        <br/>
        <h4>Known Bugs and Limitations</h4>
        <br/>
        <ul>
            <li><p>Errors during creation/editing of domain classes just redirects to the same view. No error messages are printed.</p></li>
            <li><p>Changing of scenario start and end dates does not update any transactions, so they will spill out of the scenario period. This messes up the budget table.</p></li>
            <li><p>Using spaces in account names will result in the charts not working. This is due to div Ids being set in the grails GSP using the account name. Duplicate account names will also result in problems.</p></li>
            <li><p>Dollar amounts are in cents, not dollars. I need to add some javascript to the forms to enter and display balances with the proper format.</p></li>
            <li><p>Budget table can be very long. I need to change this so it is scrollable or collapse-able. It also doesn't do so well on small screens.</p></li>
        </ul>
    </div>
</div>
</body>
</html>
