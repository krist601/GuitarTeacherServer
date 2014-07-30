package teacher

import org.springframework.dao.DataIntegrityViolationException

class PracticeScoreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [practiceScoreInstanceList: PracticeScore.list(params), practiceScoreInstanceTotal: PracticeScore.count()]
    }

    def create() {
        [practiceScoreInstance: new PracticeScore(params)]
    }

    def save() {
        def practiceScoreInstance = new PracticeScore(params)
        if (!practiceScoreInstance.save(flush: true)) {
            render(view: "create", model: [practiceScoreInstance: practiceScoreInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), practiceScoreInstance.id])
        redirect(action: "show", id: practiceScoreInstance.id)
    }

    def show(Long id) {
        def practiceScoreInstance = PracticeScore.get(id)
        if (!practiceScoreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "list")
            return
        }

        [practiceScoreInstance: practiceScoreInstance]
    }

    def edit(Long id) {
        def practiceScoreInstance = PracticeScore.get(id)
        if (!practiceScoreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "list")
            return
        }

        [practiceScoreInstance: practiceScoreInstance]
    }

    def update(Long id, Long version) {
        def practiceScoreInstance = PracticeScore.get(id)
        if (!practiceScoreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (practiceScoreInstance.version > version) {
                practiceScoreInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'practiceScore.label', default: 'PracticeScore')] as Object[],
                          "Another user has updated this PracticeScore while you were editing")
                render(view: "edit", model: [practiceScoreInstance: practiceScoreInstance])
                return
            }
        }

        practiceScoreInstance.properties = params

        if (!practiceScoreInstance.save(flush: true)) {
            render(view: "edit", model: [practiceScoreInstance: practiceScoreInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), practiceScoreInstance.id])
        redirect(action: "show", id: practiceScoreInstance.id)
    }

    def delete(Long id) {
        def practiceScoreInstance = PracticeScore.get(id)
        if (!practiceScoreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "list")
            return
        }

        try {
            practiceScoreInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'practiceScore.label', default: 'PracticeScore'), id])
            redirect(action: "show", id: id)
        }
    }
}
