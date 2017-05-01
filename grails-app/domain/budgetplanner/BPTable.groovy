package budgetplanner

class BPTable {

    def getHeader() {
        def hdr = []
        hdr<<"Date"<<"Name"<<"Tags"<<"Amount"
        scenario.accounts.sort{ it.name }.each { hdr << it.name }
        hdr
    }

    static transients = ['header']

    static belongsTo = [scenario:BPScenario]

    static hasMany = [rows:BPTableRow]

    static constraints = {
        rows nullable: true, empty: true
        scenario nullable: false
    }
}
