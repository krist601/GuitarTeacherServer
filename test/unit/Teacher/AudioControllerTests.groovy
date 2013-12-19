package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(AudioController)
@Mock(Audio)
class AudioControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/audio/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.audioInstanceList.size() == 0
        assert model.audioInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.audioInstance != null
    }

    void testSave() {
        controller.save()

        assert model.audioInstance != null
        assert view == '/audio/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/audio/show/1'
        assert controller.flash.message != null
        assert Audio.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/audio/list'

        populateValidParams(params)
        def audio = new Audio(params)

        assert audio.save() != null

        params.id = audio.id

        def model = controller.show()

        assert model.audioInstance == audio
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/audio/list'

        populateValidParams(params)
        def audio = new Audio(params)

        assert audio.save() != null

        params.id = audio.id

        def model = controller.edit()

        assert model.audioInstance == audio
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/audio/list'

        response.reset()

        populateValidParams(params)
        def audio = new Audio(params)

        assert audio.save() != null

        // test invalid parameters in update
        params.id = audio.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/audio/edit"
        assert model.audioInstance != null

        audio.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/audio/show/$audio.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        audio.clearErrors()

        populateValidParams(params)
        params.id = audio.id
        params.version = -1
        controller.update()

        assert view == "/audio/edit"
        assert model.audioInstance != null
        assert model.audioInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/audio/list'

        response.reset()

        populateValidParams(params)
        def audio = new Audio(params)

        assert audio.save() != null
        assert Audio.count() == 1

        params.id = audio.id

        controller.delete()

        assert Audio.count() == 0
        assert Audio.get(audio.id) == null
        assert response.redirectedUrl == '/audio/list'
    }
}
