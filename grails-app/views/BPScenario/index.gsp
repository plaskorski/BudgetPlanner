<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Scenarios</title>
    </head>
    <body>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h2>Scenarios
                        <g:link controller="BPScenario" action="create" params="['user.id': userId]">
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                        </g:link>
                    </h2>
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
                                <th></th>
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
                                <td>
                                    <g:form controller="BPScenario" action="delete" id="${scenario.id}" method="post">
                                        <input type="hidden" name="_method" value="DELETE" id="_method" />
                                        <button type="submit" class="btn btn-default btn-sm" onclick="return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                                    </g:form>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
            </div>
        </div>
    </body>
</html>