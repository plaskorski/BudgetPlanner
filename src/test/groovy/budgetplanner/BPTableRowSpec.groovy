package budgetplanner

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import org.joda.time.LocalDate

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BPTableRow)
class BPTableRowSpec extends Specification {

    @Unroll
    void "test row parameters"() {
        when:
        BPTableRow row = new BPTableRow(
                date:date,
                name:name,
                amount:amount,
                entries:entries,
                table:table
        )
        then:
        def output = row.validate()
        output == result

        where:
        date                                 |name         |amount |entries |table         |result
        new LocalDate(2017, 1, 1) |"Cell Phone" |4000   |[]      |new BPTable() |true
        null                                 |"Cell Phone" |4000   |[]      |new BPTable() |false
        new LocalDate(2017, 1, 1) |null         |4000   |[]      |new BPTable() |false
        new LocalDate(2017, 1, 1) |""           |4000   |[]      |new BPTable() |false
        new LocalDate(2017, 1, 1) |"Cell Phone" |null   |[]      |new BPTable() |false
        new LocalDate(2017, 1, 1) |"Cell Phone" |4000   |[]      |null          |false
    }

}
