/****************************************************************
*           Introduction To Artificial Intelligence             * 
*   Title: Konane(Hawaiian Checkers, Alpha Beta Pruning)        *
*       CSCI 697         Program 2          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Thursday March 2, 2017            *
****************************************************************/
package konane;

import konane.Board.Board;
import konane.Players.HumanPlayer;
import konane.Players.Player;
import konane.Players.AIPlayer;
import java.util.Random;

/**
 *
 * @author Anwar
 */
public class Game {
    private final Player player1, player2;
    private Board brd;
    Game(int choice, int rows, int cols, int depth1, int depth2){
        brd=new Board(rows, cols);
        switch (choice) {
            case 1:
                this.player1=new HumanPlayer("Human #1","white",brd);
                this.player2=new HumanPlayer("Human #2","black",brd);
                break;
            case 2:
                this.player1=new HumanPlayer("Human","white",brd);
                this.player2=new AIPlayer("AI","black",brd,depth1);
                break;
            default:
                this.player1=new AIPlayer("AI #1","white",brd,depth1);
                this.player2=new AIPlayer("AI #2","black",brd,depth2);
                break;
        }
        this.player1.setOpponentPlayer(player2);
        this.player2.setOpponentPlayer(player1);
    }
    public void startGame(){
        this.setBoard();
        this.printGame();
        this.removeFirstMarbles();
        player1.setMyTurn();
        while(true){    // The game is not won or quit is requested
            if(player1.getMyTurn()){
                System.out.println(player1.getPlayerName()+"'s turn!");
                if(!player1.getStrategy().requestMove(brd)){
                    System.out.println(player1.getPlayerName()+" Looses!!");
                    return;
                }
                else this.printGame();
                player1.resetMyTurn();
                player2.setMyTurn();
            }
            else{
                System.out.println(player2.getPlayerName()+"'s turn!");
                if(!player2.getStrategy().requestMove(brd)){
                    System.out.println(player2.getPlayerName()+" Looses!!");                    
                    return;
                }
                else this.printGame();
                player2.resetMyTurn();
                player1.setMyTurn();
            }
        }
    }
    private void removeFirstMarbles(){
       boolean demo=false;
        Random r=new Random();
       System.out.println("Removing the first marbles!");
       if(demo)
           brd.demoRemove();
       else if(r.nextInt(2)%2==0)
           brd.removeTwoFromMiddle();
       else brd.removeTwoFromCorner();
       brd.printBoard();
    }
    
    private void setBoard(){
        brd.initBoard();
        brd.placeMarbles();
    }

    private void printGame(){
        brd.printBoard();
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////