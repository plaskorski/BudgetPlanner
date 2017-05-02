package budgetplanner

class BPScenario {

    String name
    String description
    Date startDate
    Date endDate

    void populateTable() {

        // Create table
        table = new BPTable(scenario:this)

        // Generate and sort transactions
        BPBudgetItem[] allTransactions = []
        transactions.each { allTransactions += it }
        generators.each { allTransactions += it.getBudgetItems() }
        allTransactions = allTransactions.sort {a,b -> a.date <=> b.date ?: a.name <=> b.name}

        // Loop over transactions and generate rows
        BPTableRow[] rows = []
        BPTableRow row
        BPTableRow prevRow = new BPTableRow(
                date:startDate,
                name:"ScenarioStart",
                amount: 0,
                tags:[],
                table:table,
                entries: []
        )
        accounts.each {
            BPTableRowBalance entry = new BPTableRowBalance(name: it.name, balance: it.balance)
            prevRow.entries << entry
        }
        rows += prevRow
        allTransactions.each {
            row = prevRow.getNextRow(it)
            rows += row
            prevRow = row
        }
        table.rows = rows
    }

    static belongsTo = [user:User]

    static hasOne = [table:BPTable]

    static hasMany = [accounts:BPAccount,
                      transactions:BPBudgetItem,
                      generators:BPBudgetItemGenerator]

    static constraints = {
        name nullable: false, empty: false
        description nullable: false, empty: false
        startDate nullable: false
        endDate nullable: false, validator: {val, obj ->
            if (obj.startDate) {
                val.after(obj.startDate)
            } else {false}
        }
        user nullable: false
        accounts nullable: true, empty: true
        transactions nullable: true, empty: true
        generators nullable: true, empty: true
        table nullable: true
    }

}
