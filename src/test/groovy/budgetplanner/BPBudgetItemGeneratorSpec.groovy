package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

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
                toAccount: toAccount)
        then:
        def output = generator.validate()
        output == result

        where:
        name|startDate|endDate|intervalValue|intervalType|amount|scenario|fromAccount|toAccount|result
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |true
        ""|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        null|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|null|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|null|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|null|null|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:4,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|null|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|null|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|6|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|0|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|null|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|null|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|null|new BPAccount(name:"1") |new BPAccount(name:"2") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |null |true
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|null |new BPAccount(name:"2") |true
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"1") |false
        "rent"|new Date(year:2017,month:6,date:1)|new Date(year:2017,month:12,date:1)|2|budgetplanner.IntervalType.WEEK|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|null |null |false
    }

}
