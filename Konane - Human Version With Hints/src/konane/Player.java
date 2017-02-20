/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konane;

/**
 *
 * @author Anwar
 */
public class Player {
    private String playerName;
    private String myMarble;
    private boolean myTurn=false;
    Player(String playerType, String myMarble) {
        this.playerName=playerType;
        this.myMarble=myMarble;
    }
    // Getter and setter for turn
    public void setMyTurn() {
        this.myTurn = true;
    }
    public void resetMyTurn() {
        this.myTurn = false;
    }
    public boolean getMyTurn(){
        return this.myTurn;
    }
    // Getter and setter for player name
    public void setPlayerName(String name){
        this.playerName=name;
    }
    public String getPlayerName(){
        return this.playerName;
    }
    // Getter and setter for myMarble
    public void setMyMarble(String marble){
        this.myMarble=marble;
    }
    public String getMyMarble(){
        return this.myMarble;
    }
    public String getOpponentMarble(){
        if(this.myMarble.equals("white"))
            return "black";
        return "white";
    }
}
