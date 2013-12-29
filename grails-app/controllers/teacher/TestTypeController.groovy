package teacher

import org.springframework.dao.DataIntegrityViolationException

class TestTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [testTypeInstanceList: TestType.list(params), testTypeInstanceTotal: TestType.count()]
    }

    def create() {
        [testTypeInstance: new TestType(params)]
    }

    def save() {
        def testTypeInstance = new TestType(params)
        if (!testTypeInstance.save(flush: true)) {
            render(view: "create", model: [testTypeInstance: testTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'testType.label', default: 'TestType'), testTypeInstance.id])
        redirect(action: "show", id: testTypeInstance.id)
    }

    def show(Long id) {
        def testTypeInstance = TestType.get(id)
        if (!testTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "list")
            return
        }

        [testTypeInstance: testTypeInstance]
    }

    def edit(Long id) {
        def testTypeInstance = TestType.get(id)
        if (!testTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "list")
            return
        }

        [testTypeInstance: testTypeInstance]
    }

    def update(Long id, Long version) {
        def testTypeInstance = TestType.get(id)
        if (!testTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (testTypeInstance.version > version) {
                testTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'testType.label', default: 'TestType')] as Object[],
                          "Another user has updated this TestType while you were editing")
                render(view: "edit", model: [testTypeInstance: testTypeInstance])
                return
            }
        }

        testTypeInstance.properties = params

        if (!testTypeInstance.save(flush: true)) {
            render(view: "edit", model: [testTypeInstance: testTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'testType.label', default: 'TestType'), testTypeInstance.id])
        redirect(action: "show", id: testTypeInstance.id)
    }

    def delete(Long id) {
        def testTypeInstance = TestType.get(id)
        if (!testTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "list")
            return
        }

        try {
            testTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'testType.label', default: 'TestType'), id])
            redirect(action: "show", id: id)
        }
    }
}
