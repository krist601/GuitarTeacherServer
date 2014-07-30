package teacher

import org.springframework.dao.DataIntegrityViolationException
import Services.ResponseValidation
import grails.converters.XML
import Services.Difficulty
import Services.LevelByUser

class LevelController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [levelInstanceList: Level.list(params), levelInstanceTotal: Level.count()]
    }

    def create() {
        [levelInstance: new Level(params)]
    }

    def save() {
        def levelInstance = new Level(params)
        if (!levelInstance.save(flush: true)) {
            render(view: "create", model: [levelInstance: levelInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'level.label', default: 'Level'), levelInstance.id])
        redirect(action: "show", id: levelInstance.id)
    }

    def show(Long id) {
        def levelInstance = Level.get(id)
        if (!levelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "list")
            return
        }

        [levelInstance: levelInstance]
    }

    def edit(Long id) {
        def levelInstance = Level.get(id)
        if (!levelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "list")
            return
        }

        [levelInstance: levelInstance]
    }

    def update(Long id, Long version) {
        def levelInstance = Level.get(id)
        if (!levelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (levelInstance.version > version) {
                levelInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'level.label', default: 'Level')] as Object[],
                          "Another user has updated this Level while you were editing")
                render(view: "edit", model: [levelInstance: levelInstance])
                return
            }
        }

        levelInstance.properties = params

        if (!levelInstance.save(flush: true)) {
            render(view: "edit", model: [levelInstance: levelInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'level.label', default: 'Level'), levelInstance.id])
        redirect(action: "show", id: levelInstance.id)
    }

    def delete(Long id) {
        def levelInstance = Level.get(id)
        if (!levelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "list")
            return
        }

        try {
            levelInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'level.label', default: 'Level'), id])
            redirect(action: "show", id: id)
        }
    }
    
     def listLevelsService(){
        def validationResponse=new ResponseValidation()
        def levels=Level.findAll(sort:"difficulty")
        validationResponse.key = "1";
        validationResponse.value = "Levels";
        def xmlLista =  levels as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
      //  println(xmlSalida.toString())
      render(text:xmlSalida,contentType:"text/xml",encoding:"UTF-8")
        //def bookOutput = new XmlSlurper().parseText(xmlSalida) 
        // render bookOutput as XML)
       
        // render fri as XML
    }
 def listLevelsGroupByDifficultyByUserService(){
        def validationResponse=new ResponseValidation()
        def levels=Level.findAll(sort:"difficulty")
 
        def player=Player.findByNickname(request.XML.nickname.toString())
        println request.XML.nickname.toString()
        validationResponse.key = "1";
        validationResponse.value = "Levels";
        def difficult = -1
        def isFirth = true
        def difficultyNormalQuery = Level.executeQuery("select COALESCE(max(l.difficulty),1) from Score s,Level l where s.level=l and s.player="+player.id )
        def difficultyNormal=difficultyNormalQuery.getAt(0)
        def difficulties = "<Difficulties>"
        def someEnable=false
        for (level in levels){
            if (level.difficulty!=difficult){
                if (!isFirth)
                difficulties += "</Difficulty>"      
                difficulties += "<Difficulty>"      
                difficult=level.difficulty
                isFirth = false
                
            }
            def levelByUser = new LevelByUser()
            levelByUser.level = level.id
           levelByUser.name=level.name+":"+level.description
            if (difficultyNormal>difficult){
                levelByUser.imageId=level.success.id
                levelByUser.state = "Success"
       
            }else
            if ((difficultyNormal+1)<difficult){
                levelByUser.imageId=level.disabled.id
                levelByUser.state = "Disabled"
            }else{
                def score = Score.findAllByLevelAndPlayerAndState(level,player,1);
                if (difficultyNormal==difficult){
           
                    println("state "+score.state)
                    println("level "+levelByUser.level)
                    if (score.state.getAt(0)==null){
                        levelByUser.imageId=level.normal.id
                        levelByUser.state = "Enable"
                        someEnable = true
                    }else
                  {
                        levelByUser.imageId=level.success.id
                        levelByUser.state = "Success"
                    }
                    
                }else // Igual a dificultad mas 1
                { if (someEnable==true)
                    {
                        levelByUser.imageId=level.disabled.id
                        levelByUser.state = "Disabled"
      
                        
                    }
                    else{
                     
                            levelByUser.imageId=level.normal.id
                            levelByUser.state = "Enable"
                       
                        
                    }
                    
                }
                
                     
            }
            def levelString = levelByUser as XML
      
            difficulties += (levelString).toString().substring(levelString.toString().indexOf(">")+1)
        }
        
        difficulties += "</Difficulty></Difficulties>"
        def xmlLista =   difficulties 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista+"</response>"
        println(xmlSalida.toString())
        render(text:xmlSalida,contentType:"text/xml",encoding:"UTF-8")
        //def bookOutput = new XmlSlurper().parseText(xmlSalida) 
        // render bookOutput as XML)
       
        // render fri as XML
    }
    
    def listLevelsGroupByDifficultyService(){
        def validationResponse=new ResponseValidation()
        def levels=Level.findAll(sort:"difficulty")
        validationResponse.key = "1";
        validationResponse.value = "Levels";
        def difficult = -1
        def difficulty = null
         def difficulties = new ArrayList<Difficulty>()
        for (level in levels){
            if (level.difficulty!=difficult){
                if (difficulty!=null)
                  difficulties << difficulty
               
                difficulty = new Difficulty()
                difficult = level.difficulty 
            difficulty.levels = new ArrayList<Level>()
            difficulty.difficulty = level.difficulty
              
            }  //Verificar si el usuario paso o no -> Comentar como tiene que ser state en level
            difficulty.levels<<level
            
        }
           difficulties << difficulty
               
        def xmlLista =  difficulties as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
      //  println(xmlSalida.toString())
      render(text:xmlSalida,contentType:"text/xml",encoding:"UTF-8")
        //def bookOutput = new XmlSlurper().parseText(xmlSalida) 
        // render bookOutput as XML)
       
        // render fri as XML
    }
    
    
      
    def listLevelsGroupByDifficultyServiceDepricated(){
        def validationResponse=new ResponseValidation()
        def levels=Level.findAll(sort:"difficulty")
        validationResponse.key = "1";
        validationResponse.value = "Levels";
        def difficult = -1
        def isFirth = true
         def difficulties = "<Difficulties>"
        for (level in levels){
             if (level.difficulty!=difficult){
                if (!isFirth)
           difficulties += "</Difficulty>"      
         difficulties += "<Difficulty>"      
         difficult=level.difficulty
                isFirth = false
                
            }
            def levelString = level as XML
        difficulties += (levelString).toString().substring(levelString.toString().indexOf(">")+1)
        }
        
          difficulties += "</Difficulty></Difficulties>"
        def xmlLista =   difficulties 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista+"</response>"
        println(xmlSalida.toString())
      render(text:xmlSalida,contentType:"text/xml",encoding:"UTF-8")
        //def bookOutput = new XmlSlurper().parseText(xmlSalida) 
        // render bookOutput as XML)
       
        // render fri as XML
    }
    /*
     *Este es el metodo que debes modificar KRISTIAN, para cargar toda la ventana
     **/
    
      def listLevelsOfPlayerService(){
      
        def validationResponse=new ResponseValidation()
        def levels=Level.findAll(sort:"difficulty")
        validationResponse.key = "1";
        validationResponse.value = "Levels";
        def xmlLista =  levels as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        println(xmlSalida.toString())
        render xmlSalida
        //def bookOutput = new XmlSlurper().parseText(xmlSalida) 
        // render bookOutput as XML)
       
        // render fri as XML
    }
    
  
}
