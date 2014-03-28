package teacher

class Ritmica {
    String name
    double[] tiempo
    double[] frecuencia
    double[] rango
    
    static belongsTo = [audio: Audio]
    static constraints = {
        audio nullable: true
    }
    String toString(){
        return "${name}"
    }
}
