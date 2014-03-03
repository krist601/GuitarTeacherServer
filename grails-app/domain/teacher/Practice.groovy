package teacher

class Practice {
    String name
    String type
    
    static belongsTo = [audio: Audio, image: Image]
    static constraints = {
        image nullable: true
        audio nullable: true
    }
}
