package teacher

import org.springframework.dao.DataIntegrityViolationException
import sun.misc.BASE64Decoder
import Services.Login
import Services.AddOrDeleteFriend
import Services.SignIn
import Services.DeleteAccount
import Services.ResponseValidation
import Services.FriendScore
import grails.converters.XML
import java.text.SimpleDateFormat
import org.apache.commons.validator.EmailValidator
import grails.plugin.facebooksdk.FacebookGraphClient

class PlayerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [playerInstanceList: Player.list(params), playerInstanceTotal: Player.count()]
    }

    def create() {
        [playerInstance: new Player(params)]
    }

    def save() {
        def playerInstance = new Player(params)
        if (!playerInstance.save(flush: true)) {
            render(view: "create", model: [playerInstance: playerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'player.label', default: 'Player'), playerInstance.id])
        redirect(action: "show", id: playerInstance.id)
    }

    def show(Long id) {
        def playerInstance = Player.get(id)
        if (!playerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "list")
            return
        }

        [playerInstance: playerInstance]
    }

    def edit(Long id) {
        def playerInstance = Player.get(id)
        if (!playerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "list")
            return
        }

        [playerInstance: playerInstance]
    }

    def update(Long id, Long version) {
        def playerInstance = Player.get(id)
        if (!playerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (playerInstance.version > version) {
                playerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'player.label', default: 'Player')] as Object[],
                          "Another user has updated this Player while you were editing")
                render(view: "edit", model: [playerInstance: playerInstance])
                return
            }
        }

        playerInstance.properties = params

        if (!playerInstance.save(flush: true)) {
            render(view: "edit", model: [playerInstance: playerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'player.label', default: 'Player'), playerInstance.id])
        redirect(action: "show", id: playerInstance.id)
    }

    def delete(Long id) {
        def playerInstance = Player.get(id)
        if (!playerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "list")
            return
        }

        try {
            playerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'player.label', default: 'Player'), id])
            redirect(action: "show", id: id)
        }
    }
    
    def loginService(){
        println "entre de una"
        def user = request.XML?.nickname.toString()
        def userAccessToken = request.XML?.token.toString()
        def player = Player.findByNickname(user)
        def response = new Login()
        if (player) {
          
            response.key="1"
            response.value="Logged Successfully"
        }
        else{     
            println "Entro al logeo de new player"
            def newUser =new Player(nickname: user,lastConection:new Date())
            newUser.save(flush:true)
                Follower follower=new Follower()
                def resp=follower.addFollower(userAccessToken,user)
                if (resp.equals("0")){
                    response.key="1"
                    response.value="User created and logged successfully"
                }
                else{
                    response.key="1"
                    response.value=resp
                }
            }   
        
        render response as XML
    }
    

    def addOrDeleteFriendService(){
       
        //        BASE64Decoder dec=new BASE64Decoder();
        //        try {
        //            nicknameUser= new String(dec.decodeBuffer( nicknameUser ),"UTF-8");
        //        }
        //        catch ( IOException e ) {
        //            nicknameUser = "";
        //        }
        def response=new AddOrDeleteFriend()
        def nicknameUser=request.XML?.nicknameUser.toString()
        def nicknameFriend=request.XML?.nicknameFriend.toString()
                 
        def player = Player.findByNickname(nicknameUser) 
        if (player){
            if(nicknameUser==nicknameFriend){
                response.key="0"
                response.value="Error, the user can't be your own friend"
                render response as XML
            }
            def playerFollow = Player.findByNickname(nicknameFriend)
            if(playerFollow){
                def fri=Follower.findByPlayerAndFollower(player,playerFollow)
                if(fri){
                    fri.delete()
                    response.key="1"
                    response.value="Friend successfully deleted"
                    render response as XML
                }
                else{
                    def follower =new Follower()
                    follower.player=player
                    follower.follower=playerFollow
                    follower.save()
                    response.key="1"
                    response.value="Friend successfully added"
                    render response as XML
                }
            }
            else
            response.key="0"
            response.value="Error, the user you want to add as a friend don't exist"
            render response as XML
        }     
        else
        response.key="0"
        response.value="Error, the user logged don't exist"
        render response as XML
           
    } 
    
    def renderProfilePhoto() {    
         def respuesta = new ResponseValidation()
       
        def nick = request.XML?.nickname.toString();
        def player = new Player()
        player = Player.findByNickname(nick)
        if (player==null){
                 respuesta.key="0"
            respuesta.value="User not exist"
        render respuesta as XML
        }else{
     
        def imageInstance = player.image  
        if (player.image==null){
                      respuesta.key="0"
            respuesta.value="User has not image updated"
        render respuesta as XML
            }
        else{
            response.setContentType('multipart/form-data')  
        byte[] imageProfile = imageInstance.image 
        response.outputStream << imageProfile  
        }
    }
        
    } 
    def deleteAccountService(){
        def nickname = request.XML?.nickname.toString()
        def response = new DeleteAccount()
        def player = Player.findByNickname(nickname)
        if(player){
            player.delete(flush:true)
            response.key="1"
            response.value="The user was successfully removed"
        }
        else{
            response.key="0"
            response.value="Error, The user who tries to delete doesn't exist"
        }
        render response as XML
    }
    
    def getScoreFromNickname()
    {
        
    def user = request.XML?.nickname.toString()
           def player = Player.findByNickname(user)
        def response = new ResponseValidation()
        def total= 0;
        println("usuario: "+user)
    if (player!=null)
        { 

            def sum = Score.executeQuery("select max(score)  from Score  where testNumber=10 and state=1 and player="+player.id+" group by level")
           
           
            if (sum==null)
      sum = 0;
      else{
           for (def aux : sum)
        
           total = total + aux
       
                sum = total;
      }
      
            response.value = sum
        response.key="1"
        }else{
            
                   response.key="0"
            response.value="Error, El usuario no existe"
    
        }
        render response as XML
        
        
    }
}
