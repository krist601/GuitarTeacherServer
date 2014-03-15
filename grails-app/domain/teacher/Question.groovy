package teacher

class Question {
    String question
    String answer
    
    static belongsTo = [audio: Audio, image: Image]
    static constraints = {
        image nullable: true
        audio nullable: true
    }
 String toString(){
        return "${question}"
    }
}
