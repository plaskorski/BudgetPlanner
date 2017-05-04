package budgetplanner

import grails.test.mixin.*
import spock.lang.*

import org.joda.time.LocalDate

@TestFor(BPBudgetItemController)
@Mock(BPBudgetItem)
class BPBudgetItemControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["name"] = "TestItem"
        params["date"] = new LocalDate(2017,6,1)
        params["amount"] = 10000
        params["fromAccount"] = new BPAccount(name: "1")
        params["toAccount"] = new BPAccount(name: "2")
        params["scenario"] = new BPScenario(
                startDate: new LocalDate(2017, 1, 1),
                endDate: new LocalDate(2017, 12, 31)
        )
        params["user"] = new User()
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.BPBudgetItemList
            model.BPBudgetItemCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.BPBudgetItem!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def BPBudgetItem = new BPBudgetItem()
            BPBudgetItem.validate()
            controller.save(BPBudgetItem)

        then:"The create view is rendered again with the correct model"
            model.BPBudgetItem!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            BPBudgetItem = new BPBudgetItem(params)

            controller.save(BPBudgetItem)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/BPBudgetItem/show/1'
            controller.flash.message != null
            BPBudgetItem.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def BPBudgetItem = new BPBudgetItem(params)
            controller.show(BPBudgetItem)

        then:"A model is populated containing the domain instance"
            model.BPBudgetItem == BPBudgetItem
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def BPBudgetItem = new BPBudgetItem(params)
            controller.edit(BPBudgetItem)

        then:"A model is populated containing the domain instance"
            model.BPBudgetItem == BPBudgetItem
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/BPBudgetItem/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def BPBudgetItem = new BPBudgetItem()
            BPBudgetItem.validate()
            controller.update(BPBudgetItem)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.BPBudgetItem == BPBudgetItem

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            BPBudgetItem = new BPBudgetItem(params).save(flush: true)
            controller.update(BPBudgetItem)

        then:"A redirect is issued to the show action"
            BPBudgetItem != null
            response.redirectedUrl == "/BPBudgetItem/show/$BPBudgetItem.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/BPBudgetItem/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def BPBudgetItem = new BPBudgetItem(params).save(flush: true)

        then:"It exists"
            BPBudgetItem.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(BPBudgetItem)

        then:"The instance is deleted"
            BPBudgetItem.count() == 0
            response.redirectedUrl == '/BPScenario/show'
            flash.message != null
    }
}
