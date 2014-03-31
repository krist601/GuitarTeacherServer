package teacher

class Test {
    String title
    
    static belongsTo = [theory: Theory,question: Question,level: Level,testType: TestType, acorde: Acorde, ritmica: Ritmica, nota: Nota]
    static constraints = {
        theory nullable: true
        question nullable: true
        nota nullable: true
        ritmica nullable: true
        acorde nullable: true
    }
    
}
