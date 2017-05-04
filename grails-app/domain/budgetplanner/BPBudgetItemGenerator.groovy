package budgetplanner

import org.joda.time.LocalDate

class BPBudgetItemGenerator {

    enum IntervalType {
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    String name
    LocalDate startDate
    LocalDate endDate
    Integer intervalValue
    IntervalType intervalType
    Integer amount
    BPAccount fromAccount
    BPAccount toAccount

    static belongsTo = [scenario:BPScenario, user:User]

    def budgetItems() {

        def items = []

        BPBudgetItem item
        LocalDate date = startDate

        while (true) {

            if (date.isAfter(endDate)) {break}

            item = new BPBudgetItem(
                    name: name,
                    date: date,
                    amount: amount,
                    scenario: scenario,
                    fromAccount: fromAccount,
                    toAccount: toAccount,
                    user: user
            )
            items << item

            switch (intervalType) {
                case IntervalType.DAY:
                    date = date.plusDays(intervalValue)
                    break
                case IntervalType.WEEK:
                    date = date.plusWeeks(intervalValue)
                    break
                case IntervalType.MONTH:
                    date = date.plusMonths(intervalValue)
                    break
                case IntervalType.YEAR:
                    date = date.plusYears(intervalValue)
                    break
            }
        }
        items
    }

    static constraints = {
        scenario nullable: false
        user nullable: false
        name nullable: false, empty: false
        startDate nullable: false, validator: {val, obj ->
            if (val) {
                if (obj.scenario) {
                    if (obj.endDate) {
                        ((val <=> obj.scenario.startDate) >= 0) & ((val <=> obj.scenario.endDate) < 0) & ((val <=> obj.endDate) <= 0)
                    } else {
                        ((val <=> obj.scenario.startDate) >= 0) & ((val <=> obj.scenario.endDate) < 0)
                    }
                } else {false}
            } else {false}
        }
        endDate nullable: false, validator: {val, obj ->
            if (val) {
                if (obj.scenario) {
                    if (obj.startDate) {
                        ((val <=> obj.scenario.startDate) > 0) & ((val <=> obj.scenario.endDate) <= 0) & ((val <=> obj.startDate) > 0)
                    } else {
                        ((val <=> obj.scenario.startDate) > 0) & ((val <=> obj.scenario.endDate) <= 0)
                    }
                } else {false}
            } else {false}
        }
        amount nullable: false, validator: {val, obj -> val >= 0}
        fromAccount nullable: true, validator: {val, obj ->
            (val==null && obj.toAccount!=null) |
                    (val!=null && obj.toAccount==null) |
                    (val!=null && obj.toAccount!=null && val.name != obj.toAccount.name)
        }
        toAccount nullable: true
        intervalValue nullable: false, min: 1, max: 5
        intervalType nullable: false
    }
}
