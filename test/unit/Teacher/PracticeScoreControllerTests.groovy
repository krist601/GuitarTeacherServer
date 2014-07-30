package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(PracticeScoreController)
@Mock(PracticeScore)
class PracticeScoreControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/practiceScore/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.practiceScoreInstanceList.size() == 0
        assert model.practiceScoreInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.practiceScoreInstance != null
    }

    void testSave() {
        controller.save()

        assert model.practiceScoreInstance != null
        assert view == '/practiceScore/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/practiceScore/show/1'
        assert controller.flash.message != null
        assert PracticeScore.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/practiceScore/list'

        populateValidParams(params)
        def practiceScore = new PracticeScore(params)

        assert practiceScore.save() != null

        params.id = practiceScore.id

        def model = controller.show()

        assert model.practiceScoreInstance == practiceScore
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/practiceScore/list'

        populateValidParams(params)
        def practiceScore = new PracticeScore(params)

        assert practiceScore.save() != null

        params.id = practiceScore.id

        def model = controller.edit()

        assert model.practiceScoreInstance == practiceScore
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/practiceScore/list'

        response.reset()

        populateValidParams(params)
        def practiceScore = new PracticeScore(params)

        assert practiceScore.save() != null

        // test invalid parameters in update
        params.id = practiceScore.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/practiceScore/edit"
        assert model.practiceScoreInstance != null

        practiceScore.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/practiceScore/show/$practiceScore.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        practiceScore.clearErrors()

        populateValidParams(params)
        params.id = practiceScore.id
        params.version = -1
        controller.update()

        assert view == "/practiceScore/edit"
        assert model.practiceScoreInstance != null
        assert model.practiceScoreInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/practiceScore/list'

        response.reset()

        populateValidParams(params)
        def practiceScore = new PracticeScore(params)

        assert practiceScore.save() != null
        assert PracticeScore.count() == 1

        params.id = practiceScore.id

        controller.delete()

        assert PracticeScore.count() == 0
        assert PracticeScore.get(practiceScore.id) == null
        assert response.redirectedUrl == '/practiceScore/list'
    }
}
