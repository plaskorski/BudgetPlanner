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
            "paul"|"some scenario"|new Date()|new Date()|new User() |new BPTable()     |(new HashSet<BPAccount>().add(new BPAccount())) |(new HashSet<BPBudgetItem>().add(new BPBudgetItem())) |(new HashSet<BPBudgetItemGenerator>().add(new BPBudgetItemGenerator())) |true
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
}
