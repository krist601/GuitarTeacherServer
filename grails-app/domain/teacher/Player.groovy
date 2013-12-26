package teacher

class Player {
 
    String nickname
    Date lastConection
    
    static constraints = {
    }
    static mapping = {
        cascade: 'all-delete-orphan'
    }
}
