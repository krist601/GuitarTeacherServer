package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.TestById
import Services.ResponseValidation
import Services.VerifyAnswer

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
    
    
    def listTestsService(){
        def levelId=request.XML.levelId.toString()
        def countTests=request.XML.count.toString()
        def nickname=Player.finByNickname(request.XML.nickname.toString())
        def tests = Test.findAll("from Test as t where t.level="+levelId, [max:countTests])
        def validationResponse=new ResponseValidation()
        def score= Score.findByPlayerAndState(nickname,0)
        if(score){
            score.delete(flush:true)
            score.save()
        }
        Collections.shuffle(tests)
        validationResponse.key = "1";
        validationResponse.value = "Tests";
        def xmlLista =  tests as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        println(xmlSalida.toString())
        render xmlSalida     
    }
      
    def verifyTestService(){
        def testId=request.XML.testId.toString()
        def nickname=Player.finByNickname(request.XML.nickname.toString())
        def test = Test.get(testId)
        def response = new VerifyAnswer()
        def verification = false
        def score= Score.findByPlayerAndState(nickname,0)
        if(!score){
            def score2=new score()
            score2.player=nickname
            score2.score=0
            score2.state=0
            score2.testNumber=0
            score2.live=3
            score2.save(flush:true)
        }
        if (test) {
            response.key="1"
            response.value="successfull"
            /*
             *Validate answer required
             **/
            if (test.question!=null){
               verification = true
            }
            else{
               verification = true
                
            }
        }
        else{
            response.key="0" 
            response.value="Error, the test with id "+testId+" doesn't exist"
        }
        response.verification = verification
         render response as XML
       
        
    }
    
    def getTestService(){
        def testId=request.XML.testId.toString()
        def test = Test.get(testId)
        def response = new TestById()
        if (test) {
            response.key="1"
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
            response.key="0" 
            response.value="Error, the test with id "+testId+" doesn't exist"
        }
        render response as XML
            
    }
}
