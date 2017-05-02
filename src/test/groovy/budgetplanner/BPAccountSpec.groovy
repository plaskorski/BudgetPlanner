package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPAccount)
class BPAccountSpec extends Specification {

    BPScenario s
    BPAccount a

    void setup() {
        s = new BPScenario()
    }

    void "valid parameters validates"() {
        a = new BPAccount(name: "Paul's Checking", user: new User(), type: budgetplanner.AccountType.CHECKING, balance: 20000, scenario: s)
        expect:
            a.validate()
    }

    void "no scenario does not validate"() {
        a = new BPAccount(name: "Paul's Checking", user: new User(), type: budgetplanner.AccountType.CHECKING, balance: 20000)
        expect:
           ! a.validate()
    }

    void "no name does not validate"() {
        a = new BPAccount(type: budgetplanner.AccountType.CHECKING, user: new User(), balance: 20000, scenario: s)
        expect:
            ! a.validate()
    }

    void "no balance does not validate"() {
        a = new BPAccount(name: "Paul's Checking", user: new User(), type: budgetplanner.AccountType.CHECKING, scenario: s)
        expect:
        ! a.validate()
    }

    void "no type does not validate"() {
        a = new BPAccount(name: "Paul's Checking", user: new User(), balance: 20000, scenario: s)
        expect:
            ! a.validate()
    }

    void "empty name does not validate"() {
        a = new BPAccount(name: "", user: new User(), type: budgetplanner.AccountType.CHECKING, balance: 20000, scenario: s)
        expect:
        ! a.validate()
    }

}
