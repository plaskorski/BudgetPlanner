package budgetplanner

class BPBudgetItemGenerator {

    enum IntervalType {
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    String name
    Date startDate
    Date endDate
    Integer intervalValue
    IntervalType intervalType
    Integer amount
    BPAccount fromAccount
    BPAccount toAccount

    static belongsTo = [scenario:BPScenario, user:User]

    def budgetItems() {

        def items = []

        BPBudgetItem item
        Date date = startDate

        while (true) {

            if (date.after(endDate)) {break}

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

            Calendar cal = Calendar.getInstance()
            cal.setTime(date)
            def intType = Calendar.DATE
            def intValue = intervalValue
            switch (intervalType) {
                case IntervalType.DAY:
                    break
                case IntervalType.WEEK:
                    intValue *= 7
                    break
                case IntervalType.MONTH:
                    intType = Calendar.MONTH
                    break
                case IntervalType.YEAR:
                    intType = Calendar.YEAR
                    break
            }
            cal.add(intType, intValue)
            date = cal.getTime()
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
                        val.compareTo(obj.scenario.startDate) >= 0 & val.compareTo(obj.scenario.endDate) < 0 & val.compareTo(obj.endDate) <= 0
                    } else {
                        val.compareTo(obj.scenario.startDate) >= 0 & val.compareTo(obj.scenario.endDate) < 0
                    }
                } else {false}
            } else {false}
        }
        endDate nullable: false, validator: {val, obj ->
            if (val) {
                if (obj.scenario) {
                    if (obj.startDate) {
                        val.compareTo(obj.scenario.startDate) > 0 & val.compareTo(obj.scenario.endDate) <= 0 & val.compareTo(obj.startDate) > 0
                    } else {
                        val.compareTo(obj.scenario.startDate) > 0 & val.compareTo(obj.scenario.endDate) <= 0
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
