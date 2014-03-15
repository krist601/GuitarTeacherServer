package teacher

class Level {
    
    String name
    Integer difficulty
    String description
    static belongsTo = [normal: Image, success: Image, disabled: Image]
    
    static constraints = {
    }
 String toString(){
        return "${name}"
    }
}
