package teacher

import org.springframework.dao.DataIntegrityViolationException

class PlayerAchievementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [playerAchievementInstanceList: PlayerAchievement.list(params), playerAchievementInstanceTotal: PlayerAchievement.count()]
    }

    def create() {
        [playerAchievementInstance: new PlayerAchievement(params)]
    }

    def save() {
        def playerAchievementInstance = new PlayerAchievement(params)
        if (!playerAchievementInstance.save(flush: true)) {
            render(view: "create", model: [playerAchievementInstance: playerAchievementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), playerAchievementInstance.id])
        redirect(action: "show", id: playerAchievementInstance.id)
    }

    def show(Long id) {
        def playerAchievementInstance = PlayerAchievement.get(id)
        if (!playerAchievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "list")
            return
        }

        [playerAchievementInstance: playerAchievementInstance]
    }

    def edit(Long id) {
        def playerAchievementInstance = PlayerAchievement.get(id)
        if (!playerAchievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "list")
            return
        }

        [playerAchievementInstance: playerAchievementInstance]
    }

    def update(Long id, Long version) {
        def playerAchievementInstance = PlayerAchievement.get(id)
        if (!playerAchievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (playerAchievementInstance.version > version) {
                playerAchievementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'playerAchievement.label', default: 'PlayerAchievement')] as Object[],
                          "Another user has updated this PlayerAchievement while you were editing")
                render(view: "edit", model: [playerAchievementInstance: playerAchievementInstance])
                return
            }
        }

        playerAchievementInstance.properties = params

        if (!playerAchievementInstance.save(flush: true)) {
            render(view: "edit", model: [playerAchievementInstance: playerAchievementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), playerAchievementInstance.id])
        redirect(action: "show", id: playerAchievementInstance.id)
    }

    def delete(Long id) {
        def playerAchievementInstance = PlayerAchievement.get(id)
        if (!playerAchievementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "list")
            return
        }

        try {
            playerAchievementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'playerAchievement.label', default: 'PlayerAchievement'), id])
            redirect(action: "show", id: id)
        }
    }
}
