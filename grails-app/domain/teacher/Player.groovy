package teacher

class Player {
 
    String nickname
    String lastConection
    
    static constraints = {
    }
    static mapping = {
        cascade: 'all-delete-orphan'
    }
}
