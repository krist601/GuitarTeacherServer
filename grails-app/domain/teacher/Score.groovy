package teacher

class Score {
    int score
    Date date
    int state
    int live
    int testNumber
    
    static belongsTo = [player: Player, level: Level]

    static constraints = {
    }
}
