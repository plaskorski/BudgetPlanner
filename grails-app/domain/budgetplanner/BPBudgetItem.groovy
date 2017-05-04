package budgetplanner

import org.joda.time.LocalDate

class BPBudgetItem {

    String name
    LocalDate date
    Integer amount
    BPAccount fromAccount
    BPAccount toAccount

    static belongsTo = [scenario:BPScenario,user:User]

    static constraints = {
        name nullable: false, emtpy: false
        date nullable: false, validator: {val, obj ->
            if (obj.scenario) {((val <=> obj.scenario.startDate) >= 0) & ((val <=> obj.scenario.endDate) <= 0)}
            else {false}
        }
        amount nullable: false, min:0
        scenario nullable: false
        user nullable: false
        toAccount nullable: true
        fromAccount nullable: true, validator: {val, obj ->
                    (val==null && obj.toAccount!=null) |
                    (val!=null && obj.toAccount==null) |
                    (val!=null && obj.toAccount!=null && val.name != obj.toAccount.name)
        }
    }
}
