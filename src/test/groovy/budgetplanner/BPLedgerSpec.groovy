package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPLedger)
class BPLedgerSpec extends Specification {

    BPScenario s
    BPLedger l

    def setup() {
        s = new BPScenario()
    }

    def cleanup() {
    }

    void "valid parameters validate"() {
        l = new BPLedger(scenario: s)
        expect:
        l.validate()
    }

    void "no scenario does not validate"() {
        l = new BPLedger()
        expect:
            !l.validate()
    }
}
