package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPTable)
class BPTableSpec extends Specification {

    @Unroll
    void "test scenario parameters"() {
        when:
        BPTable table = new BPTable(
                scenario: scenario,
                rows: rows,
                user:user
        )
        then:
        def output = table.validate()
        output == result

        where:
        user|scenario|rows|result
        new User()|new BPScenario()|[]|true
        new User()|new BPScenario()|null|true
        new User()|null|null|false
        new User()|null|[]|false
        new User()|new BPScenario()|new HashSet<BPTableRow>().add(new BPTableRow())|true
    }

    void "test header values"() {
        HashSet<BPAccount> accounts = new HashSet<>()
        accounts.add(new BPAccount(name:"Checking"))
        BPScenario scenario = new BPScenario(
                name: "test",
                description: "test",
                accounts: accounts
        )
        BPTable table = new BPTable()
        table.scenario=scenario
        expect:
        table.header == ["Date","Name","Amount","Checking"]
    }

    void "test 2 header values"() {
        HashSet<BPAccount> accounts = new HashSet<>()
        accounts.add(new BPAccount(name:"Checking"))
        accounts.add(new BPAccount(name:"Savings"))
        BPScenario scenario = new BPScenario(
                name: "test",
                description: "test",
                accounts: accounts
        )
        BPTable table = new BPTable()
        table.scenario=scenario
        expect:
        table.header == ["Date","Name","Amount","Checking","Savings"]
    }
    void "test 2 header values out of order"() {
        HashSet<BPAccount> accounts = new HashSet<>()
        accounts.add(new BPAccount(name:"Savings"))
        accounts.add(new BPAccount(name:"Checking"))
        BPScenario scenario = new BPScenario(
                name: "test",
                description: "test",
                accounts: accounts
        )
        BPTable table = new BPTable()
        table.scenario=scenario
        expect:
        table.header == ["Date","Name","Amount","Checking","Savings"]
    }
}
