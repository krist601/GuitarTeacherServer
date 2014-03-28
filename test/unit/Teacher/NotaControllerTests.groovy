package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(NotaController)
@Mock(Nota)
class NotaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/nota/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.notaInstanceList.size() == 0
        assert model.notaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.notaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.notaInstance != null
        assert view == '/nota/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/nota/show/1'
        assert controller.flash.message != null
        assert Nota.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/nota/list'

        populateValidParams(params)
        def nota = new Nota(params)

        assert nota.save() != null

        params.id = nota.id

        def model = controller.show()

        assert model.notaInstance == nota
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/nota/list'

        populateValidParams(params)
        def nota = new Nota(params)

        assert nota.save() != null

        params.id = nota.id

        def model = controller.edit()

        assert model.notaInstance == nota
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/nota/list'

        response.reset()

        populateValidParams(params)
        def nota = new Nota(params)

        assert nota.save() != null

        // test invalid parameters in update
        params.id = nota.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/nota/edit"
        assert model.notaInstance != null

        nota.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/nota/show/$nota.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        nota.clearErrors()

        populateValidParams(params)
        params.id = nota.id
        params.version = -1
        controller.update()

        assert view == "/nota/edit"
        assert model.notaInstance != null
        assert model.notaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/nota/list'

        response.reset()

        populateValidParams(params)
        def nota = new Nota(params)

        assert nota.save() != null
        assert Nota.count() == 1

        params.id = nota.id

        controller.delete()

        assert Nota.count() == 0
        assert Nota.get(nota.id) == null
        assert response.redirectedUrl == '/nota/list'
    }
}
