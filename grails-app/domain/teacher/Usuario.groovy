package teacher

class Usuario {
    String name
    String userName
    String password
    Date bornDate
    String country
    static belongsTo = [usuario: Usuario]
    
    static constraints = {
    }
}
