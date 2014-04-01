package teacher

import org.springframework.dao.DataIntegrityViolationException

class RhythmicController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [rhythmicInstanceList: Rhythmic.list(params), rhythmicInstanceTotal: Ritmica.count()]
    }

    def create() {
        [rhythmicInstance: new Rhythmic(params)]
    }

    def save() {
        def rhythmicInstance = new Rhythmic(params)
        if (!rhythmicInstance.save(flush: true)) {
            render(view: "create", model: [rhythmicInstance: rhythmicInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), rhythmicInstance.id])
        redirect(action: "show", id: rhythmicInstance.id)
    }

    def show(Long id) {
        def rhythmicInstance = Rhythmic.get(id)
        if (!rhythmicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        [rhythmicInstance: rhythmicInstance]
    }

    def edit(Long id) {
        def rhythmicInstance = Rhythmic.get(id)
        if (!rhythmicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        [rhythmicInstance: rhythmicInstance]
    }

    def update(Long id, Long version) {
        def rhythmicInstance = Rhythmic.get(id)
        if (!rhythmicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (rhythmicInstance.version > version) {
                rhythmicInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'rhythmic.label', default: 'Ritmica')] as Object[],
                          "Another user has updated this Ritmica while you were editing")
                render(view: "edit", model: [rhythmicInstance: rhythmicInstance])
                return
            }
        }

        rhythmicInstance.properties = params

        if (!rhythmicInstance.save(flush: true)) {
            render(view: "edit", model: [rhythmicInstance: rhythmicInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), rhythmicInstance.id])
        redirect(action: "show", id: rhythmicInstance.id)
    }

    def delete(Long id) {
        def rhythmicInstance = Rhythmic.get(id)
        if (!rhythmicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        try {
            rhythmicInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'rhythmic.label', default: 'Ritmica'), id])
            redirect(action: "show", id: id)
        }
    }
}
