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

import konane.Board.Board;
import konane.Strategy.HumanStrategy;

/**
 *
 * @author Anwar
 */
public class HumanPlayer extends Player{
    public HumanPlayer(String playerName, String myMarble, Board brd) {
        super(playerName, myMarble);
        this.strategy=new HumanStrategy(brd);
        this.strategy.setPlayer(this);
    }
    @Override
    void setPlayerName(String name) {
        playerName=name;
    }

    @Override
    public void setOpponentPlayer(Player opponent) {
        this.opponentPlayer=opponent;
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////