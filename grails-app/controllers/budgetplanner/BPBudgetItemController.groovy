package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPBudgetItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BPBudgetItem.list(params), model:[BPBudgetItemCount: BPBudgetItem.count()]
    }

    def show(BPBudgetItem BPBudgetItem) {
        respond BPBudgetItem
    }

    def create() {
        respond new BPBudgetItem(params)
    }

    @Transactional
    def save(BPBudgetItem BPBudgetItem) {
        if (BPBudgetItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPBudgetItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPBudgetItem.errors, view:'create'
            return
        }

        BPBudgetItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'BPBudgetItem.label', default: 'BPBudgetItem'), BPBudgetItem.id])
                redirect BPBudgetItem
            }
            '*' { respond BPBudgetItem, [status: CREATED] }
        }
    }

    def edit(BPBudgetItem BPBudgetItem) {
        respond BPBudgetItem
    }

    @Transactional
    def update(BPBudgetItem BPBudgetItem) {
        if (BPBudgetItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPBudgetItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPBudgetItem.errors, view:'edit'
            return
        }

        BPBudgetItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BPBudgetItem.label', default: 'BPBudgetItem'), BPBudgetItem.id])
                redirect BPBudgetItem
            }
            '*'{ respond BPBudgetItem, [status: OK] }
        }
    }

    @Transactional
    def delete(BPBudgetItem BPBudgetItem) {

        if (BPBudgetItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        BPBudgetItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPBudgetItem.label', default: 'BPBudgetItem'), BPBudgetItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'BPBudgetItem.label', default: 'BPBudgetItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
