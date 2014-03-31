package teacher

class Question {
    String question
    String answer
    
    static belongsTo = [image: Image]
    static constraints = {
        image nullable: true
    }
 String toString(){
        return "${question}"
    }
}
