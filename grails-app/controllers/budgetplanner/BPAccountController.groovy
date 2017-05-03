package budgetplanner

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPAccountController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPAccount.list(), model: [BPAccountCount: BPAccount.count()]
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority }) {
                        def accounts = user.accounts
                        respond accounts.toList(), model: [BPAccountCount: accounts.size()]
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
            respond BPAccount.list(), model: [BPAccountCount: BPAccount.count()]
        }
    }

    def show(BPAccount BPAccount) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPAccount
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPAccount.userId == userId)) {
                        respond BPAccount
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
            respond BPAccount
        }
    }

    def create() {
        if (springSecurityService) {
            BPAccount newAccount = new BPAccount(params)
            if (newAccount) {
                def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
                if (userId) {
                    def user = User.findById(userId)
                    if (user) {
                        if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                            respond newAccount
                        } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (newAccount.userId == userId)) {
                            respond newAccount
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
            respond new BPAccount(params)
        }
    }

    @Transactional
    def save(BPAccount BPAccount) {
        if (BPAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPAccount.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPAccount.errors, view:'create'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPAccount.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPAccount.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'BPAccount.label', default: 'BPAccount'), BPAccount.id])
                redirect BPAccount
            }
            '*' { respond BPAccount, [status: CREATED] }
        }
    }

    def edit(BPAccount BPAccount) {

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPAccount.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        respond BPAccount
    }

    @Transactional
    def update(BPAccount BPAccount) {
        if (BPAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (BPAccount.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond BPAccount.errors, view:'edit'
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPAccount.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPAccount.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BPAccount.label', default: 'BPAccount'), BPAccount.id])
                redirect BPAccount
            }
            '*'{ respond BPAccount, [status: OK] }
        }
    }

    @Transactional
    def delete(BPAccount BPAccount) {

        if (BPAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPAccount.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }

        BPAccount.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPAccount.label', default: 'BPAccount'), BPAccount.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'BPAccount.label', default: 'BPAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
