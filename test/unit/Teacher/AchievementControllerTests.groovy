package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(AchievementController)
@Mock(Achievement)
class AchievementControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/achievement/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.achievementInstanceList.size() == 0
        assert model.achievementInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.achievementInstance != null
    }

    void testSave() {
        controller.save()

        assert model.achievementInstance != null
        assert view == '/achievement/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/achievement/show/1'
        assert controller.flash.message != null
        assert Achievement.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/achievement/list'

        populateValidParams(params)
        def achievement = new Achievement(params)

        assert achievement.save() != null

        params.id = achievement.id

        def model = controller.show()

        assert model.achievementInstance == achievement
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/achievement/list'

        populateValidParams(params)
        def achievement = new Achievement(params)

        assert achievement.save() != null

        params.id = achievement.id

        def model = controller.edit()

        assert model.achievementInstance == achievement
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/achievement/list'

        response.reset()

        populateValidParams(params)
        def achievement = new Achievement(params)

        assert achievement.save() != null

        // test invalid parameters in update
        params.id = achievement.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/achievement/edit"
        assert model.achievementInstance != null

        achievement.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/achievement/show/$achievement.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        achievement.clearErrors()

        populateValidParams(params)
        params.id = achievement.id
        params.version = -1
        controller.update()

        assert view == "/achievement/edit"
        assert model.achievementInstance != null
        assert model.achievementInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/achievement/list'

        response.reset()

        populateValidParams(params)
        def achievement = new Achievement(params)

        assert achievement.save() != null
        assert Achievement.count() == 1

        params.id = achievement.id

        controller.delete()

        assert Achievement.count() == 0
        assert Achievement.get(achievement.id) == null
        assert response.redirectedUrl == '/achievement/list'
    }
}
