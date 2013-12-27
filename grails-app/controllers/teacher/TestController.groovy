package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.ResponseValidation

class TestController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [testInstanceList: Test.list(params), testInstanceTotal: Test.count()]
    }

    def create() {
        [testInstance: new Test(params)]
    }

    def save() {
        def testInstance = new Test(params)
        if (!testInstance.save(flush: true)) {
            render(view: "create", model: [testInstance: testInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'test.label', default: 'Test'), testInstance.id])
        redirect(action: "show", id: testInstance.id)
    }

    def show(Long id) {
        def testInstance = Test.get(id)
        if (!testInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "list")
            return
        }

        [testInstance: testInstance]
    }

    def edit(Long id) {
        def testInstance = Test.get(id)
        if (!testInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "list")
            return
        }

        [testInstance: testInstance]
    }

    def update(Long id, Long version) {
        def testInstance = Test.get(id)
        if (!testInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (testInstance.version > version) {
                testInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'test.label', default: 'Test')] as Object[],
                          "Another user has updated this Test while you were editing")
                render(view: "edit", model: [testInstance: testInstance])
                return
            }
        }

        testInstance.properties = params

        if (!testInstance.save(flush: true)) {
            render(view: "edit", model: [testInstance: testInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'test.label', default: 'Test'), testInstance.id])
        redirect(action: "show", id: testInstance.id)
    }

    def delete(Long id) {
        def testInstance = Test.get(id)
        if (!testInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "list")
            return
        }

        try {
            testInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'test.label', default: 'Test'), id])
            redirect(action: "show", id: id)
        }
    }
    
        def listTestsByLevelIdService(){
           def levelId=request.XML.levelId.toString()
           def countTests=request.XML.count.toString()
           def tests = Test.findAll("from Test as t where t.level="+levelId, [max:countTests])
         def validationResponse=new ResponseValidation()   
        Collections.shuffle(tests)
               validationResponse.key = "1";
        validationResponse.value = "Tests";
        def xmlLista =  tests as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        println(xmlSalida.toString())
        render xmlSalida
      
        
               
       }
      
            
           }
           
       
      
}
