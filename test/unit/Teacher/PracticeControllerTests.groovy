package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(PracticeController)
@Mock(Practice)
class PracticeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/practice/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.practiceInstanceList.size() == 0
        assert model.practiceInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.practiceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.practiceInstance != null
        assert view == '/practice/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/practice/show/1'
        assert controller.flash.message != null
        assert Practice.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/practice/list'

        populateValidParams(params)
        def practice = new Practice(params)

        assert practice.save() != null

        params.id = practice.id

        def model = controller.show()

        assert model.practiceInstance == practice
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/practice/list'

        populateValidParams(params)
        def practice = new Practice(params)

        assert practice.save() != null

        params.id = practice.id

        def model = controller.edit()

        assert model.practiceInstance == practice
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/practice/list'

        response.reset()

        populateValidParams(params)
        def practice = new Practice(params)

        assert practice.save() != null

        // test invalid parameters in update
        params.id = practice.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/practice/edit"
        assert model.practiceInstance != null

        practice.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/practice/show/$practice.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        practice.clearErrors()

        populateValidParams(params)
        params.id = practice.id
        params.version = -1
        controller.update()

        assert view == "/practice/edit"
        assert model.practiceInstance != null
        assert model.practiceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/practice/list'

        response.reset()

        populateValidParams(params)
        def practice = new Practice(params)

        assert practice.save() != null
        assert Practice.count() == 1

        params.id = practice.id

        controller.delete()

        assert Practice.count() == 0
        assert Practice.get(practice.id) == null
        assert response.redirectedUrl == '/practice/list'
    }
}
