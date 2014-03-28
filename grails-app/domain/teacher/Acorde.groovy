package teacher

class Acorde {
    String name
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
