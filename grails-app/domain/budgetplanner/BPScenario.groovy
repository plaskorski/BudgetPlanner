package budgetplanner

import org.joda.time.LocalDate

class BPScenario {

    String name
    String description
    LocalDate startDate
    LocalDate endDate

    void populateTable() {

        // Create table
        table = new BPTable(scenario:this)

        // Generate and sort transactions
        ArrayList<BPBudgetItem>  allTransactions = new ArrayList<>()
        transactions.each { allTransactions.add(it) }
        generators.each { it.budgetItems().each {val -> allTransactions.add(val)} }
        allTransactions = allTransactions.sort {it.date}

        // Loop over transactions and generate rows
        ArrayList<BPTableRow> rows = new ArrayList<>()
        BPTableRow row
        BPTableRow prevRow = new BPTableRow(
                date:startDate,
                name:"ScenarioStart",
                amount: 0,
                table:table,
                entries: []
        )
        accounts.each {
            BPTableRowBalance entry = new BPTableRowBalance(name: it.name, balance: it.balance)
            prevRow.entries << entry
        }
        rows.add(prevRow)
        allTransactions.each {
            row = prevRow.getNextRow(it)
            rows.add(row)
            prevRow = row
        }
        table.rows = rows
    }

    static belongsTo = [user:User]

    static hasOne = [table:BPTable]

    static hasMany = [accounts:BPAccount,
                      transactions:BPBudgetItem,
                      generators:BPBudgetItemGenerator]

    static mapping = {
        table lazy: false
    }

    static constraints = {
        name nullable: false, empty: false
        description nullable: false, empty: false
        startDate nullable: false
        endDate nullable: false, validator: {val, obj ->
            if (obj.startDate) {
                val.isAfter(obj.startDate)
            } else {false}
        }
        user nullable: false
        accounts nullable: true, empty: true
        transactions nullable: true, empty: true
        generators nullable: true, empty: true
        table nullable: true
    }

}
