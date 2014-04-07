package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(RhythmicController)
@Mock(Rhythmic)
class RhythmicControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/rhythmic/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.rhythmicInstanceList.size() == 0
        assert model.rhythmicInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.rhythmicInstance != null
    }

    void testSave() {
        controller.save()

        assert model.rhythmicInstance != null
        assert view == '/rhythmic/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/rhythmic/show/1'
        assert controller.flash.message != null
        assert Rhythmic.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/rhythmic/list'

        populateValidParams(params)
        def rhythmic = new Rhythmic(params)

        assert rhythmic.save() != null

        params.id = rhythmic.id

        def model = controller.show()

        assert model.rhythmicInstance == rhythmic
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/rhythmic/list'

        populateValidParams(params)
        def rhythmic = new Rhythmic(params)

        assert rhythmic.save() != null

        params.id = rhythmic.id

        def model = controller.edit()

        assert model.rhythmicInstance == rhythmic
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/rhythmic/list'

        response.reset()

        populateValidParams(params)
        def rhythmic = new Rhythmic(params)

        assert rhythmic.save() != null

        // test invalid parameters in update
        params.id = rhythmic.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/rhythmic/edit"
        assert model.rhythmicInstance != null

        rhythmic.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/rhythmic/show/$rhythmic.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        rhythmic.clearErrors()

        populateValidParams(params)
        params.id = rhythmic.id
        params.version = -1
        controller.update()

        assert view == "/rhythmic/edit"
        assert model.rhythmicInstance != null
        assert model.rhythmicInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/rhythmic/list'

        response.reset()

        populateValidParams(params)
        def rhythmic = new Rhythmic(params)

        assert rhythmic.save() != null
        assert Rhythmic.count() == 1

        params.id = rhythmic.id

        controller.delete()

        assert Rhythmic.count() == 0
        assert Rhythmic.get(rhythmic.id) == null
        assert response.redirectedUrl == '/rhythmic/list'
    }
}
