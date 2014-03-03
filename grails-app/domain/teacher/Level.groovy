package teacher

class Level {
    
    String name
    Integer difficulty
    String description
    static belongsTo = [normal: Image, success: Image]
    
    static constraints = {
    }
}
