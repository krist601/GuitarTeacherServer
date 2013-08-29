package teacher
import javax.jws.*

class LoginService {
    
    static expose = ['xcfjax ']
    
    @WebResult(name="LoginRespuesta")
    @WebMethod(operationName="ValidateLogin") 
    String Login(@WebParam(name="Nickname")String nickname,@WebParam(name="Password")String password){
        def usuario = Usuario.findByUserName(nickname)
        if (usuario) {
             def contraseña = usuario.password
             if(contraseña==password){
                return "se loegeo"
             }
             else{
                return "el password no coincide"               
             }
        } else {
            return "No Existe el Usuario"
        }
        
    }
}

