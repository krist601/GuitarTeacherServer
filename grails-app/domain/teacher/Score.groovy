package teacher

class Score {
    
    int score
    Date date
    static belongsTo = [usuario: Usuario,sample: Sample]
    
    static constraints = {
    }
}
