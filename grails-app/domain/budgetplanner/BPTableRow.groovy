package budgetplanner

class BPTableRow {

    Date date
    String name
    String getTagString() { if (tags) {tags.sort {it}.join(",")} else {""} }
    Integer amount
    Integer[] getValues() { entries.sort {a,b->a.name<=>b.name}.collect {val -> val.balance} }

    static transients = ['values']

    static hasMany = [tags:String,
                      entries:BPTableRowBalance]

    static belongsTo = [table:BPTable]

    static constraints = {
        date nullable: false
        name nullable: false, empty: false
        amount nullable: false
        tags nullable: true, empty: true
        entries nullable: true, emtpy: true
        table nullable: false
    }
}
