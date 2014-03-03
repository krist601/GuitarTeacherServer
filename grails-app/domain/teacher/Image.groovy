package teacher

class Image {

    byte[] image
    String name
    
    static belongsTo = [question: Question]
    static constraints = {
        image type:'blob'
        question nullable: true
    }
}
