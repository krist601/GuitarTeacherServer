package teacher

class Chord {
    String name
    double[] frecuencia
    double[] rango
    
    static constraints = {
        frequency nullable: true
        range nullable: true
    }
    String toString(){
        return "${name}"
    }
}
