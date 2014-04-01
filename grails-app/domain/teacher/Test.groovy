package teacher

class Test {
    String title
    
    static belongsTo = [theory: Theory,question: Question,level: Level,testType: TestType, acorde: Acorde, rhythmic: Rhythmic, nota: Nota]
    static constraints = {
        theory nullable: true
        question nullable: true
        nota nullable: true
        rhythmic nullable: true
        acorde nullable: true
    }
    
}
