package Services



import org.junit.*
import grails.test.mixin.*

@TestFor(DifficultyController)
@Mock(Difficulty)
class DifficultyControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/difficulty/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.difficultyInstanceList.size() == 0
        assert model.difficultyInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.difficultyInstance != null
    }

    void testSave() {
        controller.save()

        assert model.difficultyInstance != null
        assert view == '/difficulty/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/difficulty/show/1'
        assert controller.flash.message != null
        assert Difficulty.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/difficulty/list'

        populateValidParams(params)
        def difficulty = new Difficulty(params)

        assert difficulty.save() != null

        params.id = difficulty.id

        def model = controller.show()

        assert model.difficultyInstance == difficulty
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/difficulty/list'

        populateValidParams(params)
        def difficulty = new Difficulty(params)

        assert difficulty.save() != null

        params.id = difficulty.id

        def model = controller.edit()

        assert model.difficultyInstance == difficulty
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/difficulty/list'

        response.reset()

        populateValidParams(params)
        def difficulty = new Difficulty(params)

        assert difficulty.save() != null

        // test invalid parameters in update
        params.id = difficulty.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/difficulty/edit"
        assert model.difficultyInstance != null

        difficulty.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/difficulty/show/$difficulty.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        difficulty.clearErrors()

        populateValidParams(params)
        params.id = difficulty.id
        params.version = -1
        controller.update()

        assert view == "/difficulty/edit"
        assert model.difficultyInstance != null
        assert model.difficultyInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/difficulty/list'

        response.reset()

        populateValidParams(params)
        def difficulty = new Difficulty(params)

        assert difficulty.save() != null
        assert Difficulty.count() == 1

        params.id = difficulty.id

        controller.delete()

        assert Difficulty.count() == 0
        assert Difficulty.get(difficulty.id) == null
        assert response.redirectedUrl == '/difficulty/list'
    }
}
