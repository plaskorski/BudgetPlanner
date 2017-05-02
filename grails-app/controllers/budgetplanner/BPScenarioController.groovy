package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPScenarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BPScenario.list(params), model:[BPScenarioCount: BPScenario.count()]
    }

    def show(BPScenario BPScenario) {
        respond BPScenario
    }

    def create() {
        respond new BPScenario(params)
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
