package teacher

import org.springframework.dao.DataIntegrityViolationException

class PracticeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [practiceInstanceList: Practice.list(params), practiceInstanceTotal: Practice.count()]
    }

    def create() {
        [practiceInstance: new Practice(params)]
    }

    def save() {
        def practiceInstance = new Practice(params)
        if (!practiceInstance.save(flush: true)) {
            render(view: "create", model: [practiceInstance: practiceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'practice.label', default: 'Practice'), practiceInstance.id])
        redirect(action: "show", id: practiceInstance.id)
    }

    def show(Long id) {
        def practiceInstance = Practice.get(id)
        if (!practiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "list")
            return
        }

        [practiceInstance: practiceInstance]
    }

    def edit(Long id) {
        def practiceInstance = Practice.get(id)
        if (!practiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "list")
            return
        }

        [practiceInstance: practiceInstance]
    }

    def update(Long id, Long version) {
        def practiceInstance = Practice.get(id)
        if (!practiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (practiceInstance.version > version) {
                practiceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'practice.label', default: 'Practice')] as Object[],
                          "Another user has updated this Practice while you were editing")
                render(view: "edit", model: [practiceInstance: practiceInstance])
                return
            }
        }

        practiceInstance.properties = params

        if (!practiceInstance.save(flush: true)) {
            render(view: "edit", model: [practiceInstance: practiceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'practice.label', default: 'Practice'), practiceInstance.id])
        redirect(action: "show", id: practiceInstance.id)
    }

    def delete(Long id) {
        def practiceInstance = Practice.get(id)
        if (!practiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "list")
            return
        }

        try {
            practiceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'practice.label', default: 'Practice'), id])
            redirect(action: "show", id: id)
        }
    }
}
