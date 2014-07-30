package teacher

class Score {
    int score
    Date date
    int state
    int live
    int testNumber
    int session
    int lastTest
    int lastGain
    static belongsTo = [player: Player, level: Level]

    static constraints = {
        
    }
 
}
