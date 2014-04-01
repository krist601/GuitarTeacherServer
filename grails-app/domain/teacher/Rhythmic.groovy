package teacher

class Rhythmic {
    String name
    String time
    String frequency
    String ranges
       /*
        * there is a range for frequency
     * rangos : n1:m1-n2:m2
     * */
    static belongsTo = [image: Image]
  String toString(){
        return "${name}"
    }
}
