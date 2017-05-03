<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Scenarios</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h2>Scenarios</h2>
                </div>
                <div class="panel-body">
                    <div class="btn-group" role="group" aria-label="buttons">
                        <button type="button" class="btn btn-primary">Create</button>
                        <button type="button" class="btn btn-default" disabled="disabled">Delete</button>
                        <button type="button" class="btn btn-default" disabled="disabled">Compare</button>
                    </div>
                    <table class="table table-hover table-responsive">
                        <theader>
                            <tr>
                                <sec:ifAnyGranted roles="ROLE_ADMIN"><th>User</th></sec:ifAnyGranted>
                                <sec:ifNotGranted roles="ROLE_ADMIN"><th>Name</th></sec:ifNotGranted>
                                <sec:ifAnyGranted roles="ROLE_ADMIN"><th>Scenario</th></sec:ifAnyGranted>
                                <th>Description</th>
                                <th>StartDate</th>
                                <th>EndDate</th>
                                <th class="hidden-xs">Accounts</th>
                                <th class="hidden-xs">Single Transactions</th>
                                <th class="hidden-xs">Repeat Transactions</th>
                            </tr>
                        </theader>
                        <tbody>
                        <g:each in="${BPScenarioList}" var="scenario">
                            <tr>
                                <sec:ifAnyGranted roles="ROLE_ADMIN">
                                    <th scope="row">
                                        <g:link controller="user" action="show" id="${scenario.userId}">${scenario.user.name}</g:link>
                                    </th>
                                    <th>
                                        <g:link controller="BPScenario" action="show" id="${scenario.id}">${scenario.name}</g:link>
                                    </th>
                                </sec:ifAnyGranted>
                                <sec:ifNotGranted roles="ROLE_ADMIN">
                                    <th scope="row"><g:link action="show" id="${scenario.id}">${scenario.name}</g:link></th>
                                </sec:ifNotGranted>
                                <td>${scenario.description}</td>
                                <td>${scenario.startDate.format("M/dd/yy")}</td>
                                <td>${scenario.endDate.format("M/dd/yy")}</td>
                                <td class="hidden-xs">${scenario.accounts.size()}</td>
                                <td class="hidden-xs">${scenario.transactions.size()}</td>
                                <td class="hidden-xs">${scenario.generators.size()}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>