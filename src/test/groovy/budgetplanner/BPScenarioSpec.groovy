package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPScenario)
class BPScenarioSpec extends Specification {

    @Unroll
    void "test scenario parameters"() {
        when:
        BPScenario scenario = new BPScenario(
                name: name,
                description: description,
                startDate: startDate,
                endDate: endDate,
                user: user,
                ledger: ledger,
                accounts: accounts,
                transactions: transactions,
                generators:generators
        )
        then:
            def output = scenario.validate()
            output == result

        where:
            name|description|startDate|endDate|user                    |ledger         |accounts                                        |transactions                                          |generators                                                              |result
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |new BPTable() |[]                                              |[]                                                    |[]                                                                      |true
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |new BPTable()     |(new HashSet<BPAccount>().add(new BPAccount())) |(new HashSet<BPBudgetItem>().add(new BPBudgetItem())) |(new HashSet<BPBudgetItemGenerator>().add(new BPBudgetItemGenerator())) |true
            ""|"some scenario"|new Date()|new Date()+100|new User() |new BPTable()     |[]                                              |[]                                                    |[]                                                                      |false
            null|"some scenario"|new Date()|new Date()+100|new User() |new BPTable()   |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|""|new Date()|new Date()+100|new User() |new BPTable()              |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|null|new Date()|new Date()+100|new User() |new BPTable()            |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|"some scenario"|new Date()|new Date()-100|new User() |new BPTable() |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|"some scenario"|null|new Date()+100|new User() |new BPTable()       |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|"some scenario"|new Date()|null|new User() |new BPTable()           |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|"some scenario"|new Date()|new Date()+100|null |new BPTable()       |[]                                              |[]                                                    |[]                                                                      |false
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |null          |[]                                              |[]                                                    |[]                                                                      |true
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |new BPTable() |null                                            |[]                                                    |[]                                                                      |true
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |new BPTable() |[]                                              |null                                                  |[]                                                                      |true
            "paul"|"some scenario"|new Date()|new Date()+100|new User() |new BPTable() |[]                                              |[]                                                    |null                                                                    |true

    }

    void "test one transaction"() {
        BPScenario scenario = new BPScenario(
                startDate: new Date(year:2017,month: 1,date: 1),
                endDate: new Date(year: 2017,month: 12,date: 31))
        BPAccount account = new BPAccount(name:"Checking",balance: 50000)
        scenario.accounts = []
        scenario.accounts << account
        account.scenario = scenario
        scenario.transactions = []
        BPBudgetItem item = new BPBudgetItem(
                name: "Cell Phone",
                date: new Date(year: 2017,month: 6,date: 1),
                amount: 5000,
                fromAccount:account)
        scenario.transactions << item
        scenario.populateTable()
        scenario.generators = []
        expect:
        scenario.table.rows.size() == 2
        scenario.table.rows[0].entries[0].balance == 50000
        scenario.table.rows[1].entries[0].balance == 45000

    }

    void "test transaction generator"() {
        BPScenario scenario = new BPScenario(
                startDate: new Date(year:2017,month: 1,date: 1),
                endDate: new Date(year: 2017,month: 12,date: 31))
        BPAccount account = new BPAccount(name:"Checking",balance: 50000)
        scenario.accounts = []
        scenario.accounts << account
        account.scenario = scenario
        scenario.generators = []
        BPBudgetItemGenerator generator = new BPBudgetItemGenerator(
                name: "Cell Phone",
                startDate: new Date(year: 2017,month: 6,date: 1),
                endDate: new Date(year: 2017,month: 12,date: 31),
                intervalValue: 1,
                intervalType: budgetplanner.IntervalType.MONTH,
                amount: 5000,
                fromAccount:account)
        scenario.generators << generator
        scenario.populateTable()
        scenario.transactions = []
        expect:
        scenario.table.rows.size() == 8
        scenario.table.rows[0].entries[0].balance == 50000
        scenario.table.rows[1].entries[0].balance == 45000
        scenario.table.rows[2].entries[0].balance == 40000
        scenario.table.rows[3].entries[0].balance == 35000
        scenario.table.rows[4].entries[0].balance == 30000
        scenario.table.rows[5].entries[0].balance == 25000
        scenario.table.rows[6].entries[0].balance == 20000
        scenario.table.rows[7].entries[0].balance == 15000
    }

    void "test two transactions, two accounts"() {
        BPScenario scenario = new BPScenario(
                startDate: new Date(year:2017,month: 1,date: 1),
                endDate: new Date(year: 2017,month: 12,date: 31))
        BPAccount account1 = new BPAccount(name:"Checking",balance: 50000)
        BPAccount account2 = new BPAccount(name:"Savings",balance: 100000)
        scenario.accounts = []
        scenario.accounts << account1 << account2
        account1.scenario = scenario
        account2.scenario = scenario
        scenario.transactions = []
        BPBudgetItem item1 = new BPBudgetItem(
                name: "Cell Phone",
                date: new Date(year: 2017,month: 6,date: 1),
                amount: 5000,
                fromAccount:account1)
        BPBudgetItem item2 = new BPBudgetItem(
                name: "Monthly Savings",
                date: new Date(year: 2017,month: 6,date: 15),
                amount: 10000,
                fromAccount:account1,
                toAccount:account2)
        scenario.transactions << item1 << item2
        scenario.populateTable()
        scenario.generators = []
        expect:
        scenario.table.rows.size() == 3
        scenario.table.rows[0].entries.find {it.name=="Checking"}.balance == 50000
        scenario.table.rows[0].entries.find {it.name=="Savings"}.balance == 100000
        scenario.table.rows[1].entries.find {it.name=="Checking"}.balance == 45000
        scenario.table.rows[1].entries.find {it.name=="Savings"}.balance == 100000
        scenario.table.rows[2].entries.find {it.name=="Checking"}.balance == 35000
        scenario.table.rows[2].entries.find {it.name=="Savings"}.balance == 110000

    }
}
