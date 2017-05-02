package budgetplanner

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BPBudgetItemGeneratorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BPBudgetItemGenerator.list(params), model:[BPBudgetItemGeneratorCount: BPBudgetItemGenerator.count()]
    }

    def show(BPBudgetItemGenerator BPBudgetItemGenerator) {
        respond BPBudgetItemGenerator
    }

    def create() {
        respond new BPBudgetItemGenerator(params)
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
