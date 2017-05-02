package budgetplanner

class BPTable {

    def getHeader() {
        def hdr = []
        hdr<<"Date"<<"Name"<<"Amount"
        scenario.accounts.sort{ it.name }.each { hdr << it.name }
        hdr
    }

    static transients = ['header']

    static belongsTo = [scenario:BPScenario,user:User]

    static hasMany = [rows:BPTableRow]

    static constraints = {
        rows nullable: true, empty: true
        scenario nullable: false
        user nullable: false
    }
}
