package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPUser)
class BPUserSpec extends Specification {

    BPScenario s
    BPUser u
    Date d = new Date(year:1986,month:2,date:13)
    HashSet<BPScenario> scenSet

    def setup() {
        s = new BPScenario()
        scenSet = new HashSet<BPScenario>()
        scenSet.add(s)
    }

    def cleanup() {
    }

    void "all parameters validates"() {
        u = new BPUser(name: "John",birthDate: d, scenarios: scenSet)
        expect:
            u.validate()
    }

    void "no name does not validate"() {
        u = new BPUser(birthDate: d, scenarios: scenSet)
        expect:
        !u.validate()
    }

    void "empty name does not validate"() {
        u = new BPUser(name: "",birthDate: d, scenarios: scenSet)
        expect:
        !u.validate()
    }

    void "no birthdate does not validate"() {
        u = new BPUser(name:"John",scenarios: scenSet)
        expect:
        !u.validate()
    }

    void "empty scenarios validates"() {
        u = new BPUser(name: "John",birthDate: d, scenarios: new HashSet<BPScenario>())
        expect:
        u.validate()
    }
}
