package budgetplanner

class BPTableRow {

    Date date
    String name
    Integer amount
    Integer[] getValues() { entries.sort {a,b->a.name<=>b.name}.collect {val -> val.balance} }

    BPTableRow getNextRow(BPBudgetItem transaction) {
        BPTableRow newRow = new BPTableRow(
                date:transaction.date,
                name:transaction.name,
                amount:transaction.amount,
                table:table,
                entries:[]
        )
        entries.each {
            newRow.entries << new BPTableRowBalance(name:it.name,balance:it.balance,tableRow: newRow)
        }
        if (transaction.fromAccount) {
            newRow.entries.find { it.name == transaction.fromAccount.name }.balance -= transaction.amount
        }
        if (transaction.toAccount) {
            newRow.entries.find { it.name == transaction.toAccount.name }.balance += transaction.amount
        }
        newRow
    }

    static transients = ['values']

    static hasMany = [entries:BPTableRowBalance]

    static belongsTo = [table:BPTable]

    static constraints = {
        date nullable: false
        name nullable: false, empty: false
        amount nullable: false
        entries nullable: true, emtpy: true
        table nullable: false
    }
}
