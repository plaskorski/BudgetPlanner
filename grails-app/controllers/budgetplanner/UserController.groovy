package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured([Role.ROLE_ADMIN])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    @Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
    def show(User user) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def loggedUser = User.findById(userId)
                if (loggedUser) {
                    if (Role.ROLE_ADMIN in loggedUser.authorities.collect { it.authority }) {
                        respond user
                    } else if (Role.ROLE_USER in loggedUser.authorities.collect { it.authority } & (user.id == userId)) {
                        respond user
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
            respond user
        }
    }

    @Secured([Role.ROLE_ANONYMOUS])
    def create() {
        respond new User(params)
    }

    @Transactional
    @Secured([Role.ROLE_ANONYMOUS,Role.ROLE_USER,Role.ROLE_ADMIN])
    def save(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush: true, failOnError: true
        if (springSecurityService) {
            UserRole.create user, Role.findByAuthority(Role.ROLE_USER)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    @Secured([Role.ROLE_ADMIN, Role.ROLE_USER])
    def edit(User user) {

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                if (!(Role.ROLE_ADMIN in user.authorities.collect { it.authority }) | (Role.ROLE_USER in user.authorities.collect { it.authority } & (user.id != userId))) {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        respond user
    }

    @Secured([Role.ROLE_ADMIN, Role.ROLE_USER])
    @Transactional
    def update(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                if (!(Role.ROLE_ADMIN in user.authorities.collect { it.authority }) | (Role.ROLE_USER in user.authorities.collect { it.authority } & (user.id != userId))) {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Secured([Role.ROLE_ADMIN])
    @Transactional
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                if (!(Role.ROLE_ADMIN in user.authorities.collect { it.authority }) | (Role.ROLE_USER in user.authorities.collect { it.authority } & (user.id != userId))) {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
