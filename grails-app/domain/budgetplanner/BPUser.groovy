package budgetplanner

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class BPUser implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String name
	Date birthDate

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<BPRole> getAuthorities() {
		BPUserRole.findAllByBPUser2(this)*.BPRole
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

	static hasMany = [scenarios:BPScenario]

	static transients = ['springSecurityService']

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		name nullable: false, empty: false
		birthDate nullable: false
		scenarios nullable: true, empty: true
	}

	static mapping = {
		password column: '`password`'
	}
}
