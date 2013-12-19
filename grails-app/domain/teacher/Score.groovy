package teacher

class Score {
    int score
    Date date
    
    static belongsTo = [player: Player, level: Level]

    static constraints = {
    }
}
