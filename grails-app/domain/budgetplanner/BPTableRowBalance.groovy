package budgetplanner

class BPTableRowBalance {

    String name
    Integer balance

    static belongsTo = [tableRow:BPTableRow]

    static constraints = {
        name nullable: false, empty:false
        balance nullable: false
    }
}
