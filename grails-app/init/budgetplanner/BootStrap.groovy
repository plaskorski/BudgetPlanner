package budgetplanner

class BootStrap {

    def init = { servletContext ->
        User user = new User(
                name: "admin",
                username: "admin",
                birthDate: new Date(),
                password: "secret",
                scenarios: [],
                accounts: []
        )
        BPScenario scenario = new BPScenario(
                name: "test",
                description: "blah",
                startDate: new Date(),
                endDate: new Date()+100000,
                user:user,
                accounts:[],
                transactions: [],
                generators: []
        )
        BPAccount account = new BPAccount(
                name: "checking",
                type: budgetplanner.AccountType.CHECKING,
                balance: 100,
                user: user,
                scenario: scenario
        )

        scenario.accounts << account
        user.accounts << account
        user.scenarios << scenario
        user.save(flush:true)
        Role role = new Role(authority: Role.ROLE_ADMIN)
        role.save(flush:true)
        UserRole.create(user,role)

        user = new User(
                name: "user",
                username: "user",
                birthDate: new Date(),
                password: "secret",
                scenarios: [],
                accounts: []
        )
        user.save(flush:true)
        role = new Role(authority: Role.ROLE_USER)
        role.save(flush:true)
        UserRole.create(user,role)
    }
    def destroy = {
    }
}
