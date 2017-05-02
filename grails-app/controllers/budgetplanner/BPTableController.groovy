package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPTableController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            def tables = User.findById(userId).tables
            respond tables.toList(), model: [BPTable: tables.size()]
        } else {
            respond BPTable.list(), model: [BPTableCount: BPTable.count()]
        }
    }

    def show(BPTable BPTable) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPTable.userId != userId) {notFound()}
        }
        respond BPTable
    }

    def create() {
        BPTable table = new BPTable(params)
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (table.userId != userId) {notFound()}
        }
        respond table
    }

    @Transactional
    def save(BPTable BPTable) {
        if (BPTable == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPTable.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPTable.errors, view:'create'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPTable.userId != userId) {notFound()}
        }

        BPTable.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'BPTable.label', default: 'BPTable'), BPTable.id])
                redirect BPTable
            }
            '*' { respond BPTable, [status: CREATED] }
        }
    }

    def edit(BPTable BPTable) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPTable.userId != userId) {notFound()}
        }
        respond BPTable
    }

    @Transactional
    def update(BPTable BPTable) {
        if (BPTable == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPTable.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPTable.errors, view:'edit'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPTable.userId != userId) {notFound()}
        }

        BPTable.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BPTable.label', default: 'BPTable'), BPTable.id])
                redirect BPTable
            }
            '*'{ respond BPTable, [status: OK] }
        }
    }

    @Transactional
    def delete(BPTable BPTable) {

        if (BPTable == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (BPTable.userId != userId) {notFound()}
        }

        BPTable.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPTable.label', default: 'BPTable'), BPTable.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'BPTable.label', default: 'BPTable'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
