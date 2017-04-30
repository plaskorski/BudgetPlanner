package budgetplanner

class BPLedgerRow {

    Date date
    String name
    String getTags() { tags.sort {a,b -> a<=>b}.join(",") }
    Integer amount
    Integer[] getValues() { entries.sort {a,b->a.name<=>b.name}.collect {val -> val.balance} }

    static transients = ['values']

    static hasMany = [tags:String,
                      entries:BPLedgerRowEntry]

    static belongsTo = [ledger:BPLedger]

    static constraints = {
        amount nullable: false
    }
}
