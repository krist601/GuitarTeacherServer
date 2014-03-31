package teacher

class Acorde {
    String name
    double[] frecuencia
    double[] rango
    
    static constraints = {
        frecuencia nullable: true
        rango nullable: true
    }
    String toString(){
        return "${name}"
    }
}
