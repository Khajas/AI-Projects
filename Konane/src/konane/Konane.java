/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konane;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class Konane {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int gameChoice=-1, rows, cols;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("Game Choice");
            System.out.println("1. Human vs Human");
            System.out.println("2. Human vs AI");
            System.out.println("3. AI vs AI");
            gameChoice=sc.nextInt();
        }while(gameChoice==-1);
        Game game=new Game(gameChoice);
        System.out.print("Board Size\nRows ?: ");
        rows=sc.nextInt();
        System.out.print("Cols?: ");
        cols=sc.nextInt();
        game.setBoard(rows, cols);
        game.printGame();
        game.startGame();
    }
}
