package budgetplanner

class User {

    String name
    Date birthDate

    static hasMany = [scenarios:Scenario]

    static constraints = {
        name nullable: false, empty: false
        birthDate nullable: false
        scenarios nullable: true, empty: true
    }
}
