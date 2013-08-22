package Teacher

class Sample {
    int id
    String name
    String tonality
    byte[] sound
    static hasMany = [score: Score]

    static constraints = {
    }
}
