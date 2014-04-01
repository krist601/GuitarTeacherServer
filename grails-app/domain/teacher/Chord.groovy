package teacher

class Chord {
    String name
    double[] frequency
    double[] range
    
    static constraints = {
        frequency nullable: true
        range nullable: true
    }
    String toString(){
        return "${name}"
    }
}
