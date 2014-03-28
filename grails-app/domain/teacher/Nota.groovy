package teacher

class Nota {
    String name
    String frecuencia
    
    static belongsTo = [audio: Audio]
    static constraints = {
        audio nullable: true
    }
    String toString(){
        return "${name}"
    }
}
