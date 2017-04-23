package budgetplanner

class BPScenario {

    String name
    String description
    Date startDate
    Date endDate

    static belongsTo = [user:BPUser]

    static hasMany = [accounts:BPAccount,
                      transactions:BPTransaction,
                      generators:BPTransactionGenerator,
                      ledgers:BPLedger]

    static constraints = {
        name nullable: false, empty: false
        description nullable: false, empty: false
        startDate nullable: false
        endDate nullable: false, validator: {val, obj -> val.after(obj.startDate)}
        user nullable: false
        accounts nullable: true, empty: true
        transactions nullable: true, empty: true
        generators nullable: true, empty: true
        ledgers nullable: true, empty: true
    }

}
