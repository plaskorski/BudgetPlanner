package budgetplanner

class BPLedgerRowEntry {

    String name
    Integer balance

    static belongsTo = [ledgerRow:BPLedgerRow]

    static constraints = {
        name nullable: false, empty:false
        balance nullable: false
    }
}
