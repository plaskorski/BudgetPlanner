package budgetplanner

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class BPUserRole implements Serializable {

	private static final long serialVersionUID = 1

	BPUser BPUser2
	BPRole BPRole

	@Override
	boolean equals(other) {
		if (other instanceof BPUserRole) {
			other.BPUser2Id == BPUser2?.id && other.BPRoleId == BPRole?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (BPUser2) builder.append(BPUser2.id)
		if (BPRole) builder.append(BPRole.id)
		builder.toHashCode()
	}

	static BPUserRole get(long BPUser2Id, long BPRoleId) {
		criteriaFor(BPUser2Id, BPRoleId).get()
	}

	static boolean exists(long BPUser2Id, long BPRoleId) {
		criteriaFor(BPUser2Id, BPRoleId).count()
	}

	private static DetachedCriteria criteriaFor(long BPUser2Id, long BPRoleId) {
		BPUserRole.where {
			BPUser2 == BPUser2.load(BPUser2Id) &&
			BPRole == BPRole.load(BPRoleId)
		}
	}

	static BPUserRole create(BPUser BPUser2, BPRole BPRole) {
		def instance = new BPUserRole(BPUser2: BPUser2, BPRole: BPRole)
		instance.save()
		instance
	}

	static boolean remove(BPUser u, BPRole r) {
		if (u != null && r != null) {
			BPUserRole.where { BPUser2 == u && BPRole == r }.deleteAll()
		}
	}

	static int removeAll(BPUser u) {
		u == null ? 0 : BPUserRole.where { BPUser2 == u }.deleteAll()
	}

	static int removeAll(BPRole r) {
		r == null ? 0 : BPUserRole.where { BPRole == r }.deleteAll()
	}

	static constraints = {
		BPRole validator: { BPRole r, BPUserRole ur ->
			if (ur.BPUser2?.id) {
				BPUserRole.withNewSession {
					if (BPUserRole.exists(ur.BPUser2.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['BPUser', 'BPRole']
		version false
	}
}
