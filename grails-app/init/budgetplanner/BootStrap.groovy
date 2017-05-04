package budgetplanner

import org.joda.time.LocalDate

class BootStrap {

    def init = { servletContext ->
        // ADMIN USER
        LocalDate currDate = new LocalDate(1986,6,13)
        User user = new User(
                name: "admin",
                username: "admin",
                birthDate: currDate,
                password: "secret",
                scenarios: [],
                accounts: [],
                budgetItems: [],
                budgetItemGenerators: []
        )
        user.save(flush:true)
        Role role = new Role(authority: Role.ROLE_ADMIN)
        role.save(flush:true)
        UserRole.create(user,role)

        // EXAMPLE USER
        User user2 = new User(
                name: "user",
                username: "user",
                birthDate: currDate,
                password: "secret",
                scenarios: [],
                accounts: [],
                budgetItems: [],
                budgetItemGenerators: []
        )
        user2.save(flush:true)
        LocalDate startDate = new LocalDate(2017,1,1)
        LocalDate endDate = new LocalDate(2017,12,31)
        BPScenario scenario2 = new BPScenario(
                name: "Main",
                description: "Current Budget",
                startDate: startDate,
                endDate: endDate,
                user: user2,
                accounts: [],
                transactions: [],
                generators: []
        )
        scenario2.save flush: true

        BPAccount account1 = new BPAccount(
                name: "Checking",
                type: BPAccount.AccountType.CHECKING,
                balance: 300000,
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
                type: BPAccount.AccountType.SAVINGS,
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
                name: "Taxes",
                date: new LocalDate(2017,3,1),
                amount: 30000,
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
                name: "Side Job",
                date: new LocalDate(2017,5,1),
                amount: 50000,
                toAccount: account1
        )
        item2.save flush: true

        scenario2.transactions << item2
        scenario2.save flush: true
        user2.budgetItems << item2
        user2.save flush: true

        BPBudgetItemGenerator generator = new BPBudgetItemGenerator(
                name: "Transfer",
                startDate: new LocalDate(2017,1,14),
                endDate: new LocalDate(2017,12,14),
                intervalValue: 2,
                intervalType: BPBudgetItemGenerator.IntervalType.WEEK,
                amount: 10000,
                scenario: scenario2,
                fromAccount: account1,
                toAccount: account2,
                user: user2
        )
        generator.save flush:true
        scenario2.generators << generator
        scenario2.save flush:true
        user2.budgetItemGenerators << generator


        BPBudgetItemGenerator generator2 = new BPBudgetItemGenerator(
                name: "Income",
                startDate: new LocalDate(2017,1,14),
                endDate: new LocalDate(2017,12,14),
                intervalValue: 2,
                intervalType: BPBudgetItemGenerator.IntervalType.WEEK,
                amount: 200000,
                scenario: scenario2,
                toAccount: account1,
                user: user2
        )
        generator2.save flush:true
        scenario2.generators << generator2
        scenario2.save flush:true
        user2.budgetItemGenerators << generator2

        BPBudgetItemGenerator generator3 = new BPBudgetItemGenerator(
                name: "Rent",
                startDate: new LocalDate(2017,1,1),
                endDate: new LocalDate(2017,12,1),
                intervalValue: 1,
                intervalType: BPBudgetItemGenerator.IntervalType.MONTH,
                amount: 100000,
                scenario: scenario2,
                fromAccount: account1,
                user: user2
        )
        generator3.save flush:true
        scenario2.generators << generator3
        scenario2.save flush:true
        user2.budgetItemGenerators << generator3

        user2.scenarios << scenario2
        user2.save flush: true

        role = new Role(authority: Role.ROLE_USER)
        role.save(flush:true)
        UserRole.create(user2,role)
    }
    def destroy = {
    }
}
