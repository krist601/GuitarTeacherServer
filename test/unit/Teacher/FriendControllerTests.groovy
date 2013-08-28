package teacher



import org.junit.*
import grails.test.mixin.*

@TestFor(FriendController)
@Mock(Friend)
class FriendControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/friend/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.friendInstanceList.size() == 0
        assert model.friendInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.friendInstance != null
    }

    void testSave() {
        controller.save()

        assert model.friendInstance != null
        assert view == '/friend/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/friend/show/1'
        assert controller.flash.message != null
        assert Friend.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/friend/list'

        populateValidParams(params)
        def friend = new Friend(params)

        assert friend.save() != null

        params.id = friend.id

        def model = controller.show()

        assert model.friendInstance == friend
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/friend/list'

        populateValidParams(params)
        def friend = new Friend(params)

        assert friend.save() != null

        params.id = friend.id

        def model = controller.edit()

        assert model.friendInstance == friend
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/friend/list'

        response.reset()

        populateValidParams(params)
        def friend = new Friend(params)

        assert friend.save() != null

        // test invalid parameters in update
        params.id = friend.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/friend/edit"
        assert model.friendInstance != null

        friend.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/friend/show/$friend.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        friend.clearErrors()

        populateValidParams(params)
        params.id = friend.id
        params.version = -1
        controller.update()

        assert view == "/friend/edit"
        assert model.friendInstance != null
        assert model.friendInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/friend/list'

        response.reset()

        populateValidParams(params)
        def friend = new Friend(params)

        assert friend.save() != null
        assert Friend.count() == 1

        params.id = friend.id

        controller.delete()

        assert Friend.count() == 0
        assert Friend.get(friend.id) == null
        assert response.redirectedUrl == '/friend/list'
    }
}
