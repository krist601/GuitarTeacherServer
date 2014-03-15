package teacher

class Theory {
    String name
    String description
    
    static belongsTo = [audio: Audio, image: Image]
    static constraints = {
        audio nullable: true
        image nullable: true
        description(size:1..1000)
    }
 String toString(){
        return "${name}"
    }
}