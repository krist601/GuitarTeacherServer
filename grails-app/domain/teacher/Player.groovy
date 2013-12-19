package teacher

class Player {
 
    String nickname
    static constraints = {
    }
    static mapping = {
        cascade: 'all-delete-orphan'
    }
}
