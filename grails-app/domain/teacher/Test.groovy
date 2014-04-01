package teacher

class Test {
    String title
    
    static belongsTo = [theory: Theory,question: Question,level: Level,testType: TestType, chord: Chord, rhythmic: Rhythmic, note: Note]
    static constraints = {
        theory nullable: true
        question nullable: true
        note nullable: true
        rhythmic nullable: true
        chord nullable: true
    }
    
}
