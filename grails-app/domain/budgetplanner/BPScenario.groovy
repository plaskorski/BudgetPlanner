package budgetplanner

class BPScenario {

    String name
    String description
    Date startDate
    Date endDate

    static belongsTo = [user:User]

    static hasOne = [ledger:BPTable]

    static hasMany = [accounts:BPAccount,
                      transactions:BPBudgetItem,
                      generators:BPBudgetItemGenerator]

    static constraints = {
        name nullable: false, empty: false
        description nullable: false, empty: false
        startDate nullable: false
        endDate nullable: false, validator: {val, obj ->
            if (obj.startDate) {
                val.after(obj.startDate)
            } else {false}
        }
        user nullable: false
        accounts nullable: true, empty: true
        transactions nullable: true, empty: true
        generators nullable: true, empty: true
        ledger nullable: true
    }

}
