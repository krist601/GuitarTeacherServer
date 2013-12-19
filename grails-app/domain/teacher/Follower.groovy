package teacher

class Follower {
  static belongsTo = [player: Player, follower: Player]
    static constraints = {
    }
}
