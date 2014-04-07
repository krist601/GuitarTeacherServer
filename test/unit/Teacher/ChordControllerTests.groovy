package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(ChordController)
@Mock(Chord)
class ChordControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/chord/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.chordInstanceList.size() == 0
        assert model.chordInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.chordInstance != null
    }

    void testSave() {
        controller.save()

        assert model.chordInstance != null
        assert view == '/chord/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/chord/show/1'
        assert controller.flash.message != null
        assert Chord.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/chord/list'

        populateValidParams(params)
        def chord = new Chord(params)

        assert chord.save() != null

        params.id = chord.id

        def model = controller.show()

        assert model.chordInstance == chord
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/chord/list'

        populateValidParams(params)
        def chord = new Chord(params)

        assert chord.save() != null

        params.id = chord.id

        def model = controller.edit()

        assert model.chordInstance == chord
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/chord/list'

        response.reset()

        populateValidParams(params)
        def chord = new Chord(params)

        assert chord.save() != null

        // test invalid parameters in update
        params.id = chord.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/chord/edit"
        assert model.chordInstance != null

        chord.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/chord/show/$chord.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        chord.clearErrors()

        populateValidParams(params)
        params.id = chord.id
        params.version = -1
        controller.update()

        assert view == "/chord/edit"
        assert model.chordInstance != null
        assert model.chordInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/chord/list'

        response.reset()

        populateValidParams(params)
        def chord = new Chord(params)

        assert chord.save() != null
        assert Chord.count() == 1

        params.id = chord.id

        controller.delete()

        assert Chord.count() == 0
        assert Chord.get(chord.id) == null
        assert response.redirectedUrl == '/chord/list'
    }
}
