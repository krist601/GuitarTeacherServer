package teacher

class Test {
    String title
    
    static belongsTo = [image: Image,theory: Theory,question: Question,practice: Practice,level: Level,testType: TestType]
    static constraints = {
        image nullable: true
        theory nullable: true
        question nullable: true
        practice nullable: true
    }
    
}
