package budgetplanner

class BPAccount {

    String name
    budgetplanner.AccountType type
    Integer balance

    static belongsTo = [scenario:BPScenario]

    static constraints = {
        name nullable: false, empty: false
        balance nullable: false
    }
}
