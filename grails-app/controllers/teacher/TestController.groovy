package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.TestById
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
      
    def testByIdTest(){
        def testId=request.XML.testId.toString()
        def test = Test.get(testId)
        def response = new TestById()
        if (test) {
            response.key="0"
            response.value="successfull"
            if (test.theory!=null){
                response.theory=test.theory.description
            }
            if (test.question!=null){
                response.question=test.question.question
                response.answer=test.question.answer
            }
            if (test.image!=null){
                response.image=test.image.image
            }
            if (test.practice!=null){
                if (test.practice.audio!=null){
                    response.sound=test.practice.audio.sound
                }
            }
        }
        else{
            response.key="1" 
            response.value="Error, the test with id "+testId+" doesn't exist"
        }
//        
//        //Esto deberia ser un switch   
//        if (test)
//        {
//            //Teoria
//            if (test.testType.id==1)        
//            {
//                if (test.theory){
//                    respuesta.key="1" 
//                    respuesta.value=test.theory.description
//                    render respuesta as XML
//                }
//                else
//                {
//                    respuesta.key="0" 
//                    respuesta.value="Error at the test"
//                    render respuesta as XML
//                }
//            }
//        }else{
//            respuesta.key="0" 
//            respuesta.value="Test not found"
//            render respuesta as XML
//                    
//            
//        }
        render response as XML
            
    }
}
