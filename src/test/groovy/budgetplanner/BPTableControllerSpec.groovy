package budgetplanner

import grails.test.mixin.*
import spock.lang.*

@TestFor(BPTableController)
@Mock(BPTable)
class BPTableControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["scenario"] = new BPScenario()
        params["user"] = new User()
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.BPTableList
            model.BPTableCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.BPTable!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def BPTable = new BPTable()
            BPTable.validate()
            controller.save(BPTable)

        then:"The create view is rendered again with the correct model"
            model.BPTable!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            BPTable = new BPTable(params)

            controller.save(BPTable)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/BPTable/show/1'
            controller.flash.message != null
            BPTable.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def BPTable = new BPTable(params)
            controller.show(BPTable)

        then:"A model is populated containing the domain instance"
            model.BPTable == BPTable
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def BPTable = new BPTable(params)
            controller.edit(BPTable)

        then:"A model is populated containing the domain instance"
            model.BPTable == BPTable
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/BPTable/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def BPTable = new BPTable()
            BPTable.validate()
            controller.update(BPTable)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.BPTable == BPTable

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            BPTable = new BPTable(params).save(flush: true)
            controller.update(BPTable)

        then:"A redirect is issued to the show action"
            BPTable != null
            response.redirectedUrl == "/BPTable/show/$BPTable.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/BPTable/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def BPTable = new BPTable(params).save(flush: true)

        then:"It exists"
            BPTable.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(BPTable)

        then:"The instance is deleted"
            BPTable.count() == 0
            response.redirectedUrl == '/BPTable/index'
            flash.message != null
    }
}
