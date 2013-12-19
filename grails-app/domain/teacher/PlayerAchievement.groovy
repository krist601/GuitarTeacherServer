package teacher

class PlayerAchievement {

    static belongsTo = [player: Player, achievement: Achievement]
    static constraints = {
    }
}
