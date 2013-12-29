package teacher
import Services.ResponseValidation
import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML

class AudioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [audioInstanceList: Audio.list(params), audioInstanceTotal: Audio.count()]
    }

    def create() {
        [audioInstance: new Audio(params)]
    }

    def save() {
        def audioInstance = new Audio(params)
        if (!audioInstance.save(flush: true)) {
            render(view: "create", model: [audioInstance: audioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'audio.label', default: 'Audio'), audioInstance.id])
        redirect(action: "show", id: audioInstance.id)
    }

    def show(Long id) {
        def audioInstance = Audio.get(id)
        if (!audioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "list")
            return
        }

        [audioInstance: audioInstance]
    }

    def edit(Long id) {
        def audioInstance = Audio.get(id)
        if (!audioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "list")
            return
        }

        [audioInstance: audioInstance]
    }

    def update(Long id, Long version) {
        def audioInstance = Audio.get(id)
        if (!audioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (audioInstance.version > version) {
                audioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'audio.label', default: 'Audio')] as Object[],
                          "Another user has updated this Audio while you were editing")
                render(view: "edit", model: [audioInstance: audioInstance])
                return
            }
        }

        audioInstance.properties = params

        if (!audioInstance.save(flush: true)) {
            render(view: "edit", model: [audioInstance: audioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'audio.label', default: 'Audio'), audioInstance.id])
        redirect(action: "show", id: audioInstance.id)
    }

    def delete(Long id) {
        def audioInstance = Audio.get(id)
        if (!audioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "list")
            return
        }

        try {
            audioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'audio.label', default: 'Audio'), id])
            redirect(action: "show", id: id)
        }
    }
    
     def uploadAudio() { 
            
        def audio = new Audio()
        def respuesta = new ResponseValidation()
        def f = request.getFile('sound')
        if (f==null){
                 respuesta.key="0"
            respuesta.value="Error sending file"

  }else
        if (f.empty) {
            respuesta.key="0"
            respuesta.value="Empty file"
        }
      else{    
        audio.sound=f.getBytes()
        audio.save()   

            respuesta.key="1"
            respuesta.value="sound updated"
            
        }
        
        render respuesta as XML
        
    }
}
