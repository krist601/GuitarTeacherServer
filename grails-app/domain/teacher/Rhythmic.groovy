package teacher

class Rhythmic {
    String name
    String time
    String frequency
    String range
    
    static belongsTo = [image: Image]
  String toString(){
        return "${name}"
    }
}
