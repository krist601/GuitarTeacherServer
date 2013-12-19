package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(PlayerAchievementController)
@Mock(PlayerAchievement)
class PlayerAchievementControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/playerAchievement/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.playerAchievementInstanceList.size() == 0
        assert model.playerAchievementInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.playerAchievementInstance != null
    }

    void testSave() {
        controller.save()

        assert model.playerAchievementInstance != null
        assert view == '/playerAchievement/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/playerAchievement/show/1'
        assert controller.flash.message != null
        assert PlayerAchievement.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/playerAchievement/list'

        populateValidParams(params)
        def playerAchievement = new PlayerAchievement(params)

        assert playerAchievement.save() != null

        params.id = playerAchievement.id

        def model = controller.show()

        assert model.playerAchievementInstance == playerAchievement
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/playerAchievement/list'

        populateValidParams(params)
        def playerAchievement = new PlayerAchievement(params)

        assert playerAchievement.save() != null

        params.id = playerAchievement.id

        def model = controller.edit()

        assert model.playerAchievementInstance == playerAchievement
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/playerAchievement/list'

        response.reset()

        populateValidParams(params)
        def playerAchievement = new PlayerAchievement(params)

        assert playerAchievement.save() != null

        // test invalid parameters in update
        params.id = playerAchievement.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/playerAchievement/edit"
        assert model.playerAchievementInstance != null

        playerAchievement.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/playerAchievement/show/$playerAchievement.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        playerAchievement.clearErrors()

        populateValidParams(params)
        params.id = playerAchievement.id
        params.version = -1
        controller.update()

        assert view == "/playerAchievement/edit"
        assert model.playerAchievementInstance != null
        assert model.playerAchievementInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/playerAchievement/list'

        response.reset()

        populateValidParams(params)
        def playerAchievement = new PlayerAchievement(params)

        assert playerAchievement.save() != null
        assert PlayerAchievement.count() == 1

        params.id = playerAchievement.id

        controller.delete()

        assert PlayerAchievement.count() == 0
        assert PlayerAchievement.get(playerAchievement.id) == null
        assert response.redirectedUrl == '/playerAchievement/list'
    }
}
