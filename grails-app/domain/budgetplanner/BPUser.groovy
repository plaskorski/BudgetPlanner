package budgetplanner

class BPUser {

    String name
    Date birthDate

    static hasMany = [scenarios:BPScenario]

    static constraints = {
        name nullable: false, empty: false
        birthDate nullable: false
        scenarios nullable: true, empty: true
    }
}
