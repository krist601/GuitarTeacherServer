package teacher
import javax.jws.*

class LoginService {
    
    static expose = ['xcfjax ']
    
    @WebResult(name="Login")
    @WebMethod(operationName="ValidateLogin") 
    String Login(@WebParam(name="Nickname")String nickname,@WebParam(name="Password")String password){
        return "Hola ${nickname}! ${password}!"
    }
}