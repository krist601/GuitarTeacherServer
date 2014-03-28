package teacher

import org.springframework.dao.DataIntegrityViolationException

class AcordeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [acordeInstanceList: Acorde.list(params), acordeInstanceTotal: Acorde.count()]
    }

    def create() {
        [acordeInstance: new Acorde(params)]
    }

    def save() {
        def acordeInstance = new Acorde(params)
        if (!acordeInstance.save(flush: true)) {
            render(view: "create", model: [acordeInstance: acordeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'acorde.label', default: 'Acorde'), acordeInstance.id])
        redirect(action: "show", id: acordeInstance.id)
    }

    def show(Long id) {
        def acordeInstance = Acorde.get(id)
        if (!acordeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "list")
            return
        }

        [acordeInstance: acordeInstance]
    }

    def edit(Long id) {
        def acordeInstance = Acorde.get(id)
        if (!acordeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "list")
            return
        }

        [acordeInstance: acordeInstance]
    }

    def update(Long id, Long version) {
        def acordeInstance = Acorde.get(id)
        if (!acordeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (acordeInstance.version > version) {
                acordeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'acorde.label', default: 'Acorde')] as Object[],
                          "Another user has updated this Acorde while you were editing")
                render(view: "edit", model: [acordeInstance: acordeInstance])
                return
            }
        }

        acordeInstance.properties = params

        if (!acordeInstance.save(flush: true)) {
            render(view: "edit", model: [acordeInstance: acordeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'acorde.label', default: 'Acorde'), acordeInstance.id])
        redirect(action: "show", id: acordeInstance.id)
    }

    def delete(Long id) {
        def acordeInstance = Acorde.get(id)
        if (!acordeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "list")
            return
        }

        try {
            acordeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'acorde.label', default: 'Acorde'), id])
            redirect(action: "show", id: id)
        }
    }
}
