package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPTableRowBalance)
class BPTableRowBalanceSpec extends Specification {

    @Unroll
    void "test scenario parameters"() {
        when:
        BPTableRowBalance entry = new BPTableRowBalance(
                name: name,
                balance: balance,
                tableRow:tableRow
        )
        then:
        def output = entry.validate()
        output == result

        where:
        name|balance|tableRow|result
        "Checking"|0|new BPTableRow()|true
        ""|0|new BPTableRow()|false
        null|0|new BPTableRow()|false
        "Checking"|null|new BPTableRow()|false
    }
}
