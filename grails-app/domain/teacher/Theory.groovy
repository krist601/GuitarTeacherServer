package teacher

class Theory {
    String description
    
    static belongsTo = [audio: Audio]
    static constraints = {
        audio nullable: true
    }
}
