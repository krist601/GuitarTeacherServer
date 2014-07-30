package teacher

class PracticeScore {
    int points
    
    static belongsTo = [score: Score, note: Note, rhythmic: Rhythmic, chord: Chord]
    
    static constraints = {
        note nullable: true
        rhythmic nullable: true
        chord nullable: true
    }
}
