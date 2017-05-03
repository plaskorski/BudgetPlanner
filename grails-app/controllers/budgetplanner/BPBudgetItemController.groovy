package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPBudgetItemController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPBudgetItem.list(), model: [BPBudgetItemCount: BPBudgetItem.count()]
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority }) {
                        def items = user.budgetItems
                        respond items.toList(), model: [BPBudgetItemCount: items.size()]
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
            respond BPBudgetItem.list(), model: [BPBudgetItemCount: BPBudgetItem.count()]
        }
    }

    def show(BPBudgetItem BPBudgetItem) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPBudgetItem
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItem.userId == userId)) {
                        respond BPBudgetItem
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
            respond BPBudgetItem
        }
    }

    def create() {
        if (springSecurityService) {
            BPBudgetItem newItem = new BPBudgetItem(params)
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
            respond new BPBudgetItem(params)
        }
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

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItem.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItem.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }
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

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItem.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItem.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
