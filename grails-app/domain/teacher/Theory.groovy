package teacher

class Theory {
    String name
    String description
    
    static belongsTo = [image: Image]
    static constraints = {
        image nullable: true
        description(size:1..1000)
    }
 String toString(){
        return "${name}"
    }
}