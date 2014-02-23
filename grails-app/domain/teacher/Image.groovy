package teacher

class Image {

    byte[] image
    String name
    
    static belongsTo = [theory: Theory,question: Question,practice: Practice,level: Level]
    static constraints = {
        image type:'blob'
        theory nullable: true
        question nullable: true
        practice nullable: true
        level nullable: true
    }
}
