package teacher
import teacher.Usuario
import teacher.Sample

class Score {
    int score
    Date date
    static belongsTo = [sample: Sample, usuario: Usuario]
    //static belongsTo = [Usuario, Sample]

    static constraints = {
    }
}
