package teacher

class Question {
    String question
    String answer
    
    static belongsTo = [audio: Audio]
    static constraints = {
        audio nullable: true
    }
}
