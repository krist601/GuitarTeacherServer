package teacher

class Player {
 
    String nickname
    String notification
    static constraints = {
    }
    static mapping = {
        cascade: 'all-delete-orphan'
    }
}
