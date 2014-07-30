package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import Services.TestById
import Services.Report
import Services.Report
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
    
    /*  
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
    }*/
    def listTestsService(){
        def levelId=request.XML.levelId.toString()
        def countTests=request.XML.count.toString()
        def nickname=Player.findByNickname(request.XML.nickname.toString())
        def tests = Test.findAll("from Test as t where t.level="+levelId+" order by id", [max:countTests])
        def validationResponse=new ResponseValidation()
        validationResponse.key = "1";
        validationResponse.value = "Tests";
          def sco=new Score()
                sco.player=nickname
                sco.level=Level.get(levelId)
                sco.date=new Date()
                sco.score=0
                sco.state=0
                sco.testNumber=0
                sco.live=3
                   sco.session=Score.count()+1
                 sco.save(flush:true)
        def xmlLista =  tests as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"<sessionToLevel>"+sco.session+"</sessionToLevel></response>"
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
        def score= Score.findByPlayerAndStateAndSessionAndLevel(player,0,Integer.parseInt(sessionActive),test.level)
      
        if (test && score) {
            score.lock()
            validationResponse.key = "1";
            validationResponse.value = "successfull";
        //   if (score.lastTest!=test.id)
            //  score.testNumber=score.testNumber+1
            
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
                        score.lastGain=0
                    }
                    score.testNumber=score.testNumber+1
                    if(score.testNumber==10 && score.live>=0){
                        // score.state=1
                        response.score=score.score
                    }else
                    if(score.live==-1){
                        score.score=0
                        score.state=2
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
                score.testNumber=score.testNumber+1
            }
         

            response.live=score.live
           println("Test SQVE!")
            score.save(flush:true)
        
        }
        else{
            validationResponse.key = "0";
            validationResponse.value = "Error, the test with id "+testId+" doesn't exist";
         println("Test NO SAVE!")
         
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
        def score= Score.findByPlayerAndStateAndSessionAndLevel(player,0,Integer.parseInt(sessionActive),test.level)
 if (test && score) {
                score.lock()
            validationResponse.key = "1";
            validationResponse.value = "successfull";
     
            if (score.lastTest!=test.id)
            {  score.testNumber=score.testNumber+1
            }else{
                score.score=score.score-score.lastGain
            }
           
            score.lastTest=test.id
            def soundClient=request.getFile('mp3')
            def soundClientBytes=soundClient.getBytes()
                 
            def engine = new SoundEngine()
            def correlation=-1;
            if (test.testType.id==3 || test.testType.id==9)//NOTAS note(byte[] muestra, String begin, String end,String frequency)
            correlation = Math.round(engine.note(soundClientBytes,test.note.range,test.note.frequency))
            else
            if (test.testType.id==4 || test.testType.id==10)//CHORD
            correlation = Math.round(engine.analizeChord(soundClientBytes,test.chord.range,test.chord.frequency))
            else
            if (test.testType.id==5 || test.testType.id==11)//Rhythmicaudio,rangos,arrayTimes,frecuencias
            correlation = Math.round(engine.rhythmic(soundClientBytes,test.rhythmic.range,test.rhythmic.time,test.rhythmic.frequency))
      
            if (correlation==10)
            response.value = "Excelente"
            else if (correlation>5)
            response.value = "Buen intento"
            else if (correlation>=0)
            response.value = "Tu puedes hacerlo mejor!"
            else if (correlation<0)
           { correlation=0;
            response.value = "Vuelve a intentarlo, tu puedes mejorar"
           }
            response.score = correlation
            score.score=score.score+correlation
            score.lastGain=correlation
            verification = true
            
            def pracSco=PracticeScore.findByScoreAndNoteAndChordAndRhythmic(score,test.note,test.chord,test.rhythmic)
            if (pracSco){
                if (pracSco.points<correlation){
                    pracSco.points=correlation
                    pracSco.save(flush:true)
                }
            }
            else{
                def sco=new PracticeScore()
                sco.score=score
                sco.points=correlation


                if (test.testType.id==3 || test.testType.id==9)//NOTES
                sco.note=test.note
                else
                if (test.testType.id==4 || test.testType.id==10)//CHORD
                sco.chord=test.chord
                else
                if (test.testType.id==5 || test.testType.id==11)//Rhythmic
                sco.rhythmic=test.rhythmic
                 sco.save(flush:true)
            }
            println("Test  SAVE!")
            //sonido 
                score.save(flush:true)
        }
        else{
             println("Test NO SAVE!")
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
            if(score.testNumber==10){
                score.score=score.score+score.live
                score.state=1
                response.live=score.live
                response.score=score.score
                score.save(flush:true)
            }
    
        } else{
            validationResponse.key = "0";
            validationResponse.value = "Error, score doesn't exist";
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
            if (test.testType.id==9 || test.testType.id==10){//practica
                response.image=test.theory.image.image
                if (test.testType.id==9)
                response.instruction=test.note.name
                if (test.testType.id==10)
                response.instruction=test.chord.name
              
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
    
    
    
    def reportService(){
        def player=request.XML.nickname.toString()
        
        def response=new Report()
        def row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Player as p, Score as s, Level as l, Test as t, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND n.id = t.note group by n.name order by count(n.name) desc ")
        if (row[0])    
            response.notaMasTocadaUsuario = row[0]
        else
            response.notaMasTocadaUsuario = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Player as p, Score as s, Level as l, Test as t, Chord as a WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND a.id = t.chord group by a.name order by count(a.name) desc")
        if (row[0])    
            response.acordeMasTocadoUsuario = row[0]
        else
            response.acordeMasTocadoUsuario = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Player as p, Score as s, Level as l, Test as t, Rhythmic as r WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND r.id = t.rhythmic group by r.name order by count(r.name) desc ")
        if (row[0])    
            response.ritmicaMasTocadaUsuario = row[0]
        else
            response.ritmicaMasTocadaUsuario = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Score as s, Level as l, Test as t, Note as n WHERE l.id = s.level AND l.id = t.level AND n.id = t.note group by n.name order by count(n.name) desc ")
        if (row[0])    
            response.notaMasTocadaGlobal = row[0]
        else
            response.notaMasTocadaGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Score as s, Level as l, Test as t, Chord as a WHERE l.id = s.level AND l.id = t.level AND a.id = t.chord group by a.name order by count(a.name) desc ")
        if (row[0])    
            response.acordeMasTocadaGlobal = row[0]
        else
            response.acordeMasTocadaGlobal = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Score as s, Level as l, Test as t, Rhythmic as r WHERE l.id = s.level AND l.id = t.level AND r.id = t.rhythmic group by r.name order by count(r.name) desc ")
        if (row[0])    
            response.ritmicaMasTocadaGlobal = row[0]
        else
            response.ritmicaMasTocadaGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Player as p, Score as s, Level as l, Test as t, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND n.id = t.note group by n.name order by count(n.name) ")
        if (row[0])    
            response.notaMenosTocadaUsuario = row[0]
        else
            response.notaMenosTocadaUsuario = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Player as p, Score as s, Level as l, Test as t, Chord as a WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND a.id = t.chord group by a.name order by count(a.name) ")
        if (row[0])    
            response.acordeMenosTocadaUsuario = row[0]
        else
            response.acordeMenosTocadaUsuario = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Player as p, Score as s, Level as l, Test as t, Rhythmic as r WHERE '"+player+"' = p.nickname AND p.id = s.player AND l.id = s.level AND l.id = t.level AND r.id = t.rhythmic group by r.name order by count(r.name) ")
        if (row[0])    
            response.ritmicaMenosTocadaUsuario = row[0]
        else
            response.ritmicaMenosTocadaUsuario = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Score as s, Level as l, Test as t, Note as n WHERE l.id = s.level AND l.id = t.level AND n.id = t.note group by n.name order by count(n.name) ")
        if (row[0])    
            response.notaMenosTocadaGlobal = row[0]
        else
            response.notaMenosTocadaGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Score as s, Level as l, Test as t, Chord as a WHERE l.id = s.level AND l.id = t.level AND a.id = t.chord group by a.name order by count(a.name) ")
        if (row[0])    
            response.acordeMenosTocadaGlobal = row[0]
        else
            response.acordeMenosTocadaGlobal = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Score as s, Level as l, Test as t, Rhythmic as r WHERE l.id = s.level AND l.id = t.level AND r.id = t.rhythmic group by r.name order by count(r.name)  ")
        if (row[0])    
            response.ritmicaMenosTocadaGlobal = row[0]
        else
            response.ritmicaMenosTocadaGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id group by n.name order by sum(ps.points)/count(n.name) desc ")
        if (row[0])    
            response.notaMasFacil = row[0]
        else
            response.notaMasFacil = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Player as p, Score as s,PracticeScore as ps, Chord as a WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.chord=a.id group by a.name order by sum(ps.points)/count(a.name) desc ")
        if (row[0])    
            response.acordeMasFacil = row[0]
        else
            response.acordeMasFacil = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Player as p, Score as s,PracticeScore as ps, Rhythmic as r WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.rhythmic=r.id group by r.name order by sum(ps.points)/count(r.name) desc ")
        if (row[0])    
            response.ritmicaMasFacil = row[0]
        else
            response.ritmicaMasFacil = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id group by n.name order by sum(ps.points)/count(n.name) ")
        if (row[0])    
            response.notaMasDificil = row[0]
        else
            response.notaMasDificil = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Player as p, Score as s,PracticeScore as ps, Chord as a WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.chord=a.id group by a.name order by sum(ps.points)/count(a.name) ")
        if (row[0])    
            response.acordeMasDificil = row[0]
        else
            response.acordeMasDificil = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Player as p, Score as s,PracticeScore as ps, Rhythmic as r WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.rhythmic=r.id group by r.name order by sum(ps.points)/count(r.name) ")
        if (row[0])    
            response.ritmicaMasDificil = row[0]
        else
            response.ritmicaMasDificil = "Ninguna"
            
        
        
        
        
        
        
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Score as s,PracticeScore as ps, Note as n WHERE s.id = ps.score AND ps.note=n.id group by n.name order by sum(ps.points)/count(n.name) desc ")
        if (row[0])    
            response.notaMasFacilGlobal = row[0]
        else
            response.notaMasFacilGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM  Score as s,PracticeScore as ps, Chord as a WHERE s.id = ps.score AND ps.chord=a.id group by a.name order by sum(ps.points)/count(a.name) desc ")
        if (row[0])    
            response.acordeMasFacilGlobal = row[0]
        else
            response.acordeMasFacilGlobal = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Score as s,PracticeScore as ps, Rhythmic as r WHERE s.id = ps.score AND ps.rhythmic=r.id group by r.name order by sum(ps.points)/count(r.name) desc ")
        if (row[0])    
            response.ritmicaMasFacilGlobal = row[0]
        else
            response.ritmicaMasFacilGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) FROM Score as s,PracticeScore as ps, Note as n WHERE s.id = ps.score AND ps.note=n.id group by n.name order by sum(ps.points)/count(n.name) ")
        if (row[0])    
            response.notaMasDificilGlobal = row[0]
        else
            response.notaMasDificilGlobal = "Ninguna"
        row = Test.executeQuery("SELECT (CASE WHEN SUBSTRING(a.name,18,1)LIKE' ' THEN SUBSTRING(a.name,17,8) ELSE SUBSTRING(a.name,17,9) END) FROM Score as s,PracticeScore as ps, Chord as a WHERE s.id = ps.score AND ps.chord=a.id group by a.name order by sum(ps.points)/count(a.name) ")
        if (row[0])    
            response.acordeMasDificilGlobal = row[0]
        else
            response.acordeMasDificilGlobal = "Ninguno"
        row = Test.executeQuery("SELECT SUBSTRING(r.name,33,char_length(r.name)-33) FROM Score as s,PracticeScore as ps, Rhythmic as r WHERE  s.id = ps.score AND ps.rhythmic=r.id group by r.name order by sum(ps.points)/count(r.name) ")
        if (row[0])    
            response.ritmicaMasDificilGlobal = row[0]
        else
            response.ritmicaMasDificilGlobal = "Ninguna"
            
        
        
        
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'DO'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionDO = row[0]
        else
            response.mayorPuntuacionDO = "0"
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'DO#'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionDOS = row[0]
        else
            response.mayorPuntuacionDOS = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'RE'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionRE= row[0]
        else
            response.mayorPuntuacionRE = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'RE#'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionRES = row[0]
        else
            response.mayorPuntuacionRES = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'MI'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionMI = row[0]
        else
            response.mayorPuntuacionMI = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'FA'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionFA = row[0]
        else
            response.mayorPuntuacionFA = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'FA#'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionFAS = row[0]
        else
            response.mayorPuntuacionFAS = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'SOL'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionSOL = row[0]
        else
            response.mayorPuntuacionSOL = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'SOL#'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionSOLS = row[0]
        else
            response.mayorPuntuacionSOLS = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'LA'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionLA = row[0]
        else
            response.mayorPuntuacionLA = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'LA#'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionLAS = row[0]
        else
            response.mayorPuntuacionLAS = 0
        row = Test.executeQuery("SELECT coalesce(max(ps.points),'0') FROM Player as p, Score as s,PracticeScore as ps, Note as n WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score AND ps.note=n.id AND 'SI'=(CASE WHEN SUBSTRING(n.name,18,1)LIKE' ' THEN SUBSTRING(n.name,16,2) ELSE SUBSTRING(n.name,16,3) END) group by n.name order by max(ps.points) desc ")
        if (row[0])    
            response.mayorPuntuacionSI = row[0]
        else
            response.mayorPuntuacionSI = 0
        row = Test.executeQuery("SELECT ROUND(avg(ps.points),2) FROM Player as p, Score as s,PracticeScore as ps WHERE '"+player+"' = p.nickname AND p.id = s.player AND s.id = ps.score") 
        if (row[0])    
            response.promedioPractica = row[0]
        else
            response.promedioPractica = "0"
        row = Test.executeQuery("SELECT ROUND(avg(ps.points),2) FROM Score as s,PracticeScore as ps WHERE s.id = ps.score") 
        if (row[0])    
            response.promedioPracticaGlobal = row[0]
        else
            response.promedioPracticaGlobal = "0"
        def promedioTeoria=0
        row = Test.executeQuery("SELECT s.score - s.live, sum(ps.points) from PracticeScore as ps right join ps.score as s WHERE '"+player+"'=s.player.nickname and s.state=1 GROUP BY s.score,s.live")
        for (def queryRow : row){
            def score=queryRow[0]
            def practiceScore=queryRow[1]
            if (promedioTeoria==0)
                if (practiceScore)
                    promedioTeoria=score-practiceScore
                else
                    promedioTeoria=score
            else
                if (practiceScore)
                    promedioTeoria=(promedioTeoria+(score-practiceScore)) / 2
                else
                    promedioTeoria=(promedioTeoria+(score)) / 2
        }
        print promedioTeoria
        try{
        response.promedioTeoria = String.format("%.4g",promedioTeoria)
        }
        catch(Exception e){
            response.promedioTeoria=promedioTeoria
            
        }
        
        
        def promedioTeoriaGlobal=0
        row = Test.executeQuery("SELECT s.score - s.live, sum(ps.points) from PracticeScore as ps right join ps.score as s WHERE s.state=1 GROUP BY s.score,s.live")
        for (def queryRow : row){
            def score=queryRow[0]
            def practiceScore=queryRow[1]
            if (promedioTeoriaGlobal==0)
                if (practiceScore)
                    promedioTeoriaGlobal=score-practiceScore
                else
                    promedioTeoriaGlobal=score
            else
                if (practiceScore)
                    promedioTeoriaGlobal=(promedioTeoriaGlobal+(score-practiceScore)) / 2
                else
                    promedioTeoriaGlobal=(promedioTeoriaGlobal+(score)) / 2
        }
        print promedioTeoriaGlobal
        try{
        response.promedioTeoriaGlobal = String.format("%.4g",promedioTeoriaGlobal)
        }
        catch(Exception e){
            response.promedioTeoriaGlobal=promedioTeoriaGlobal
            
        }
        print response as XML
        render response as XML
        
    }
}
