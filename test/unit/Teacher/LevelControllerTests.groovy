package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(LevelController)
@Mock(Level)
class LevelControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/level/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.levelInstanceList.size() == 0
        assert model.levelInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.levelInstance != null
    }

    void testSave() {
        controller.save()

        assert model.levelInstance != null
        assert view == '/level/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/level/show/1'
        assert controller.flash.message != null
        assert Level.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/level/list'

        populateValidParams(params)
        def level = new Level(params)

        assert level.save() != null

        params.id = level.id

        def model = controller.show()

        assert model.levelInstance == level
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/level/list'

        populateValidParams(params)
        def level = new Level(params)

        assert level.save() != null

        params.id = level.id

        def model = controller.edit()

        assert model.levelInstance == level
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/level/list'

        response.reset()

        populateValidParams(params)
        def level = new Level(params)

        assert level.save() != null

        // test invalid parameters in update
        params.id = level.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/level/edit"
        assert model.levelInstance != null

        level.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/level/show/$level.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        level.clearErrors()

        populateValidParams(params)
        params.id = level.id
        params.version = -1
        controller.update()

        assert view == "/level/edit"
        assert model.levelInstance != null
        assert model.levelInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/level/list'

        response.reset()

        populateValidParams(params)
        def level = new Level(params)

        assert level.save() != null
        assert Level.count() == 1

        params.id = level.id

        controller.delete()

        assert Level.count() == 0
        assert Level.get(level.id) == null
        assert response.redirectedUrl == '/level/list'
    }
}
