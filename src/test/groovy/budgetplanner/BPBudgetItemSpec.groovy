package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

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
                tags: tags
        )
        then:
        def output = transaction.validate()
        output == result

        where:
        name|date|amount|scenario|fromAccount|toAccount|tags|result
        "rent"|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |true
        ""|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        null|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|null|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|new Date(year:2018,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|null|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|-1|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|80000| null|new BPAccount(name:"1") |new BPAccount(name:"2") |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |new BPAccount(name:"1") |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|null |null |[] |false
        "rent"|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|new BPAccount(name:"1") |null |[] |true
        "rent"|new Date(year:2017,month:6,date:1)|80000|new BPScenario(startDate: new Date(year:2017,month:1,date:1), endDate: new Date(year:2017,month: 12,date: 31))|null |new BPAccount(name:"2") |[] |true
    }

}
