package teacher


import grails.plugin.facebooksdk.FacebookGraphClient

class Follower {
  static belongsTo = [player: Player, follower: Player]
    static constraints = {
        player unique: 'follower'
    }
    
    def addFollower(String userAccessToken,String nickname){
        def response
        try{
            def facebookClient = new FacebookGraphClient(userAccessToken)
            String query = "SELECT uid2 FROM friend WHERE uid1= "+nickname
            def users = facebookClient.executeQuery(query)
            def player2=Player.findByNickname(nickname)
            if(player2){        
                users.toString().tokenize("],").each{
                    def spl=it.tokenize('"')
                    def player=Player.findByNickname(spl[3])
                    if(player){
                        def fri=Follower.findByPlayerAndFollower(player,player2)
                        if(!fri){
                            def follower =new Follower()
                            follower.player=player
                            follower.follower=player2
                            follower.save()
                        }
                        fri=Follower.findByPlayerAndFollower(player2,player)
                        if(!fri){
                            def follower2 =new Follower()
                            follower2.player=player2
                            follower2.follower=player
                            follower2.save()
                        }
                    }
                }
                response="0" 
            }
            else{
                response="Error, The user id doesn't exist"            
            }
        }
        catch(Exception){
                response="Error, Facebook's token session has expired"  
        }
        return response
    }
}
