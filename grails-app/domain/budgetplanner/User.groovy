package budgetplanner

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import org.joda.time.LocalDate

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String name
	LocalDate birthDate

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static hasMany = [scenarios:BPScenario,
                      accounts:BPAccount,
                      budgetItems:BPBudgetItem,
                      budgetItemGenerators:BPBudgetItemGenerator,
                      tables:BPTable]

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		name nullable: false, empty: false
		birthDate nullable: false
		scenarios nullable: true, empty: true
		accounts nullable: true, empty: true
		budgetItems nullable: true, empty: true
        budgetItemGenerators nullable: true, empty: true
        tables nullable: true, empty: true
	}

	static mapping = {
		password column: '`password`'
	}
}
