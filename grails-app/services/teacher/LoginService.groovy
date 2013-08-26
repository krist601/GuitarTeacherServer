package teacher
import javax.jws.*

class LoginService {
    
    static expose = ['xcfjax ']
    
    @WebResult(name="Login")
    @WebMethod(operationName="Validate Login") 
    Float Login(@WebParam(name="Nickname")String nickname,@WebParam(name="Password")String password){
        return 'Funciona'
    }
}
