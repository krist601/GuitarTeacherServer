package teacher

class Note {
    String name
    String frequency
    String range
    /*
     * n1:m1
     * */
    static constraints = {
    }
    String toString(){
        return "${name}"
    }
}
