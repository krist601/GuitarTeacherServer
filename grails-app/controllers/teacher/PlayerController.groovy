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
    def signInService(){
        def sign=new SignIn()
        def nickname=request.XML?.nickname.toString()
        def password=request.XML?.password.toString()
        def name=request.XML?.name.toString()
        def repeatPassword=request.XML?.repeatPassword.toString()
        def email=request.XML?.email.toString()
        def bornDate=request.XML?.bornDate.toString() 
        
        if(nickname==""){
            sign.key="0"
            sign.value="Error, Nickname field can´t be empty"
            render sign as XML
        }
        def player = Player.findByNickname(nickname) 
        if (player){
            sign.key="0"
            sign.value="Error, The nickname already exist in the server"
            render sign as XML
            
        }
        player = Player.findByEmail(email) 
        if (player){
            sign.key="0"
            sign.value="Error, The email already exist in the server"
            render sign as XML
            
        }
        if(password==""){
            sign.key="0"
            sign.value="Error, Password field can´t be empty"
            render sign as XML
        }
        if(name==""){
            sign.key="0"
            sign.value="Error, Name field can´t be empty"
            render sign as XML
        }
        if(repeatPassword==""){
            sign.key="0"
            sign.value="Error, Repeat Password field can´t be empty"
            render sign as XML
        }
        if(email==""){
            sign.key="0"
            sign.value="Error, Email field can´t be empty"
            render sign as XML
        }
        EmailValidator emailValidator = EmailValidator.getInstance() 
        if (emailValidator.isValid(email)==false){
            sign.key="0"
            sign.value="Error, Email without correct format"
            render sign as XML
        }
        if(bornDate==""){
            sign.key="0"
            sign.value="Error, Born Date field can´t be empty"
            render sign as XML
        }
        if (password.equalsIgnoreCase(repeatPassword)){
            Date date
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy")
                date = formatter.parse(bornDate)                
            }
            catch(Exception){
                sign.key="0"
                sign.value="Error, invalid date"
                render sign as XML
            }
            try{
                def user =new Player(name: name,nickname: nickname,password: password,country: "Venezuela",email: email,token: request.getRemoteAddr().toString(),bornDate: date)
                user.save()
                sign.key="1"
                sign.value="User created successfully"
                render sign as XML
            }
            catch(Exception){
                sign.key="0"
                sign.value="Error, An error occurred while trying to create the user"
                render sign as XML
            }
        }
        else{       
            sign.key="0"
            sign.value="Error, The passwords should be the same"
            render sign as XML
        }
    }
    
    def loginService(){
        def user = request.XML?.nickname.toString()
         def player = Player.findByNickname(user)
        def log = new Login()
        if (player) {
                log.key="1"
                log.value="Logged Successfully"
                log.nickname=player.nickname
                render log as XML
           }
        else{     
     def newUser =new Player(nickname: user)
               if (newUser.save()){
         
            log.key="1"
            log.value="User created and logged successfully"
            render log as XML
               }
               else
           {
                log.key="0"
            log.value="System error"
            render log as XML
           
                
           }    
        }
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
        }}
        
    } 
    def deleteAccountService(){
        def nickname = request.XML?.nickname.toString()
        def response = new DeleteAccount()
        def player = Player.findByNickname(nickname)
        if(player){
            player.delete(flush:true)
            response.key="1"
            response.value="The user was deleted successfully"
        }
        else{
            response.key="0"
            response.value="Error, The user wasn't deleted"
        }
        render response as XML
    }
}
