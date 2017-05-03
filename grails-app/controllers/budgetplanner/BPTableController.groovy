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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPTable.list(), model: [BPTableCount: BPTable.count()]
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority }) {
                        def items = user.tables
                        respond items.toList(), model: [BPTableCount: items.size()]
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
            respond BPTable.list(), model: [BPTableCount: BPTable.count()]
        }
    }

    def show(BPTable BPTable) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPTable
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPTable.userId == userId)) {
                        respond BPTable
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
            respond BPTable
        }
    }

    def create() {
        if (springSecurityService) {
            BPTable newItem = new BPTable(params)
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
            respond new BPTable(params)
        }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPTable.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPTable.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPTable.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPTable.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
