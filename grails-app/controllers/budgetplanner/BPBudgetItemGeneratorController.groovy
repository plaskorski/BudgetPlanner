package budgetplanner

import org.springframework.security.access.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured([Role.ROLE_ADMIN,Role.ROLE_USER])
@Transactional(readOnly = true)
class BPBudgetItemGeneratorController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPBudgetItemGenerator.list(), model: [BPBudgetItemGeneratorCount: BPBudgetItemGenerator.count()]
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority }) {
                        def items = user.budgetItemGenerators
                        respond items.toList(), model: [BPBudgetItemGeneratorCount: items.size()]
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
            respond BPBudgetItemGenerator.list(), model: [BPBudgetItemGeneratorCount: BPBudgetItemGenerator.count()]
        }
    }

    def show(BPBudgetItemGenerator BPBudgetItemGenerator) {
        if (springSecurityService) {
            def userId = springSecurityService.isLoggedIn() ? springSecurityService.getPrincipal()?.getId() : null
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_ADMIN in user.authorities.collect { it.authority }) {
                        respond BPBudgetItemGenerator
                    } else if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItemGenerator.userId == userId)) {
                        redirect controller:"BPScenario", action:"show", id:BPBudgetItemGenerator.scenarioId
                        //respond BPBudgetItemGenerator
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
            respond BPBudgetItemGenerator
        }
    }

    def create() {
        if (springSecurityService) {
            BPBudgetItemGenerator newItem = new BPBudgetItemGenerator(params)
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
            respond new BPBudgetItemGenerator(params)
        }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItemGenerator.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItemGenerator.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItemGenerator.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
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
            if (userId) {
                def user = User.findById(userId)
                if (user) {
                    if (Role.ROLE_USER in user.authorities.collect { it.authority } & (BPBudgetItemGenerator.userId != userId)) {
                        notFound()
                    }
                } else {
                    notFound()
                }
            } else {
                notFound()
            }
        }
        def scenario = BPBudgetItemGenerator.getScenarioId()

        BPBudgetItemGenerator.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BPBudgetItemGenerator.label', default: 'BPBudgetItemGenerator'), BPBudgetItemGenerator.id])
                redirect controller:"BPScenario", action:"show", id:scenario
            }
            '*'{ redirect controller:"BPScenario", action:"show", id:scenario }
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
