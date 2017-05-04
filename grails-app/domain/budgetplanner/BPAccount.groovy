package budgetplanner

class BPAccount {

    String name
    AccountType type
    Integer balance

    enum AccountType {
        SAVINGS,
        CHECKING,
        LOAN,
        CREDIT,
        ASSET
    }

    static belongsTo = [scenario:BPScenario,user:User]

    static constraints = {
        name nullable: false, empty: false
        type nullable: false
        balance nullable: false
        scenario nullable: false
        user nullable: false
    }
}
