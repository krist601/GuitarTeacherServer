package teacher

import org.springframework.dao.DataIntegrityViolationException

class FriendController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [friendInstanceList: Friend.list(params), friendInstanceTotal: Friend.count()]
    }

    def create() {
        [friendInstance: new Friend(params)]
    }

    def save() {
        def friendInstance = new Friend(params)
        if (!friendInstance.save(flush: true)) {
            render(view: "create", model: [friendInstance: friendInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'friend.label', default: 'Friend'), friendInstance.id])
        redirect(action: "show", id: friendInstance.id)
    }

    def show(Long id) {
        def friendInstance = Friend.get(id)
        if (!friendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "list")
            return
        }

        [friendInstance: friendInstance]
    }

    def edit(Long id) {
        def friendInstance = Friend.get(id)
        if (!friendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "list")
            return
        }

        [friendInstance: friendInstance]
    }

    def update(Long id, Long version) {
        def friendInstance = Friend.get(id)
        if (!friendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (friendInstance.version > version) {
                friendInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'friend.label', default: 'Friend')] as Object[],
                          "Another user has updated this Friend while you were editing")
                render(view: "edit", model: [friendInstance: friendInstance])
                return
            }
        }

        friendInstance.properties = params

        if (!friendInstance.save(flush: true)) {
            render(view: "edit", model: [friendInstance: friendInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'friend.label', default: 'Friend'), friendInstance.id])
        redirect(action: "show", id: friendInstance.id)
    }

    def delete(Long id) {
        def friendInstance = Friend.get(id)
        if (!friendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "list")
            return
        }

        try {
            friendInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'friend.label', default: 'Friend'), id])
            redirect(action: "show", id: id)
        }
    }
}
