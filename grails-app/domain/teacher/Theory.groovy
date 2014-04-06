package teacher

class Theory {
    String name
    String description
    static belongsTo = [image: Image,audio: Audio]
    static constraints = {
        image nullable: true
        audio nullable: true
        description(size:1..1000)
    }
 String toString(){
        return "${name}"
    }
}