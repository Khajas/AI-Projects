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

import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class Konane {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        int gameChoice=-1, rows, cols, depth1=0, depth2=0;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("Game Choice");
            System.out.println("1. Human vs Human");
            System.out.println("2. Human vs AI");
            System.out.println("3. AI vs AI");
            System.out.print("Your Choice: ");
            gameChoice=sc.nextInt();
            if(gameChoice>=2){
               System.out.print("AI Level?: ");
               depth1=sc.nextInt();
            }
            if(gameChoice>=3){
               System.out.print("AI #2 Level?: ");
               depth2=sc.nextInt();
            }
        }while(gameChoice==-1);
        System.out.print("Board Size\nRows?: ");
        rows=sc.nextInt();
        System.out.print("Cols?: ");
        cols=sc.nextInt();
        Game game=new Game(gameChoice, rows, cols, depth1, depth2);
        game.startGame();
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////