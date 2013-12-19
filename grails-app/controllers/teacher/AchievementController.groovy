package teacher

import org.springframework.dao.DataIntegrityViolationException

class AchievementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [achievementInstanceList: Achievement.list(params), achievementInstanceTotal: Achievement.count()]
    }

    def create() {
        [achievementInstance: new Achievement(params)]
    }

    def save() {
        def achievementInstance = new Achievement(params)
        if (!achievementInstance.save(flush: true)) {
            render(view: "create", model: [achievementInstance: achievementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'achievement.label', default: 'Achievement'), achievementInstance.id])
        redirect(action: "show", id: achievementInstance.id)
    }

    def show(Long id) {
        def achievementInstance = Achievement.get(id)
        if (!achievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "list")
            return
        }

        [achievementInstance: achievementInstance]
    }

    def edit(Long id) {
        def achievementInstance = Achievement.get(id)
        if (!achievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "list")
            return
        }

        [achievementInstance: achievementInstance]
    }

    def update(Long id, Long version) {
        def achievementInstance = Achievement.get(id)
        if (!achievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (achievementInstance.version > version) {
                achievementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'achievement.label', default: 'Achievement')] as Object[],
                          "Another user has updated this Achievement while you were editing")
                render(view: "edit", model: [achievementInstance: achievementInstance])
                return
            }
        }

        achievementInstance.properties = params

        if (!achievementInstance.save(flush: true)) {
            render(view: "edit", model: [achievementInstance: achievementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'achievement.label', default: 'Achievement'), achievementInstance.id])
        redirect(action: "show", id: achievementInstance.id)
    }

    def delete(Long id) {
        def achievementInstance = Achievement.get(id)
        if (!achievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "list")
            return
        }

        try {
            achievementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'achievement.label', default: 'Achievement'), id])
            redirect(action: "show", id: id)
        }
    }
}
