package budgetplanner

import grails.test.mixin.*
import spock.lang.*

@TestFor(BPBudgetItemGeneratorController)
@Mock(BPBudgetItemGenerator)
class BPBudgetItemGeneratorControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["name"] = "TestItem"
        params["startDate"] = new Date(year: 2017,month: 6,date: 1)
        params["endDate"] = new Date(year: 2017,month: 12,date: 31)
        params["intervalType"] = budgetplanner.IntervalType.MONTH
        params["intervalValue"] = 1
        params["amount"] = 10000
        params["fromAccount"] = new BPAccount(name: "1")
        params["toAccount"] = new BPAccount(name: "2")
        params["scenario"] = new BPScenario(
                startDate: new Date(year:2017,month: 1,date: 1),
                endDate: new Date(year: 2017,month: 12,date: 31)
        )
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.BPBudgetItemGeneratorList
            model.BPBudgetItemGeneratorCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.BPBudgetItemGenerator!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def BPBudgetItemGenerator = new BPBudgetItemGenerator()
            BPBudgetItemGenerator.validate()
            controller.save(BPBudgetItemGenerator)

        then:"The create view is rendered again with the correct model"
            model.BPBudgetItemGenerator!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            BPBudgetItemGenerator = new BPBudgetItemGenerator(params)

            controller.save(BPBudgetItemGenerator)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/BPBudgetItemGenerator/show/1'
            controller.flash.message != null
            BPBudgetItemGenerator.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def BPBudgetItemGenerator = new BPBudgetItemGenerator(params)
            controller.show(BPBudgetItemGenerator)

        then:"A model is populated containing the domain instance"
            model.BPBudgetItemGenerator == BPBudgetItemGenerator
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def BPBudgetItemGenerator = new BPBudgetItemGenerator(params)
            controller.edit(BPBudgetItemGenerator)

        then:"A model is populated containing the domain instance"
            model.BPBudgetItemGenerator == BPBudgetItemGenerator
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/BPBudgetItemGenerator/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def BPBudgetItemGenerator = new BPBudgetItemGenerator()
            BPBudgetItemGenerator.validate()
            controller.update(BPBudgetItemGenerator)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.BPBudgetItemGenerator == BPBudgetItemGenerator

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            BPBudgetItemGenerator = new BPBudgetItemGenerator(params).save(flush: true)
            controller.update(BPBudgetItemGenerator)

        then:"A redirect is issued to the show action"
            BPBudgetItemGenerator != null
            response.redirectedUrl == "/BPBudgetItemGenerator/show/$BPBudgetItemGenerator.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/BPBudgetItemGenerator/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def BPBudgetItemGenerator = new BPBudgetItemGenerator(params).save(flush: true)

        then:"It exists"
            BPBudgetItemGenerator.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(BPBudgetItemGenerator)

        then:"The instance is deleted"
            BPBudgetItemGenerator.count() == 0
            response.redirectedUrl == '/BPBudgetItemGenerator/index'
            flash.message != null
    }
}
