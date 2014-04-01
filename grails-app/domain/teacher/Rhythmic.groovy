package teacher

class Rhythmic {
    String name
    double[] time
    double[] frequency
    double[] range
    
    static belongsTo = [image: Image]
    static constraints = {
        time nullable: true
        frequency nullable: true
        range nullable: true
    }
    String toString(){
        return "${name}"
    }
}
