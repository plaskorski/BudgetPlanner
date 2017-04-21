package budgetplanner

class Scenario {

    String name
    String description
    Date startDate
    Date endDate

    static belongsTo = [user:User]

    static hasMany = [accounts:Account,
                      transactions:Transaction,
                      generators:TransactionGenerator,
                      ledgers:Ledger]

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
