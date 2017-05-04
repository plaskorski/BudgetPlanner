package budgetplanner

import org.springframework.security.access.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPScenarioController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPScenario.list(), model: [BPScenarioCount: BPScenario.count(), userId:userId]
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority }) {
                        def items = user.scenarios
                        respond items.toList(), model: [BPScenarioCount: items.size(), userId:userId]
                    } else {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        } else {
            respond BPScenario.list(), model: [BPScenarioCount: BPScenario.count()]
        }
    }

    def show(BPScenario BPScenario) {
        BPScenario?.populateTable()
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPScenario
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPScenario.userId == userId)) {
                        respond BPScenario
                    } else {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        } else {
            respond BPScenario
        }
    }

    def create() {
        if (springSecurityService) {
            BPScenario newItem = new BPScenario(params)
            if (newItem) {
                def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
                if (userId) {
                    def user = User.findById(userId)
                    if (user) {
                        if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                            respond newItem
                        } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (newItem.userId == userId)) {
                            respond newItem
                        } else {
                            notFound()
                        }
                    } else {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        } else {
            respond new BPScenario(params)
        }
    }

    @Transactional
    def save(BPScenario BPScenario) {

        if (BPScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPScenario.errors, view:'create'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    def authorities = user.authorities.collect { it.authority }
                    if (!(Role.ROLE_USER in authorities) | (BPScenario.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPScenario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'BPScenario.label', default: 'BPScenario'), BPScenario.id])
                redirect BPScenario
            }
            '*' { respond BPScenario, [status: CREATED] }
        }
    }

    def edit(BPScenario BPScenario) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPScenario.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        respond BPScenario
    }

    @Transactional
    def update(BPScenario BPScenario) {

        if (BPScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPScenario.errors, view:'edit'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPScenario.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPScenario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BPScenario.label', default: 'BPScenario'), BPScenario.id])
                redirect BPScenario
            }
            '*'{ respond BPScenario, [status: OK] }
        }
    }

    @Transactional
    def delete(BPScenario BPScenario) {

        if (BPScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPScenario.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPScenario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPScenario.label', default: 'BPScenario'), BPScenario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'BPScenario.label', default: 'BPScenario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
