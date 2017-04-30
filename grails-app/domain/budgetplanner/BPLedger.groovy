package budgetplanner

class BPLedger {

    String[] header

    static transients = ['header']

    static belongsTo = [scenario:BPScenario]

    static hasMany = [rows:BPLedgerRow]

    static constraints = {
        rows nullable: true, empty: true
        scenario nullable: false
        header nullable: false, empty: false
    }
}
