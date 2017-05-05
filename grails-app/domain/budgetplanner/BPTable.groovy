package budgetplanner

class BPTable {

    def getHeader() {
        def hdr = []
        hdr<<"Date"<<"Name"<<"Amount"
        scenario.accounts.sort{ it.name }.each { hdr << it.name }
        hdr
    }

    def getAccounts() {
        def hdr = []
        scenario.accounts.sort{ it.name }.each { hdr << it.name }
        hdr
    }

    def getColumns() {
        def result = [:]
        result.accounts = []
        result.date = []
        for (account in accounts) {
            result.accounts << account
            result.put(account,[])
        }
        for (row in rows) {
            result.get("date") << row.date
            for (entry in row.entries) {
                result.get(entry.name) << (entry.balance/100)
            }
        }
        result
    }

    static transients = ['header','accounts','columns']

    static belongsTo = [scenario:BPScenario,user:User]

    static hasMany = [rows:BPTableRow]

    static constraints = {
        rows nullable: true, empty: true
        scenario nullable: false
        user nullable: false
    }
}
