package budgetplanner

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BPBudgetItemGeneratorController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            def budgetItemGenerators = User.findById(userId).budgetItemGenerators
            respond budgetItemGenerators.toList(), model: [BPBudgetItemGeneratorCount: budgetItemGenerators.size()]
        } else {
            respond BPBudgetItemGenerator.list(), model: [BPBudgetItemGeneratorCount: BPBudgetItemGenerator.count()]
        }
    }

    def show(BPBudgetItemGenerator BPBudgetItemGenerator) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPBudgetItemGenerator.userId != userId) {notFound()}
        }
        respond BPBudgetItemGenerator
    }

    def create() {
        BPBudgetItemGenerator budgetItemGenerator = new BPBudgetItemGenerator(params)
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (budgetItemGenerator.userId != userId) {notFound()}
        }
        respond budgetItemGenerator
    }

    @Transactional
    def save(BPBudgetItemGenerator BPBudgetItemGenerator) {
        if (BPBudgetItemGenerator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPBudgetItemGenerator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPBudgetItemGenerator.errors, view:'create'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPBudgetItemGenerator.userId != userId) {notFound()}
        }

        BPBudgetItemGenerator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'BPBudgetItemGenerator.label', default: 'BPBudgetItemGenerator'), BPBudgetItemGenerator.id])
                redirect BPBudgetItemGenerator
            }
            '*' { respond BPBudgetItemGenerator, [status: CREATED] }
        }
    }

    def edit(BPBudgetItemGenerator BPBudgetItemGenerator) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPBudgetItemGenerator.userId != userId) {notFound()}
        }
        respond BPBudgetItemGenerator
    }

    @Transactional
    def update(BPBudgetItemGenerator BPBudgetItemGenerator) {
        if (BPBudgetItemGenerator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPBudgetItemGenerator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPBudgetItemGenerator.errors, view:'edit'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPBudgetItemGenerator.userId != userId) {notFound()}
        }

        BPBudgetItemGenerator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BPBudgetItemGenerator.label', default: 'BPBudgetItemGenerator'), BPBudgetItemGenerator.id])
                redirect BPBudgetItemGenerator
            }
            '*'{ respond BPBudgetItemGenerator, [status: OK] }
        }
    }

    @Transactional
    def delete(BPBudgetItemGenerator BPBudgetItemGenerator) {

        if (BPBudgetItemGenerator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPBudgetItemGenerator.userId != userId) {notFound()}
        }

        BPBudgetItemGenerator.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPBudgetItemGenerator.label', default: 'BPBudgetItemGenerator'), BPBudgetItemGenerator.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'BPBudgetItemGenerator.label', default: 'BPBudgetItemGenerator'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
