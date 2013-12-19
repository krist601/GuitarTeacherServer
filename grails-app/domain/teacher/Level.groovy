package teacher

class Level {
    
    String name
    Integer difficulty
    String description
    
    static belongsTo = [image: Image]
    static constraints = {
          image nullable: true
    }
}
