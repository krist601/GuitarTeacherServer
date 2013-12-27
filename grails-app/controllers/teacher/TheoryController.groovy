package teacher

import org.springframework.dao.DataIntegrityViolationException
import Services.ResponseValidation
import grails.converters.XML

class TheoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [theoryInstanceList: Theory.list(params), theoryInstanceTotal: Theory.count()]
    }

    def create() {
        [theoryInstance: new Theory(params)]
    }

    def save() {
        def theoryInstance = new Theory(params)
        if (!theoryInstance.save(flush: true)) {
            render(view: "create", model: [theoryInstance: theoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'theory.label', default: 'Theory'), theoryInstance.id])
        redirect(action: "show", id: theoryInstance.id)
    }

    def show(Long id) {
        def theoryInstance = Theory.get(id)
        if (!theoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "list")
            return
        }

        [theoryInstance: theoryInstance]
    }

    def edit(Long id) {
        def theoryInstance = Theory.get(id)
        if (!theoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "list")
            return
        }

        [theoryInstance: theoryInstance]
    }

    def update(Long id, Long version) {
        def theoryInstance = Theory.get(id)
        if (!theoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (theoryInstance.version > version) {
                theoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'theory.label', default: 'Theory')] as Object[],
                          "Another user has updated this Theory while you were editing")
                render(view: "edit", model: [theoryInstance: theoryInstance])
                return
            }
        }

        theoryInstance.properties = params

        if (!theoryInstance.save(flush: true)) {
            render(view: "edit", model: [theoryInstance: theoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'theory.label', default: 'Theory'), theoryInstance.id])
        redirect(action: "show", id: theoryInstance.id)
    }

    def delete(Long id) {
        def theoryInstance = Theory.get(id)
        if (!theoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "list")
            return
        }

        try {
            theoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'theory.label', default: 'Theory'), id])
            redirect(action: "show", id: id)
        }
    }
    
    def theoryById(){
        def theoryId=request.XML.theoryId.toString()
        def theory = Theory.get(theoryId)
        def respuesta = new ResponseValidation()
        respuesta.key = "1"
        respuesta.value=theory.description
        render respuesta as XML
    }
}
