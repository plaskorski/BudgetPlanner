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
            def scenarios = User.findById(userId).scenarios
            respond scenarios.toList(), model: [BPScenarioCount: scenarios.size()]
        } else {
            respond BPScenario.list(), model: [BPScenarioCount: BPScenario.count()]
        }
    }

    def show(BPScenario BPScenario) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPScenario.user.id != userId) {notFound()}
            respond BPScenario
        } else {
            respond BPScenario
        }
    }

    def create() {
        if (springSecurityService) {
            BPScenario newScenario = new BPScenario(params)
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (newScenario.user.id != userId) {notFound()}
            respond newScenario
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

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPScenario.user.id != userId) {notFound()}
        }

        if (BPScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPScenario.errors, view:'create'
            return
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
            if (BPScenario.user.id != userId) {notFound()}
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

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPScenario.user.id != userId) {notFound()}
        }

        if (BPScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPScenario.errors, view:'edit'
            return
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
            if (BPScenario.user.id != userId) {notFound()}
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
