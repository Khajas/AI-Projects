/****************************************************************
*           Introduction To Artificial Intelligence             * 
*   Title: Konane(Hawaiian Checkers, Alpha Beta Pruning)        *
*       CSCI 697         Program 2          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Thursday March 2, 2017            *
****************************************************************/
package konane.Strategy;

import java.util.Scanner;
import konane.Board.Board;

/**
 *
 * @author Anwar
 */
public class HumanStrategy extends Strategy{
    public HumanStrategy(Board brd) {
        super(brd);
    }
    @Override
    public boolean requestMove(Board brd){
        if(!canMove(brd, this.getPlayer())){
            return false;
        }
        Scanner sc=new Scanner(System.in);
        int fromRow, fromCol, toRow, toCol;
        boolean invalidMove;
        do{
            System.out.print("From Row #: ");
            fromRow=sc.nextInt();
            System.out.print("From Col #: ");
            fromCol=sc.nextInt();
            System.out.print("To Row #: ");
            toRow=sc.nextInt();
            System.out.print("To Col #: ");
            toCol=sc.nextInt();
            invalidMove=!isValidMove(fromRow-1, fromCol-1, toRow-1, toCol-1, brd, this.getPlayer());
            if(invalidMove) System.out.println("Ops!!! that's an invalid move");
        }while(invalidMove);
        return true;
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////