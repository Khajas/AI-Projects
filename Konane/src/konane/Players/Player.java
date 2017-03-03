/****************************************************************
*           Introduction To Artificial Intelligence             * 
*   Title: Konane(Hawaiian Checkers, Alpha Beta Pruning)        *
*       CSCI 697         Program 2          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Thursday March 2, 2017            *
****************************************************************/
package konane.Players;

import konane.Strategy.Strategy;

/**
 *
 * @author Anwar
 */
abstract public class Player {
    protected String playerName;
    private String myMarble;
    private boolean myTurn=false;
    protected Strategy strategy;
    protected Player opponentPlayer;
    Player(String playerType, String myMarble) {
        this.playerName=playerType;
        this.myMarble=myMarble;
    }
    public abstract void setOpponentPlayer(Player opponent);
    // Getter and setter for turn
    public Player getOpponentPlayer(){
        return this.opponentPlayer;
    }
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
    abstract void setPlayerName(String name);
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
    public Strategy getStrategy(){
        return this.strategy;
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////