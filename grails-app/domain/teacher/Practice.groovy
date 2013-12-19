package teacher

class Practice {
    String name
    String type
    
    static belongsTo = [audio: Audio]
    static constraints = {
        audio nullable: true
    }
}
