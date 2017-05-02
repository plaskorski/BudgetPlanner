package budgetplanner

class BPAccount {

    String name
    budgetplanner.AccountType type
    Integer balance

    static belongsTo = [scenario:BPScenario,user:User]

    static constraints = {
        name nullable: false, empty: false
        type nullable: false
        balance nullable: false
        scenario nullable: false
        user nullable: false
    }
}
