/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konane;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class Game {
    private Board brd;
    private Player player1, player2;
    Game(int choice) {
        switch (choice) {
            case 1:
                this.player1=new Human("Human #1","white");
                this.player2=new AI("Human #2","black");
                break;
            case 2:
                this.player1=new Human("Human","white");
                this.player2=new AI("AI","black");
                break;
            default:
                this.player1=new AI("AI #1","white");
                this.player2=new AI("AI #2","black");
                break;
        }
    }
    
    public void startGame(){
        this.removeFirstMarbles();
        player1.setMyTurn();
        while(true){    // The game is not won or quit is requested
            if(player1.getMyTurn()){
                System.out.println(player1.getPlayerName()+"'s turn!");
                if(!this.requestMove(player1)){
                    System.out.println(player1.getPlayerName()+" Looses!!");
                    return;
                }
                player1.resetMyTurn();
                player2.setMyTurn();
            }
            else{
                System.out.println(player2.getPlayerName()+"'s turn!");
                if(!this.requestMove(player2)){
                    System.out.println(player2.getPlayerName()+" Looses!!");                    
                    return;
                }
                player2.resetMyTurn();
                player1.setMyTurn();
            }
        }
    }
    boolean requestMove(Player player){
        if(!canMove(player)){
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
            invalidMove=!isValidMove(player, fromRow-1, fromCol-1, toRow-1, toCol-1);
            if(invalidMove) System.out.println("That's invalid move");
        }while(invalidMove);
        return true;
    }

    boolean ownedBySelf(Player player, int row, int col){
        if(brd.getBoard().get(row).get(col).getOccupiedBy().equals(player.getMyMarble()))
            return true;
        System.out.println("Not owned");
        return false;
    }
    
    boolean isVacant(int row, int col){
        if(brd.getBoard().get(row).get(col).getOccupiedBy().equals("free"))
            return true;
        System.out.println("Is not vacant");
        return false;
    }
    
    boolean isValidMove(Player player, int fromRow, int fromCol, int toRow, int toCol){
        // It's valid, if it's from the player's marble and the two position is vacant
        // and there are only opponent's marbles on the way
        if(ownedBySelf(player, fromRow, fromCol)){
           if(isVacant(toRow,toCol)){
               if(isHorizontalMove(fromRow,fromCol,toRow,toCol)){
                   if(isEastMove(fromCol, toCol )){
                       System.out.println("The user is trying to make a horizontal move in east direction");
                       if(validateEastDirection(player, fromRow, fromCol, toRow, toCol,false, false)){
                            System.out.println("WOW!! That's valid in East Direction");
                            makeMove(player, fromRow, fromCol, toRow, toCol, "east");
                            return true;
                       }
                       return false;
                   }
                   else{
                       System.out.println("The user is trying to make a horizontal move in west direction");
                        if(validateWestDirection(player, fromRow, fromCol, toRow, toCol,false, false)){
                            System.out.println("WOW!! That's valid move in West Direction");
                            makeMove(player, fromRow, fromCol, toRow, toCol,"west");
                            return true;
                       }
                        return false;
                   }
               }
               else if(isVerticalMove(fromRow, fromCol, toRow, toCol)){     // The user want's to make a vertical move
                   if(isNorthMove(fromRow, toRow)){
                       System.out.println("The user is trying to make a verical move in north direction");
                        if(validateNorthDirection(player, fromRow, fromCol, toRow, toCol,false, false)){
                            System.out.println("WOW!! That's valid in North Direction");
                            makeMove(player, fromRow, fromCol, toRow, toCol, "north");
                            return true;
                       }
                        return false;
                   }
                   else{
                       System.out.println("The user is trying to make a vertical move in south direction");
                        if(validateSouthDirection(player, fromRow, fromCol, toRow, toCol,false, false)){
                            System.out.println("WOW!! That's valid in South Direction");
                            makeMove(player, fromRow, fromCol, toRow, toCol, "south");
                            return true;
                       }
                        return false;
                   }
               }
           }
        }
        return false;
    }
    
    boolean validateEastDirection(Player player, int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy){
        if(fromCol >=brd.getCols()){
            Slot prev=brd.getBoard().get(fromRow).get(fromCol-1);
            return prev.getOccupiedBy().equals("free") & foundOpponent;
        }
        Slot s=brd.getBoard().get(fromRow).get(fromCol).getEastSlot();
        if(s.equals(brd.getBoard().get(toRow).get(toCol)))
            return s.getWestSlot().getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                                        & s.getOccupiedBy().equals("free");
        if(s.getEastSlot()== null)
            return s.getWestSlot().getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                                        & s.getOccupiedBy().equals("free");
        if(s.getOccupiedBy().equals("free")){
            if(foundOpponent & greedy) return true;
            return validateEastDirection(player, fromRow, fromCol+1, toRow, toCol, foundOpponent, greedy);
        }
        else if(s.getOccupiedBy().equals(player.getOpponentMarble())){
            return validateEastDirection(player, fromRow, fromCol+1, toRow, toCol, true, greedy);
        }
        else return false;
    }
       
    boolean validateWestDirection(Player player, int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy){
        if(fromCol <= 0){
            Slot prev=brd.getBoard().get(fromRow).get(fromCol+1);
            return prev.getOccupiedBy().equals("free") & foundOpponent;
        }
        Slot s=brd.getBoard().get(fromRow).get(fromCol).getWestSlot();
        if(s.equals(brd.getBoard().get(toRow).get(toCol))){
            return s.getEastSlot().getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                                        & s.getOccupiedBy().equals("free");
        }
        if(s.getWestSlot()==null){
            return s.getEastSlot().getOccupiedBy().equals("free") & foundOpponent;
        }
        if(s.getOccupiedBy().equals("free")){
            if(foundOpponent & greedy) return true;
            return validateWestDirection(player, fromRow, fromCol-1, toRow, toCol, foundOpponent, greedy);
        }
        else if(s.getOccupiedBy().equals(player.getOpponentMarble())){
            return validateWestDirection(player, fromRow, fromCol-1, toRow, toCol, true, greedy);
        }
        else return false;
    }
    boolean validateNorthDirection(Player player, int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy){
        if(fromRow <= 0){
            Slot prev=brd.getBoard().get(fromRow+1).get(fromCol);
            return prev.getOccupiedBy().equals("free") & foundOpponent;
        }
        Slot s=brd.getBoard().get(fromRow).get(fromCol).getNorthSlot();
        if(s.getNorthSlot()==null){
            return foundOpponent & s.getOccupiedBy().equals("free");
        }
        if(s.equals(brd.getBoard().get(toRow).get(toCol))){
            return s.getSouthSlot().getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                    & s.getOccupiedBy().equals("free");
        }
        if(s.getOccupiedBy().equals("free")){
            if(foundOpponent & greedy) return true;
            return validateNorthDirection(player, fromRow-1, fromCol, toRow, toCol, foundOpponent, greedy);
        }
        else if(s.getOccupiedBy().equals(player.getOpponentMarble())){
            return validateNorthDirection(player, fromRow-1, fromCol, toRow, toCol, true, greedy);
        }
        else return false;
    }
       
    boolean validateSouthDirection(Player player,  int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy){
        if(fromRow >= brd.getRows()){
            Slot prev=brd.getBoard().get(fromRow-1).get(fromCol);
            return prev.getOccupiedBy().equals("free") & foundOpponent;
        }
        Slot s=brd.getBoard().get(fromRow).get(fromCol).getSouthSlot();
        if(s.getSouthSlot()==null){
            return s.getNorthSlot().getOccupiedBy().equals("free") & foundOpponent;
        }
        if(s.equals(brd.getBoard().get(toRow).get(toCol))){
            return s.getNorthSlot().getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                    & s.getOccupiedBy().equals("free");
        }
        if(s.getOccupiedBy().equals("free")){
            if(foundOpponent & greedy) return true;          
            return validateSouthDirection(player, fromRow+1, fromCol,toRow, toCol, foundOpponent, greedy);
        }
        else if(s.getOccupiedBy().equals(player.getOpponentMarble())){
            return validateSouthDirection(player, fromRow+1, fromCol,toRow, toCol, true, greedy);
        }
        else return false;
    }
       
    // If toCol==fromCol & fromRow!=toRow, the user is trying for a Vertical move 
    boolean isVerticalMove(int fromRow, int fromCol, int toRow, int toCol){
        return (fromRow!=toRow) & (fromCol==toCol);
    }
    // If toCol==fromCol & fromRow!=toRow, the user is trying for a horizontal move 
    boolean isHorizontalMove(int fromRow, int fromCol, int toRow, int toCol){
        return (fromCol!=toCol) & (fromRow==toRow);
    }
    // If fromCol is below the toCol, then it's a east direction
    boolean isEastMove(int fromCol, int toCol){
        return fromCol < toCol;
    }
    // If fromRow is before the toRow, then it's a north direction
    boolean isNorthMove(int fromRow, int toRow){
        return fromRow > toRow;
    }
    
    void makeMove(Player player, int fromRow, int fromCol, int toRow, int toCol, String direction){
        // Free the current slot
        brd.getBoard().get(fromRow).get(fromCol).setOccupiedBy("free");
        Slot source=brd.getBoard().get(fromRow).get(fromCol);
        Slot s=null;
        Slot prev=source;
        do{
            switch(direction){
                case "north":
                    s=prev.getNorthSlot();
                    break;
                case "south":
                    s=prev.getSouthSlot();
                    break;
                case "east":
                    s=prev.getEastSlot();
                    break;
                case "west":
                    s=prev.getWestSlot();
                    break;
            }
            if(s==null) return;
            s.setOccupiedBy("free");
            prev=s;
        }while(!s.equals(brd.getBoard().get(toRow).get(toCol)));
        brd.getBoard().get(toRow).get(toCol).setOccupiedBy(player.getMyMarble());
        brd.printBoard();
    }
    private void removeFirstMarbles(){
       Random r=new Random();
       System.out.println("Removing the first marbles!");
       if(r.nextInt(2)%2==0)
           brd.removeTwoFromMiddle();
       else brd.removeTwoFromCorner();
       brd.printBoard();
    }
    
    public void setBoard(int row, int col){
        brd=new Board(row, col);
        brd.initBoard();
        brd.placeMarbles();
    }
    
    public void printGame(){
        brd.printBoard();
    }
    
    boolean canMove(Player player){
        ArrayList<ArrayList<Slot>> arr=brd.getBoard();
        for(int i=0; i<brd.getCols();++i){
            for(int j=0; j<brd.getRows();++j){
                Slot s=arr.get(i).get(j);
                if(s.getOccupiedBy().equals(player.getMyMarble())){
                    for(int temp=i-1; temp>=0; --temp){
                        if(validateNorthDirection(player,i,j,temp,j,false,true)){
                            System.out.print("Hint: Slot ("+(i+1)+","+(j+1)+")");
                            System.out.println("Can move in North Direction");
                            return true;
                        }
                    }
                    for(int temp=i+1; temp<brd.getRows(); ++temp){
                        if(validateSouthDirection(player,i,j,temp,j,false,true)){
                            System.out.print("Hint: Slot ("+(i+1)+","+(j+1)+")");
                            System.out.println("Can move in South Direction");
                            return true;
                        }
                    }
                    for(int temp=j+1; temp<brd.getCols(); ++temp){
                        if(validateEastDirection(player,i,j,i,temp,false,true)){
                            System.out.print("Hint: Slot ("+(i+1)+","+(j+1)+")");
                            System.out.println("Can move in East Direction");
                            return true;
                        }
                    }
                    for(int temp=j-1; temp>=0; --temp){
                        if(validateWestDirection(player,i,j,i,temp,false,true)){
                            System.out.print("Hint: Slot ("+(i+1)+","+(j+1)+")");
                            System.out.println("Can move in West Direction");
                            return true;
                        }
                    }
                    System.out.println("Hint: Slot ("+(i+1)+","+(j+1)+") can't move anywhere!!!");
                }
            }
        }
        return false;
    }
}
