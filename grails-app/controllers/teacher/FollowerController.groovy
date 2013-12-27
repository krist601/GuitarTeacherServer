package teacher

import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.facebooksdk.FacebookGraphClient
import Services.ResponseValidation
import Services.FriendScore
import grails.converters.XML

class FollowerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [followerInstanceList: Follower.list(params), followerInstanceTotal: Follower.count()]
    }

    def create() {
        [followerInstance: new Follower(params)]
    }

    def save() {
        def followerInstance = new Follower(params)
        if (!followerInstance.save(flush: true)) {
            render(view: "create", model: [followerInstance: followerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'follower.label', default: 'Follower'), followerInstance.id])
        redirect(action: "show", id: followerInstance.id)
    }

    def show(Long id) {
        def followerInstance = Follower.get(id)
        if (!followerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "list")
            return
        }

        [followerInstance: followerInstance]
    }

    def edit(Long id) {
        def followerInstance = Follower.get(id)
        if (!followerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "list")
            return
        }

        [followerInstance: followerInstance]
    }

    def update(Long id, Long version) {
        def followerInstance = Follower.get(id)
        if (!followerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (followerInstance.version > version) {
                followerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'follower.label', default: 'Follower')] as Object[],
                          "Another user has updated this Follower while you were editing")
                render(view: "edit", model: [followerInstance: followerInstance])
                return
            }
        }

        followerInstance.properties = params

        if (!followerInstance.save(flush: true)) {
            render(view: "edit", model: [followerInstance: followerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'follower.label', default: 'Follower'), followerInstance.id])
        redirect(action: "show", id: followerInstance.id)
    }

    def delete(Long id) {
        def followerInstance = Follower.get(id)
        if (!followerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "list")
            return
        }

        try {
            followerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'follower.label', default: 'Follower'), id])
            redirect(action: "show", id: id)
        }
    }
     def listFollowersService(){
      
        def user=request.XML.nickname.toString();
        def usuario = Player.findByNickname(user)
        def validationResponse=new ResponseValidation()
        def friends = new ArrayList<FriendScore>()
        def j=Follower.executeQuery("select p.nickname  as nick, (select coalesce(sum(s.score),'0') from Score s where p=s.player) as score from Follower f,Player p where f.player="+usuario.id+" and f.follower=p ")
        for ( aux in j){
            def friend = new FriendScore()
            friend.name = aux[0]
            friend.score = aux[1]
            friends <<friend
       
        }
        validationResponse.key = "1";
        validationResponse.value = "Followers";
        def xmlLista =  friends as XML 
        def xmlRespuesta = validationResponse as XML 
        def xmlSalida = "<?xml version=\"1.0\" encoding=\"UTF-8\"  ?><response>"+xmlRespuesta.toString().substring(xmlRespuesta.toString().indexOf(">")+1)+xmlLista.toString().substring(xmlLista.toString().indexOf(">")+1)+"</response>"
        println(xmlSalida.toString())
        render xmlSalida
       
    }
  
    def uploadProfilePhotoService(String nickName) {
        def player = new Player()
        def respuesta = new ResponseValidation()
        player = Player.findByNickname(nickName)
        if (player==null){
                 respuesta.key="0"
            respuesta.value="User not exist"
        }else{
     def f = request.getFile('profilePhoto')
        if (f==null){
                 respuesta.key="0"
            respuesta.value="Error sending file"
       
        }else
        if (f.empty) {
            respuesta.key="0"
            respuesta.value="Empty file"
        }
        else{
            def imagen = new Image()
            imagen.image = f.getBytes()
            imagen.save()
            player.image = imagen
            player.save()
            respuesta.key="1"
            respuesta.value="Photo updated"
            
        }}
        render respuesta as XML
        
    }
}
