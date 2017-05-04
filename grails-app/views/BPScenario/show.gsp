<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Scenario View</title>
    </head>
    <div class="container-fluid">
        <div class="container-fluid col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Overview
                        <g:link controller="BPScenario" action="edit" id="${BPScenario.id}">
                            <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true" /></button>
                        </g:link>
                    </h4>
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
                                <td>${BPScenario.startDate.toString('M/dd/yy')}</td>
                            </tr>
                            <tr>
                                <th scope="row">End Date</th>
                                <td>${BPScenario.endDate.toString('M/dd/yy')}</td>
                            </tr>
                        </tbody>
                    </table>
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
</div>
<div class="container-fluid">

    <div class="container-fluid col-md-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>Accounts
                <g:link controller="BPAccount" action="create" params="['scenario.id': BPScenario.id, 'user.id': BPScenario.user.id]">
                    <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                </g:link>
                </h4>
            </div>
                <table class="table table-hover table-responsive">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Starting Balance</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${BPScenario.accounts.sort {it.id}}" var="account">
                        <tr>
                            <th scope="row">${account.name}</th>
                            <td>${account.type}</td>
                            <td>${'$ '+String.format('%.2f',account.balance/100)}</td>
                            <td>
                                <g:form controller="BPAccount" action="delete" id="${account.id}" method="post">
                                    <input type="hidden" name="_method" value="DELETE" id="_method" />
                                    <g:link controller="BPAccount" action="edit" id="${account.id}">
                                        <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </g:link>
                                    <button type="submit" class="btn btn-default btn-sm" onclick="return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                                </g:form>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
        </div>
    </div>


    <div class="container-fluid col-md-8">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>Transactions</h4>
            </div>
            <div class="panel-body">

                <div class="container-fluid">
                    <h4>One-Time
                        <g:link controller="BPBudgetItem" action="create" params="['scenario.id': BPScenario.id, 'user.id': BPScenario.user.id]">
                            <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                        </g:link>
                    </h4>
                </div>
                <table class="table table-hover table-responsive">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>FromAccount</th>
                        <th>ToAccount</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${BPScenario.transactions.sort {it.id}}" var="transaction">
                        <tr>
                            <th scope="row">${transaction.name}</th>
                            <td>${transaction.date.toString('M/dd/yy')}</td>
                            <td>${'$ '+String.format('%.2f',transaction.amount/100)}</td>
                            <td>${transaction.fromAccount?.name}</td>
                            <td>${transaction.toAccount?.name}</td>
                            <td>
                                <g:form controller="BPBudgetItem" action="delete" id="${transaction.id}" method="post">
                                    <input type="hidden" name="_method" value="DELETE" id="_method" />
                                    <g:link controller="BPBudgetItem" action="edit" id="${transaction.id}">
                                        <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </g:link>
                                    <button type="submit" class="btn btn-default btn-sm" onclick="return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                                </g:form>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

            <div class="container-fluid">
                <h4>Repeating
                    <g:link controller="BPBudgetItemGenerator" action="create" params="['scenario.id': BPScenario.id, 'user.id': BPScenario.user.id]">
                        <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </g:link>
                </h4>
            </div>
                <table class="table table-hover table-responsive">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Every</th>
                        <th>Interval</th>
                        <th>Amount</th>
                        <th>FromAccount</th>
                        <th>ToAccount</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${BPScenario.generators.sort {it.id}}" var="generator">
                        <tr>
                            <th scope="row">${generator.name}</th>
                            <td>${generator.startDate.toString('M/dd/yy')}</td>
                            <td>${generator.endDate.toString('M/dd/yy')}</td>
                            <td>${generator.intervalValue}</td>
                            <td>${generator.intervalType}</td>
                            <td>${'$ '+String.format('%.2f',generator.amount/100)}</td>
                            <td>${generator.fromAccount?.name}</td>
                            <td>${generator.toAccount?.name}</td>
                            <td>
                                <g:form controller="BPBudgetItemGenerator" action="delete" id="${generator.id}" method="post">
                                    <input type="hidden" name="_method" value="DELETE" id="_method" />
                                    <g:link controller="BPBudgetItemGenerator" action="edit" id="${generator.id}">
                                        <button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </g:link>
                                    <button type="submit" class="btn btn-default btn-sm" onclick="return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                                </g:form>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
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
                                <th scope="row">${row.date.toString('M/dd/yy')}</th>
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
    </body>
</html>
