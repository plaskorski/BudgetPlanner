<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Scenario View</title>
    </head>
    <body>


        <div class="container-fluid col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Overview</h4>
                </div>
                <div class="panel-body">
                    <div class="btn-group" role="group" aria-label="buttons">
                        <button type="button" class="btn btn-primary">Edit</button>
                        <button type="button" class="btn btn-default" disabled="disabled">Delete</button>
                    </div>
                    <table class="table table-hover">
                        <tbody>
                            <tr>
                                <th scope="row">Name</th>
                                <td>${BPScenario.name}</td>
                            </tr>
                            <tr>
                                <th scope="row">Description</th>
                                <td>${BPScenario.description}</td>
                            </tr>
                            <tr>
                                <th scope="row">Start Date</th>
                                <td>${BPScenario.startDate.format("M/dd/yy")}</td>
                            </tr>
                            <tr>
                                <th scope="row">End Date</th>
                                <td>${BPScenario.endDate.format("M/dd/yy")}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="container-fluid col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Chart</h4>
                </div>
                <div class="panel-body">
                    <p>This is where the budget chart goes</p>
                </div>
            </div>
        </div>


        <div class="container-fluid col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Budget</h4>
                </div>
                <div class="panel-body">
                    <table class="table table-hover table-responsive">
                        <thead>
                            <tr>
                                <g:each in="${BPScenario.table.getHeader()}" var="field">
                                    <th>${field}</th>
                                </g:each>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${BPScenario.table.rows}" var="row">
                            <tr>
                                <th scope="row">${row.date.format("M/dd/yy")}</th>
                                <td>${row.name}</td>
                                <td>${'$ '+String.format('%.2f',row.amount/100)}</td>
                                <g:each in="${row.getValues()}" var="value">
                                    <td>${'$ '+String.format('%.2f',value/100)}</td>
                                </g:each>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="container-fluid col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Accounts</h4>
                </div>
                <div class="panel-body">
                    <div class="btn-group" role="group" aria-label="buttons">
                        <button type="button" class="btn btn-primary">Create</button>
                        <button type="button" class="btn btn-default" disabled="disabled">Delete</button>
                    </div>
                    <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Starting Balance</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${BPScenario.accounts}" var="account">
                            <tr>
                                <th scope="row">${account.name}</th>
                                <td>${account.type}</td>
                                <td>${'$ '+String.format('%.2f',account.balance/100)}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="container-fluid col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Transactions</h4>
                </div>

                <div class="container-fluid">
                <h4>One-Time</h4>
                <table class="table table-hover table-responsive">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>FromAccount</th>
                        <th>ToAccount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${BPScenario.transactions}" var="transaction">
                        <tr>
                            <th scope="row">${transaction.name}</th>
                            <td>${transaction.date.format("M/dd/yy")}</td>
                            <td>${'$ '+String.format('%.2f',transaction.amount/100)}</td>
                            <td>${transaction.fromAccount?.name}</td>
                            <td>${transaction.toAccount?.name}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                </div>

                <div class="container-fluid">
                <h4>Repeating</h4>
                    <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Amount</th>
                            <th>FromAccount</th>
                            <th>ToAccount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${BPScenario.generators}" var="generator">
                            <tr>
                                <th scope="row">${generator.name}</th>
                                <td>${generator.startDate.format("M/dd/yy")}</td>
                                <td>${'$ '+String.format('%.2f',generator.amount/100)}</td>
                                <td>${generator.fromAccount?.name}</td>
                                <td>${generator.toAccount?.name}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </body>
</html>
