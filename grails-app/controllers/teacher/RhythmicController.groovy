package teacher

import org.springframework.dao.DataIntegrityViolationException

class RhythmicController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [ritmicaInstanceList: Rhythmic.list(params), ritmicaInstanceTotal: Ritmica.count()]
    }

    def create() {
        [ritmicaInstance: new Rhythmic(params)]
    }

    def save() {
        def ritmicaInstance = new Rhythmic(params)
        if (!ritmicaInstance.save(flush: true)) {
            render(view: "create", model: [ritmicaInstance: ritmicaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), ritmicaInstance.id])
        redirect(action: "show", id: ritmicaInstance.id)
    }

    def show(Long id) {
        def ritmicaInstance = Rhythmic.get(id)
        if (!ritmicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        [ritmicaInstance: ritmicaInstance]
    }

    def edit(Long id) {
        def ritmicaInstance = Rhythmic.get(id)
        if (!ritmicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        [ritmicaInstance: ritmicaInstance]
    }

    def update(Long id, Long version) {
        def ritmicaInstance = Rhythmic.get(id)
        if (!ritmicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ritmicaInstance.version > version) {
                ritmicaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ritmica.label', default: 'Ritmica')] as Object[],
                          "Another user has updated this Ritmica while you were editing")
                render(view: "edit", model: [ritmicaInstance: ritmicaInstance])
                return
            }
        }

        ritmicaInstance.properties = params

        if (!ritmicaInstance.save(flush: true)) {
            render(view: "edit", model: [ritmicaInstance: ritmicaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), ritmicaInstance.id])
        redirect(action: "show", id: ritmicaInstance.id)
    }

    def delete(Long id) {
        def ritmicaInstance = Rhythmic.get(id)
        if (!ritmicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "list")
            return
        }

        try {
            ritmicaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ritmica.label', default: 'Ritmica'), id])
            redirect(action: "show", id: id)
        }
    }
}
