package teacher

class Ritmica {
    String name
    double[] tiempo
    double[] frecuencia
    double[] rango
    
    static belongsTo = [image: Image]
    static constraints = {
        tiempo nullable: true
        frecuencia nullable: true
        rango nullable: true
    }
    String toString(){
        return "${name}"
    }
}
