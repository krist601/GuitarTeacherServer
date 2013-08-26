package teacher
import javax.jws.*

class LoginService {
    
    static expose = ['xcfjax ']
    
    @WebResult(name="Login")
    @WebMethod(operationName="add") 
    Float Login(){
        return 'Funciona'
    }
}
