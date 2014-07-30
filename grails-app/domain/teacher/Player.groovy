package teacher

class Player {
 
    String nickname
    Date lastConection
    
    static constraints = {
        nickname unique: true
    }
    static mapping = {
        cascade: 'all-delete-orphan'
    }
}
