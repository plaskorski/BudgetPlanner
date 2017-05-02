package budgetplanner

class BPBudgetItem {

    String name
    Date date
    Integer amount
    BPAccount fromAccount
    BPAccount toAccount

    static belongsTo = [scenario:BPScenario]

    static constraints = {
        name nullable: false, emtpy: false
        date nullable: false, validator: {val, obj ->
            if (obj.scenario) {
                val.compareTo(obj.scenario.startDate) >= 0 &
                        val.compareTo(obj.scenario.endDate) <= 0
            } else {false}
        }
        amount nullable: false, min:0
        scenario nullable: false
        toAccount nullable: true
        fromAccount nullable: true, validator: {val, obj ->
                    (val==null && obj.toAccount!=null) |
                    (val!=null && obj.toAccount==null) |
                    (val!=null && obj.toAccount!=null && val.name != obj.toAccount.name)
        }
    }
}
