package budgetplanner

class BPBudgetItemGenerator {

    String name
    Date startDate
    Date endDate
    Integer intervalValue
    budgetplanner.IntervalType intervalType
    Integer amount

    static belongsTo = [scenario:BPScenario]

    static hasOne = [fromAccount:BPAccount,
                     toAccount:BPAccount]

    static hasMany = [tags:String]

    static constraints = {
        scenario nullable: false
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
        tags nullable: true, empty: true
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
