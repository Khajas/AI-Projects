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

import konane.Players.Player;
import java.util.ArrayList;
import konane.Board.Board;
import konane.Board.Slot;

/**
 *
 * @author Anwar
 */
public abstract class Strategy {
    final Board brd;
    private Player player;
    Strategy(Board brd){
        this.brd=brd;
    }
    public void setPlayer(Player player){
        this.player=player;
    }
    public Player getPlayer(){
        return this.player;
    }
    abstract public boolean requestMove(Board brd);

    private boolean ownedBySelf(int row, int col, Board board, Player p){
        return board.getBoard().get(row).get(col).getOccupiedBy().equals(p.getMyMarble());
    }
    
    private boolean isVacant(int row, int col, Board board){
        if(board.getBoard().get(row).get(col).getOccupiedBy().equals("free"))
            return true;
        System.out.println("Is not vacant");
        return false;
    }
    
    protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board, Player p){
        // It's valid, if it's from the player's marble and the two position is vacant
        // and there are only opponent's marbles on the way
        if(fromRow >= board.getRows() || fromCol >=board.getCols() 
                || toRow >= board.getRows() || toCol >=board.getCols() ){
            System.out.println("Please check dimensions!!!");
            return false;
        }
        if(p.getStrategy().ownedBySelf(fromRow, fromCol,board,p)){
           if(p.getStrategy().isVacant(toRow,toCol,board)){
               if(p.getStrategy().isHorizontalMove(fromRow,fromCol,toRow,toCol)){
                   if(p.getStrategy().isEastMove(fromCol, toCol )){
                       if(p.getStrategy().validateEastDirection(fromRow, fromCol, toRow, toCol,false, false,board, p)){
                            p.getStrategy().makeMove( fromRow, fromCol, toRow, toCol, "east",board, p);
                            return true;
                       }
                       return false;
                   }
                   else{
                        if(p.getStrategy().validateWestDirection(fromRow, fromCol, toRow, toCol,false, false, board,p)){
                            p.getStrategy().makeMove(fromRow, fromCol, toRow, toCol,"west",board, p);
                            return true;
                       }
                        return false;
                   }
               }
               else if(p.getStrategy().isVerticalMove(fromRow, fromCol, toRow, toCol)){     // The user want's to make a vertical move
                   if(p.getStrategy().isNorthMove(fromRow, toRow)){
                        if(p.getStrategy().validateNorthDirection(fromRow, fromCol, toRow, toCol,false, false,board,p)){
                            p.getStrategy().makeMove(fromRow, fromCol, toRow, toCol, "north",board,p);
                            return true;
                       }
                        return false;
                   }
                   else{
                        if(p.getStrategy().validateSouthDirection(fromRow, fromCol, toRow, toCol,false, false, board, p)){
                            p.getStrategy().makeMove(fromRow, fromCol, toRow, toCol, "south",board,p);
                            return true;
                       }
                        return false;
                   }
               }
           }
        }
        return false;
    }
    
    private boolean validateEastDirection(int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy, Board board, Player player){
        if(fromCol >=board.getCols()){
            Slot prev=board.getBoard().get(fromRow).get(fromCol-1);
            return board.getSlot(fromRow,fromCol-1).getOccupiedBy().equals("free") & foundOpponent
                    & prev.getX()==toRow & prev.getY()==toCol;
        }
        
        Slot s=board.getBoard().get(fromRow).get(fromCol+1);
        if(board.getSlot(s.getX(), s.getY()).getOccupiedBy().equals(player.getMyMarble()))
            return false; // If the south slot is of player itself
        if(board.getSlot(s.getX(), s.getY()).getEastSlot()== null){
            return board.getSlot(s.getX(), s.getY()).getOccupiedBy().equals("free") & foundOpponent
                    & s.getX()==toRow & s.getY()==toCol;
        }
        
        if(s.getX()==toRow && s.getY()==toCol){
            return board.getSlot(s.getX(),s.getY()-1).getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                   & board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free");
        }
         if(board.getSlot(s.getX(), s.getY()).getOccupiedBy().equals("free")){
            if(foundOpponent & greedy & s.getX()==toRow & s.getY()==toCol){
                return true;
            }
            else if(foundOpponent & greedy) return false;
            return validateEastDirection(fromRow, fromCol+1, toRow, toCol, foundOpponent, greedy, board, player);
        }
        else if(board.getSlot(s.getX(), s.getY()).getOccupiedBy().equals(player.getOpponentMarble())){
            return validateEastDirection(fromRow, fromCol+1, toRow, toCol, true, greedy, board, player);
        }
        else return false;
    }
    private boolean validateWestDirection(int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy, Board board, Player player){
        if(fromCol <= 0){
            Slot prev=board.getBoard().get(fromRow).get(fromCol+1);
            return board.getSlot(fromRow,fromCol+1).getOccupiedBy().equals("free") & foundOpponent
                    & prev.getX()==toRow & prev.getY()==toCol;
        }
        Slot s=board.getBoard().get(fromRow).get(fromCol-1);
        if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals(player.getMyMarble()))
            return false; // If the south slot is of player itself
        if(board.getSlot(s.getX(),s.getY()).getWestSlot()==null){            
            return board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free") & foundOpponent
                    & s.getX()==toRow & s.getY()==toCol;
        }
        if(s.getX()==toRow && s.getY()==toCol){
           return board.getSlot(s.getX(),s.getY()+1).getOccupiedBy().equals(player.getOpponentMarble()) & foundOpponent
                   & board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free");
        }
        if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free")){
            if(foundOpponent & greedy & s.getX()==toRow & s.getY()==toCol){
                return true;
            }
            else if(foundOpponent & greedy) return false;
            return validateWestDirection(fromRow, fromCol-1, toRow, toCol, foundOpponent, greedy, board,player);
        }
        else if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals(player.getOpponentMarble())){
            return validateWestDirection(fromRow, fromCol-1, toRow, toCol, true, greedy, board,player);
        }
        else return false;
    }
    private boolean validateNorthDirection(int fromRow, int fromCol, int toRow, int toCol, boolean foundOpponent, boolean greedy,Board board, Player p){
        if(fromRow <= 0){
            Slot prev=board.getBoard().get(fromRow+1).get(fromCol);
            return prev.getOccupiedBy().equals("free") & foundOpponent
                    & prev.getX()==toRow & prev.getY()==toCol;
        }
        Slot s=board.getBoard().get(fromRow-1).get(fromCol);
        if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals(p.getMyMarble()))
            return false; // If the south slot is of player itself
        if(board.getSlot(s.getX(),s.getY()).getNorthSlot()==null){
            return board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free") & foundOpponent
                    & s.getX()==toRow & s.getY()==toCol;
        }
        if(s.getX()==toRow && s.getY()==toCol){
            return board.getSlot(s.getX(),s.getY()).getSouthSlot().getOccupiedBy().equals(p.getOpponentMarble()) & foundOpponent
                    & board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free");
        }
        if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free")){
            if(foundOpponent & greedy & (s.getX()==toRow & s.getY()==toCol)){
                return true;
            } else if(foundOpponent & greedy) return false;
            return validateNorthDirection(fromRow-1, fromCol, toRow, toCol, foundOpponent, greedy,board, p);
        }
        else if(board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals(p.getOpponentMarble())){
            return validateNorthDirection(fromRow-1, fromCol, toRow, toCol, true, greedy, board,p);
        }
        else return false;
    }
    // If toCol==fromCol & fromRow!=toRow, the user is trying for a Vertical move 
    private boolean isVerticalMove(int fromRow, int fromCol, int toRow, int toCol){
        return (fromRow!=toRow) & (fromCol==toCol);
    }
    // If toCol==fromCol & fromRow!=toRow, the user is trying for a horizontal move 
    private boolean isHorizontalMove(int fromRow, int fromCol, int toRow, int toCol){
        return (fromCol!=toCol) & (fromRow==toRow);
    }
    // If fromCol is below the toCol, then it's a east direction
    private boolean isEastMove(int fromCol, int toCol){
        return fromCol < toCol;
    }
    // If fromRow is before the toRow, then it's a north direction
    private boolean isNorthMove(int fromRow, int toRow){
        return fromRow > toRow;
    }
    
    protected void makeMove(int fromRow, int fromCol, int toRow, int toCol, String direction, Board board, Player p){
        // Free the current slot
        Slot source=board.getBoard().get(fromRow).get(fromCol);
        source.setOccupiedBy("free");
//        board.printBoard();
        Slot s=board.getBoard().get(fromRow).get(fromCol);
        Slot prev=board.getBoard().get(fromRow).get(fromCol);
        do{
            if(direction.equals("north"))
                s=prev.getNorthSlot();
            if(direction.equals("south"))
                s=prev.getSouthSlot();
            if(direction.equals("east"))
                s=prev.getEastSlot();
            if(direction.equals("west"))
                s=prev.getWestSlot();
            if(s==null){
                return;
            }else{
                board.getSlot(s.getX(), s.getY()).setOccupiedBy("free");
            }
            prev=s;
        }while(!(s.getX()==toRow && s.getY()==toCol));
        board.getSlot(s.getX(), s.getY()).setOccupiedBy(p.getMyMarble());
    }
    public boolean canMove(Board board, Player p){
        ArrayList<ArrayList<Slot>> arr=board.getBoard();
        for(int i=0; i<board.getRows();++i){
            for(int j=0; j<board.getCols();++j){
                Slot s=arr.get(i).get(j);
                if(s.getOccupiedBy().equals(p.getMyMarble())){
                    if(getNextValidMove(i,j, board,p)!=null){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    protected Slot getNextValidMove(int i, int j, Board board, Player p){
        for(int temp=i-1; temp>=0; --temp){
            if(validateNorthDirection(i,j,temp,j,false,true,board, p)){
                board.getSlot(i,j).setPreferredDirection("north");
                return board.getSlot(temp, j);
            }
        }
        for(int temp=j-1; temp>=0; --temp){
            if(validateWestDirection(i,j,i,temp,false,true, board, p)){
                board.getSlot(i,j).setPreferredDirection("west");
                return board.getSlot(i, temp);
            }
        }
        for(int temp=i+1; temp<board.getRows(); ++temp){
            if(validateSouthDirection(i,j,temp,j,false,true,board, p)){
                board.getSlot(i,j).setPreferredDirection("south");
                return board.getSlot(temp, j);
            }
        }
        for(int temp=j+1; temp<board.getCols(); ++temp){
            if(validateEastDirection(i,j,i,temp,false,true,board,p)){
                board.getSlot(i,j).setPreferredDirection("east");
                return board.getSlot(i, temp);
            }
        }
        return null;
    }
    
    public Integer getLeftOverMoves(Board board, Player p){
        Integer movesLeft=0;
        for(int i=0; i<board.getRows(); ++i){
            for(int j=0; j<board.getCols();++j){
                if(board.getSlot(i, j).getOccupiedBy().equals(p.getMyMarble())){
                    ArrayList<Slot> ar=getAllValidMove(i,j,board,p);
                    if(ar!=null) movesLeft+=ar.size();
                }
            }
        }
        return movesLeft;
    }
    
    
    
    public ArrayList<Slot> getAllValidMove(int i, int j, Board board, Player p){
        ArrayList<Slot> allPossibleSlots=new ArrayList<>();
        for(int temp=i-1; temp>=0; --temp){
            if(validateNorthDirection(i,j,temp,j,false,true, board,p)){
                board.getSlot(i, j).setPreferredDirection("north");
                board.getSlot(i, j).setPreferredSlot(board.getSlot(temp, j));
                allPossibleSlots.add(board.getSlot(i, j));
            }
        }
        for(int temp=j-1; temp>=0; --temp){
            if(validateWestDirection(i,j,i,temp,false,true,board,p)){
                board.getSlot(i, j).setPreferredDirection("west");
                board.getSlot(i, j).setPreferredSlot(board.getSlot(i, temp));
                allPossibleSlots.add(board.getSlot(i, j));
            }
        }
        for(int temp=i+1; temp<board.getRows(); ++temp){
            if(validateSouthDirection(i,j,temp,j,false,true,board,p)){
                board.getSlot(i, j).setPreferredDirection("south");
                board.getSlot(i, j).setPreferredSlot(board.getSlot(temp, j));
                allPossibleSlots.add(board.getSlot(i, j));
            }
        }
        for(int temp=j+1; temp<board.getCols(); ++temp){
            if(validateEastDirection(i,j,i,temp,false,true,board, p)){
                board.getSlot(i, j).setPreferredDirection("east");
                board.getSlot(i, j).setPreferredSlot(board.getSlot(i, temp));
                allPossibleSlots.add(board.getSlot(i, j));
            }
        }
        if(allPossibleSlots.size()>0) return allPossibleSlots;
            return null;
    }

    private boolean validateSouthDirection(int fromRow, int fromCol,
            int toRow, int toCol, boolean foundOpponent, boolean greedy, Board board, Player p){
        if(fromRow >= board.getRows()){
            Slot prev=board.getBoard().get(fromRow-1).get(fromCol);
            return prev.getOccupiedBy().equals("free") & foundOpponent
                    & prev.getX()==toRow & prev.getY()==toCol;
        }
        Slot s=board.getBoard().get(fromRow+1).get(fromCol);
        if(s.getOccupiedBy().equals(p.getMyMarble()))
            return false; // If the south slot is of player itself
        if(s.getSouthSlot()==null){
            return s.getOccupiedBy().equals("free") & foundOpponent;
        }
        if(s.getX()==toRow && s.getY()==toCol){
           return board.getSlot(s.getX()-1,s.getY()).getOccupiedBy().equals(p.getOpponentMarble()) & foundOpponent
                   & board.getSlot(s.getX(),s.getY()).getOccupiedBy().equals("free");
        }
        if(s.getOccupiedBy().equals("free")){
            if(foundOpponent & greedy & s.getX()==toRow & s.getY()==toCol){
                return true;
            } else if(foundOpponent & greedy) return false;         
            return validateSouthDirection(fromRow+1, fromCol,toRow, toCol, foundOpponent, greedy, board, p);
        }
        else if(s.getOccupiedBy().equals(p.getOpponentMarble())){
            boolean r=validateSouthDirection(fromRow+1, fromCol,toRow, toCol, true, greedy, board,p);
            return r;
        }
        else return false;
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////