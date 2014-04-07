package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.TestById
import Services.ResponseValidation
import Services.ScoreResult
import Services.VerifyAnswer
import comparadordesonido.SoundEngine

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
        def nickname=Player.findByNickname(request.XML.nickname.toString())
        def tests = Test.findAll("from Test as t where t.level="+levelId, [max:countTests])
        def validationResponse=new ResponseValidation()
        println"toy"
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
        def response = new VerifyAnswer()
        
        def validationResponse=new ResponseValidation()
     
       
        //def req = request.getParameter("xml_id") as XML
        def testId=request.XML.testId.toString()
        def sessionActive=request.XML.session.toString()
        println(testId)
        def player=Player.findByNickname(request.XML.nickname.toString())
        def test = Test.get(testId)
        def verification = false
        def score= Score.findByPlayerAndStateAndSession(player,0,Integer.parseInt(sessionActive))
        if (test) {
            validationResponse.key = "1";
            validationResponse.value = "successfull";
            if(!score){
                def sco=new Score()
                sco.player=player
                sco.level=test.level
                sco.date=new Date()
                sco.score=0
                sco.state=0
                sco.testNumber=0
                sco.live=3
                sco.save(flush:true)
                sco.session=Score.count()+1
                response.session=sco.session
                score=sco
            
            }
               if (score.lastTest!=test.id)
            {  score.testNumber=score.testNumber+1
            }else{
                score.score=score.score-score.lastGain
            }
            if (test.question!=null){
                //question
                def asw=Question.get(test.question.id)
                if (asw){
                    score.lastTest=test.id
                    def answer=request.XML.answer.toString()
                    if(asw.answer==answer){
                        verification = true
                        score.score=score.score+10
                        score.lastGain=10
                    }
                    else{
                        verification = false
                        score.live=score.live-1
                    }
                
                    if(score.live==0){
                        score.score=0
                        score.state=1
                        response.score=score.score
                        response.value="Debes intentarlo de nuevo"
                     
                    }
                    
                }
                else
                verification = false
            }else
            if (test.theory!=null){
                score.lastGain = 0
                score.lastTest=test.id
                response.session=score.session
            }
         

            response.live=score.live
            score.save()
        
        }
        else{
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
        }
    
        def xmlLista =  response as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        render xmlSalida
    }
    
    def verifyTestWithFileService(){
        def response = new VerifyAnswer()
        
        def validationResponse=new ResponseValidation()
     
        def req = new XmlSlurper().parseText(request.getParameter("xml_id"))
        //def req = request.getParameter("xml_id") as XML
        def testId=req.testId.text()
        def sessionActive=req.session.text()
        println(testId)
        def player=Player.findByNickname(req.nickname.text())
        def test = Test.get(testId)
        def verification = false
        def score= Score.findByPlayerAndStateAndSession(player,0,Integer.parseInt(sessionActive))
        if (test) {
            validationResponse.key = "1";
            validationResponse.value = "successfull";
            if(!score){
                def sco=new Score()
                sco.player=player
                sco.level=test.level
                sco.date=new Date()
                sco.score=0
                sco.state=0
                sco.testNumber=0
                sco.live=3
                sco.save(flush:true)
                sco.session=Score.count()+1
                response.session=sco.session
                score=sco
            
            }
               if (score.lastTest!=test.id)
            {  score.testNumber=score.testNumber+1
            }else{
                score.score=score.score-score.lastGain
            }
            if (test.practice!=null){
                  score.lastTest=test.id
                def soundClient=request.getFile('mp3')
                def soundClientBytes=soundClient.getBytes()
                 
                def engine = new SoundEngine()
                def correlation = Math.round(engine.decodeMp3(test.practice.audio.sound,soundClientBytes)*100)
                score.score=score.score+correlation
                  score.lastGain=correlation
                if (correlation>89)
                response.value = "Excelente"
                else if (correlation>59)
                response.value = "Buen intento"
                else if (correlation>39)
                response.value = "Tu puedes hacerlo mejor!"
                else if (correlation>0)
                response.value = "Vuelve a intentarlo, tu puedes mejorar"
                verification = true
            }
            //sonido 
            score.save()
        
        }
        else{
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
        }
    
        def xmlLista =  response as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        render xmlSalida
    }
    
    def commitResultTest(){
          def response = new ScoreResult()
          def validationResponse=new ResponseValidation()
        def sessionActive=request.XML.session.text()
        def player=Player.findByNickname(request.XML.nickname.toString())
        def level=Level.get(request.XML.level.toString())
        def score= Score.findByPlayerAndStateAndSessionAndLevel(player,0,Integer.parseInt(sessionActive),level)
        if (score) { 
            validationResponse.key = "1";
            validationResponse.value = "successfull";
        if(score.testNumber==8){
            score.score=score.score+(score.live*10)
            score.state=1
            response.live=score.live
            response.score=score.score
              score.save()
        }
    
    } else{
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
        }
        
           def xmlLista =  response as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        render xmlSalida
        
    }
    
    def testList(){
        def levels=Image.findAll(){
            question == Question.get(202)
        }
        render levels as XML
    }
    
    def testRecognizer(){
        //  println("dsasadasds"+ Audio.get("11").sound.length())
        def engine = new SoundEngine()
        def test1  = Audio.get(12)
        def test2  = Audio.get(13)
        def test3  = Audio.get(14)
        engine.decodeMp3(test1.sound,test2.sound)
        engine.decodeMp3(test2.sound,test1.sound)
        engine.decodeMp3(test3.sound,test1.sound)
     
    }
    
    def getTestServiceDepricated(){
        def testId=request.XML.testId.toString()
        def test = Test.get(testId)
        def xmlSalida = ""
        def xmlImages = ""
        def validationResponse=new ResponseValidation()
        def response = new TestById()
        if (test) {
            validationResponse.key = "1";
            validationResponse.value = "successfull";
            println("test type : "+test.testType.id)
            if (test.testType.id==1){//Pregunta-Imagen
                response.question=test.question.question
                response.image=test.question.image.image
                response.answer=test.question.answer
            }
            if (test.testType.id==2){//Pregunta-Respuesta-Imagen
                
                def images=Image.findAll(){
                    question == Question.get(test.question.id)
                }
                    
                response.question=test.question.question
                response.answer=test.question.answer
                xmlImages = images as XML
    
                xmlImages=xmlImages.toString().substring(xmlImages.toString().indexOf(">")+1)

            }
            if (test.testType.id==4){
                response.theory=test.theory.description
                response.title=test.theory.name
                println("Tipo 4")
            }
            if (test.testType.id==6){
                response.image=test.theory.image.image
                response.theory=test.theory.description
                response.title=test.theory.name
            }
            if (test.testType.id==7){
                response.image=test.theory.image.image
                response.theory=test.theory.description
                response.title=test.theory.name
                response.sound=test.practice.audio.sound
                response.instruction=test.practice.name
            }
            if (test.testType.id==5){
                response.image=test.practice.image.image
                response.instruction=test.practice.name
            }
            if (test.testType.id==3){
                response.question=test.question.question
                response.answer=test.question.answer
            }
            def xmlLista =  response as XML 
            def xmlRespuesta = validationResponse as XML 
       
            xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+xmlImages+"</response>"

        }
        else{
        
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
            def xmlRespuesta = validationResponse as XML 
            xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+"</response>"
  
        }
        render xmlSalida
            
    }
    
     
    def getTestService(){
        def testId=request.XML.testId.toString()
        def test = Test.get(testId)
        def xmlSalida = ""
        def xmlImages = ""
        def validationResponse=new ResponseValidation()
        def response = new TestById()
        if (test) {
            validationResponse.key = "1";
            validationResponse.value = "successfull";
            println("test type : "+test.testType.id)
            if (test.testType.id==1){//Teoría
                response.theory=test.theory.description
                response.title=test.theory.name
            }
            if (test.testType.id==2){//Teoría-Imagen
                response.image=test.theory.image.image
                response.theory=test.theory.description
                response.title=test.theory.name
            }
             
            if (test.testType.id==3 || test.testType.id==4 || test.testType.id==5){//Teoría-Imagen-Audio-Practica
                response.image=test.theory.image.image
                response.theory=test.theory.description
                response.title=test.theory.name
                response.sound=test.theory.audio.sound
               if (test.testType.id==3)
                response.instruction=test.note.name
               if (test.testType.id==4)
                response.instruction=test.chord.name
               if (test.testType.id==5){
                response.instruction=test.rhythmic.name
                response.times=test.rhythmic.time
                }
                
            }
            if (test.testType.id==6){//pregunta
                response.question=test.question.question
                response.answer=test.question.answer
            }
            if (test.testType.id==7){//Pregunta-Imagen
                response.question=test.question.question
                response.image=test.question.image.image
                response.answer=test.question.answer
            }
            if (test.testType.id==8){//Pregunta-Respuesta con Imagen
                
                def images=Image.findAll(){
                    question == Question.get(test.question.id)
                }
                    
                response.question=test.question.question
                response.answer=test.question.answer
                xmlImages = images as XML
    
                xmlImages=xmlImages.toString().substring(xmlImages.toString().indexOf(">")+1)

            }
              if (test.testType.id==9 || test.testType.id==10 || test.testType.id==11){//practica
                response.image=test.theory.image.image
                 if (test.testType.id==9)
                response.instruction=test.note.name
               if (test.testType.id==10)
                response.instruction=test.chord.name
               if (test.testType.id==11){
                response.instruction=test.rhythmic.name
                response.times=test.rhythmic.time
                }
            }
            
            def xmlLista =  response as XML 
            def xmlRespuesta = validationResponse as XML 
       
            xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+xmlImages+"</response>"

        }
        else{
        
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
            def xmlRespuesta = validationResponse as XML 
            xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+"</response>"
  
        }
        render xmlSalida
            
    }
}
