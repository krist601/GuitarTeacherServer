package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(RitmicaController)
@Mock(Ritmica)
class RitmicaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/ritmica/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ritmicaInstanceList.size() == 0
        assert model.ritmicaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.ritmicaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ritmicaInstance != null
        assert view == '/ritmica/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/ritmica/show/1'
        assert controller.flash.message != null
        assert Ritmica.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/ritmica/list'

        populateValidParams(params)
        def ritmica = new Ritmica(params)

        assert ritmica.save() != null

        params.id = ritmica.id

        def model = controller.show()

        assert model.ritmicaInstance == ritmica
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/ritmica/list'

        populateValidParams(params)
        def ritmica = new Ritmica(params)

        assert ritmica.save() != null

        params.id = ritmica.id

        def model = controller.edit()

        assert model.ritmicaInstance == ritmica
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/ritmica/list'

        response.reset()

        populateValidParams(params)
        def ritmica = new Ritmica(params)

        assert ritmica.save() != null

        // test invalid parameters in update
        params.id = ritmica.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/ritmica/edit"
        assert model.ritmicaInstance != null

        ritmica.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/ritmica/show/$ritmica.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        ritmica.clearErrors()

        populateValidParams(params)
        params.id = ritmica.id
        params.version = -1
        controller.update()

        assert view == "/ritmica/edit"
        assert model.ritmicaInstance != null
        assert model.ritmicaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/ritmica/list'

        response.reset()

        populateValidParams(params)
        def ritmica = new Ritmica(params)

        assert ritmica.save() != null
        assert Ritmica.count() == 1

        params.id = ritmica.id

        controller.delete()

        assert Ritmica.count() == 0
        assert Ritmica.get(ritmica.id) == null
        assert response.redirectedUrl == '/ritmica/list'
    }
}
