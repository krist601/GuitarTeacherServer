package Teacher

class Score {
    int id
    int score
    Date date
    static belongsTo = [User, Sample]

    static constraints = {
    }
}
