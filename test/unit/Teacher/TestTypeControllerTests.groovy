package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(TestTypeController)
@Mock(TestType)
class TestTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/testType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.testTypeInstanceList.size() == 0
        assert model.testTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.testTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.testTypeInstance != null
        assert view == '/testType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/testType/show/1'
        assert controller.flash.message != null
        assert TestType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/testType/list'

        populateValidParams(params)
        def testType = new TestType(params)

        assert testType.save() != null

        params.id = testType.id

        def model = controller.show()

        assert model.testTypeInstance == testType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/testType/list'

        populateValidParams(params)
        def testType = new TestType(params)

        assert testType.save() != null

        params.id = testType.id

        def model = controller.edit()

        assert model.testTypeInstance == testType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/testType/list'

        response.reset()

        populateValidParams(params)
        def testType = new TestType(params)

        assert testType.save() != null

        // test invalid parameters in update
        params.id = testType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/testType/edit"
        assert model.testTypeInstance != null

        testType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/testType/show/$testType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        testType.clearErrors()

        populateValidParams(params)
        params.id = testType.id
        params.version = -1
        controller.update()

        assert view == "/testType/edit"
        assert model.testTypeInstance != null
        assert model.testTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/testType/list'

        response.reset()

        populateValidParams(params)
        def testType = new TestType(params)

        assert testType.save() != null
        assert TestType.count() == 1

        params.id = testType.id

        controller.delete()

        assert TestType.count() == 0
        assert TestType.get(testType.id) == null
        assert response.redirectedUrl == '/testType/list'
    }
}
