package Teacher

class User {
    int id
    String name
    String userName
    String token
    Date bornDate
    String country
    static hasMany = [score: Score,user: User]

    static constraints = {
    }
}
