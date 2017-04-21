package budgetplanner

class TransactionGenerator {

    String name
    Date startDate
    Date endDate
    Integer intervalValue
    budgetplanner.IntervalType intervalType
    Long amount

    static belongsTo = [scenario:Scenario]

    static hasOne = [fromAccount:Account,
                     toAccount:Account]

    static hasMany = [tags:String]

    static constraints = {
        scenario nullable: false
        name nullable: false, empty: false
        startDate nullable: false
        endDate nullable: false, validator: {val, obj -> val.compareTo(obj.startDate) >= 0}
        amount nullable: false, validator: {val, obj -> val >= 0}
        tags nullable: true, empty: true
        fromAccount validator: {val, obj ->
            (val==null && obj.toAccount!=null) |
                    (val!=null && obj.toAccount==null) |
                    (val!=null && obj.toAccount!=null && val.id != obj.toAccount.id)
        }
        intervalValue min: 1, max: 5
        intervalType nullable: false
    }
}
