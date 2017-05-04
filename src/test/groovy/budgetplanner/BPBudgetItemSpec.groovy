package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import org.joda.time.LocalDate

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPBudgetItem)
class BPBudgetItemSpec extends Specification {

    @Unroll
    void "test scenario parameters"() {
        when:
        BPBudgetItem transaction = new BPBudgetItem(
                name: name,
                date: date,
                amount: amount,
                scenario: scenario,
                fromAccount: fromAccount,
                toAccount: toAccount,
                user:user
        )
        then:
        def output = transaction.validate()
        output == result

        where:
        user|name|date                                      |amount |scenario                                                                                                         |fromAccount             |toAccount               |result
        new User()|"rent"|new LocalDate(2017, 6, 1) |80000  |new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |true
        new User()|""|new LocalDate(2017, 6, 1)     |80000  |new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|null|new LocalDate(2017, 6, 1)   |80000  |new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|null|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))                               |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2018, 6, 1)|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|null|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))  |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|-1|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|80000| null|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"1") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|null |null |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |null |true
        new User()|"rent"|new LocalDate(2017, 6, 1)|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|null |new BPAccount(name:"2") |true
    }

}
