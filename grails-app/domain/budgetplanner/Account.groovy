package budgetplanner

class Account {

    String name
    budgetplanner.AccountType type
    Integer balance

    static belongsTo = [scenario:Scenario]

    static constraints = {
        name nullable: false, empty: false
        balance nullable: false
    }
}
