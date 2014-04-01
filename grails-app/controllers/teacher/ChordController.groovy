package teacher

import org.springframework.dao.DataIntegrityViolationException

class ChordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [chordInstanceList: Chord.list(params), chordInstanceTotal: Chord.count()]
    }

    def create() {
        [chordInstance: new Chord(params)]
    }

    def save() {
        def chordInstance = new Chord(params)
        if (!chordInstance.save(flush: true)) {
            render(view: "create", model: [chordInstance: chordInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'chord.label', default: 'Chord'), chordInstance.id])
        redirect(action: "show", id: chordInstance.id)
    }

    def show(Long id) {
        def chordInstance = Chord.get(id)
        if (!chordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "list")
            return
        }

        [chordInstance: chordInstance]
    }

    def edit(Long id) {
        def chordInstance = Chord.get(id)
        if (!chordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "list")
            return
        }

        [chordInstance: chordInstance]
    }

    def update(Long id, Long version) {
        def chordInstance = Chord.get(id)
        if (!chordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (chordInstance.version > version) {
                chordInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'chord.label', default: 'Chord')] as Object[],
                          "Another user has updated this Chord while you were editing")
                render(view: "edit", model: [chordInstance: chordInstance])
                return
            }
        }

        chordInstance.properties = params

        if (!chordInstance.save(flush: true)) {
            render(view: "edit", model: [chordInstance: chordInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'chord.label', default: 'Chord'), chordInstance.id])
        redirect(action: "show", id: chordInstance.id)
    }

    def delete(Long id) {
        def chordInstance = Chord.get(id)
        if (!chordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "list")
            return
        }

        try {
            chordInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'chord.label', default: 'Chord'), id])
            redirect(action: "show", id: id)
        }
    }
}

