package budgetplanner

import grails.transaction.Transactional

@Transactional
class BPLedgerService {

    def generateTransactions(BPBudgetItemGenerator generator) {
        BPBudgetItem[] transactions = []
        // TODO: implement this
        transactions
    }

    def generateRow(BPTableRow row, BPBudgetItem transaction) {
        BPTableRow newRow = new BPTableRow(
                date:transaction.date,
                name:transaction.name,
                amount:transaction.amount,
                tags:transaction.tags,
                ledger:row.ledger,
                entries:row.entries
        )
        if (transaction.fromAccount) {
            newRow.entries.find { val -> val.name == transaction.fromAccount.name }.balance -= transaction.amount
        }
        if (transaction.toAccount) {
            newRow.entries.find { val -> val.name == transaction.toAccount.name }.balance += transaction.amount
        }
        newRow
    }

    def createLedger(BPScenario scenario) {

        // Create ledger
        BPTable ledger = new BPTable(scenario:scenario)
        def hdr = []
        hdr<<"Date"<<"Name"<<"Tags"<<"Amount"
        scenario.accounts.sort { a,b -> a.name <=> b.name }.each { val -> hdr<<val }
        ledger.header = hdr

        // Generate and sort transactions
        BPBudgetItem[] transactions = []
        scenario.transactions.each { val -> transactions<<val }
        scenario.generators.each { val -> transactions<<generateTransactions(val) }
        transactions = transactions.sort {a,b -> a.date <=> b.date}

        // Loop over transactions and generate rows
        BPTableRow[] rows = []
        BPTableRow row
        BPTableRow prevRow = new BPTableRow(
                date:scenario.startDate,
                name:"ScenarioStart",
                amount: 0,
                tags:[],
                ledger:ledger,
                entries: []
        )
        scenario.accounts.each { val ->
            BPTableRowBalance entry = new BPTableRowBalance(name: val.name, balance: val.balance)
            prevRow.entries << entry
        }
        rows<<prevRow
        transactions.each { val ->
            row = generateRow(prevRow,val)
            rows<<row
            prevRow = row
        }
        ledger.rows = rows

        // Return ledger
        ledger
    }
}
