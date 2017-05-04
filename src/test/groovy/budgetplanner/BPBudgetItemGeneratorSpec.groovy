package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import org.joda.time.LocalDate

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPBudgetItemGenerator)
class BPBudgetItemGeneratorSpec extends Specification {

    @Unroll
    void "test scenario parameters"() {
        when:
        BPBudgetItemGenerator generator = new BPBudgetItemGenerator(
                name: name,
                startDate: startDate,
                endDate: endDate,
                intervalValue: intervalValue,
                intervalType: intervalType,
                amount: amount,
                scenario: scenario,
                fromAccount: fromAccount,
                toAccount: toAccount,
                user: user
        )
        then:
        def output = generator.validate()
        output == result

        where:
        user       |name   |startDate                          |endDate                             |intervalValue |intervalType                            |amount |scenario                                                                                                       |fromAccount             |toAccount               |result
        new User() |"rent" |new LocalDate(2017, 6, 1)  |new LocalDate(2017, 12, 1) |2             |BPBudgetItemGenerator.IntervalType.WEEK |80000  |new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |true
        new User() |""     |new LocalDate(2017, 6, 1) |new LocalDate(2017, 12, 1) |2             |BPBudgetItemGenerator.IntervalType.WEEK |80000  |new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31)) |new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|null|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|null|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|null|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|null|null|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 4, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|null|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|null|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|6|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|0|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|null|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|null|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|null|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |null |true
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|null |new BPAccount(name:"2") |true
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|new BPAccount(name:"1") |new BPAccount(name:"1") |false
        new User()|"rent"|new LocalDate(2017, 6, 1)|new LocalDate(2017, 12, 1)|2|BPBudgetItemGenerator.IntervalType.WEEK|80000|new BPScenario(startDate: new LocalDate(2017, 1, 1), endDate: new LocalDate(2017, 12, 31))|null |null |false
    }

}
