package budgetplanner

class BootStrap {

    def init = { servletContext ->
        User user = new User(
                name: "admin",
                username: "admin",
                birthDate: new Date(),
                password: "secret",
                scenarios: [],
                accounts: [],
                budgetItems: [],
                budgetItemGenerators: []
        )
        BPScenario scenario = new BPScenario(
                name: "test",
                description: "blah",
                startDate: new Date(),
                endDate: new Date()+100,
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

        User user2 = new User(
                name: "user",
                username: "user",
                birthDate: new Date(),
                password: "secret",
                scenarios: [],
                accounts: [],
                budgetItems: [],
                budgetItemGenerators: []
        )
        user2.save(flush:true)
        BPScenario scenario2 = new BPScenario(
                name: "test2",
                description: "blah2",
                startDate: new Date(),
                endDate: new Date()+100,
                user: user2,
                accounts: [],
                transactions: [],
                generators: []
        )
        scenario2.save flush: true

        BPAccount account1 = new BPAccount(
                name: "Checking",
                type: budgetplanner.AccountType.CHECKING,
                balance: 100000,
                user: user2,
                scenario: scenario2
        )
        account1.save flush: true
        scenario2.accounts << account1
        scenario2.save flush: true
        user2.accounts << account1
        user2.save flush: true

        BPAccount account2 = new BPAccount(
                name: "Savings",
                type: budgetplanner.AccountType.SAVINGS,
                balance: 500000,
                user: user2,
                scenario: scenario2
        )
        account2.save flush:true
        scenario2.accounts << account2
        scenario2.save flush: true
        user2.accounts << account2
        user2.save flush:true

        BPBudgetItem item1 = new BPBudgetItem(
                user: user2,
                scenario: scenario2,
                name: "Cell Phone",
                date: new Date()+10,
                amount: 3000,
                fromAccount: account1
        )
        item1.save flush: true

        scenario2.transactions << item1
        scenario2.save flush:true
        user2.budgetItems << item1
        user2.save flush: true

        BPBudgetItem item2 = new BPBudgetItem(
                user: user2,
                scenario: scenario2,
                name: "Savings Transfer",
                date: new Date()+14,
                amount: 5000,
                fromAccount: account1,
                toAccount: account2
        )
        item2.save flush: true

        scenario2.transactions << item2
        scenario2.save flush: true
        user2.budgetItems << item2
        user2.save flush: true

        scenario2.save flush: true
        user2.save(flush:true)

        BPBudgetItemGenerator item3 = new BPBudgetItemGenerator(
                user: user2,
                scenario: scenario2,
                name: "Savings Transfer2",
                startDate: new Date()+14,
                endDate: new Date()+100,
                intervalValue: 1,
                intervalType: BPBudgetItemGenerator.IntervalType.MONTH,
                amount: 5000,
                fromAccount: account1,
                toAccount: account2
        )
        item3.save flush: true

        scenario2.generators << item3
        scenario2.save flush:true
        user2.budgetItemGenerators << item3
        user2.save flush: true

        user2.scenarios << scenario2
        user2.save flush: true

        role = new Role(authority: Role.ROLE_USER)
        role.save(flush:true)
        UserRole.create(user2,role)
    }
    def destroy = {
    }
}
