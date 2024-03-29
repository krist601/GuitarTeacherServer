package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.ResponseValidation

class ImageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [imageInstanceList: Image.list(params), imageInstanceTotal: Image.count()]
    }

    def create() {
        [imageInstance: new Image(params)]
    }

    def save() {
        def imageInstance = new Image(params)
        if (!imageInstance.save(flush: true)) {
            render(view: "create", model: [imageInstance: imageInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def show(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def edit(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def update(Long id, Long version) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (imageInstance.version > version) {
                imageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'image.label', default: 'Image')] as Object[],
                          "Another user has updated this Image while you were editing")
                render(view: "edit", model: [imageInstance: imageInstance])
                return
            }
        }

        imageInstance.properties = params

        if (!imageInstance.save(flush: true)) {
            render(view: "edit", model: [imageInstance: imageInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def delete(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        try {
            imageInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "show", id: id)
        }
    }
    def renderPhotobyId() {    
        def respuesta = new ResponseValidation()
       
        def id = request.XML?.idImage.toString();
        def image = Image.get(id).image
        if (image==null){
            respuesta.key="0"
            respuesta.value="User not exist"
            render respuesta as XML
        }else{
            byte[] imageProfile = image 
            response.outputStream << imageProfile  
        }}
        
    def uploadImage() {
        
        def image = new Image()
        def respuesta = new ResponseValidation()
        def f = request.getFile('image')
        if (f==null){
                 respuesta.key="0"
            respuesta.value="Error sending file"

  }else
        if (f.empty) {
            respuesta.key="0"
            respuesta.value="Empty file"
        }
      else{    
        image.image=f.getBytes()
        image.save()   

            respuesta.key="1"
            respuesta.value="image updated"
            
        }
        render respuesta as XML
        
    }
    def showImage = {
        def imagen = Image.get( params.id )
        response.outputStream << imagen.screenshot
        response.outputStream.flush()
    }
} 
    

