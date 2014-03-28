package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(AcordeController)
@Mock(Acorde)
class AcordeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/acorde/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.acordeInstanceList.size() == 0
        assert model.acordeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.acordeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.acordeInstance != null
        assert view == '/acorde/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/acorde/show/1'
        assert controller.flash.message != null
        assert Acorde.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/acorde/list'

        populateValidParams(params)
        def acorde = new Acorde(params)

        assert acorde.save() != null

        params.id = acorde.id

        def model = controller.show()

        assert model.acordeInstance == acorde
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/acorde/list'

        populateValidParams(params)
        def acorde = new Acorde(params)

        assert acorde.save() != null

        params.id = acorde.id

        def model = controller.edit()

        assert model.acordeInstance == acorde
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/acorde/list'

        response.reset()

        populateValidParams(params)
        def acorde = new Acorde(params)

        assert acorde.save() != null

        // test invalid parameters in update
        params.id = acorde.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/acorde/edit"
        assert model.acordeInstance != null

        acorde.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/acorde/show/$acorde.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        acorde.clearErrors()

        populateValidParams(params)
        params.id = acorde.id
        params.version = -1
        controller.update()

        assert view == "/acorde/edit"
        assert model.acordeInstance != null
        assert model.acordeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/acorde/list'

        response.reset()

        populateValidParams(params)
        def acorde = new Acorde(params)

        assert acorde.save() != null
        assert Acorde.count() == 1

        params.id = acorde.id

        controller.delete()

        assert Acorde.count() == 0
        assert Acorde.get(acorde.id) == null
        assert response.redirectedUrl == '/acorde/list'
    }
}
