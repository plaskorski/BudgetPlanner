package budgetplanner

class BPTransaction {

    String name
    Date date
    Long amount

    static belongsTo = [scenario:BPScenario]

    static hasOne = [fromAccount:BPAccount,
                     toAccount:BPAccount]

    static hasMany = [tags:String]

    static constraints = {
        name nullable: false, emtpy: false
        date nullable: false, validator: {val, obj ->
            val.compareTo(obj.scenario.startDate) >= 0 &
                    val.compareTo(obj.scenario.endDate) <= 0
        }
        amount nullable: false, min:0
        scenario nullable: false
        tags nullable: true, empty: true
        fromAccount validator: {val, obj ->
                    (val==null && obj.toAccount!=null) |
                    (val!=null && obj.toAccount==null) |
                    (val!=null && obj.toAccount!=null && val.id != obj.toAccount.id)
        }
    }
}
