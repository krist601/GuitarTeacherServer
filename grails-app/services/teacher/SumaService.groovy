package teacher
import javax.jws.*

class SumaService {
 
static expose=['xcfjax']

    @WebResult(name="Login Services")
    @WebMethod(operationName="add") 
    String sayHello(@WebParam(name="Nickname")String Nickname,@WebParam(name="yourName")String yourName) {
        return "Hello ${yourName}!"
    }

}