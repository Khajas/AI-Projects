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

import java.util.ArrayList;
import konane.Board.Board;
import konane.Board.Slot;

/**
 *
 * @author Anwar
 */
public class AIStrategy extends Strategy{
    private int depth=-1;
    private final ArrayList<Slot> toFromArray;
    private final Board board;
    public AIStrategy(Board brd, int depth) {
        super(brd);
        this.depth=depth;
        toFromArray=new ArrayList<>(2);
        this.board=new Board(brd.getRows(), brd.getCols());
    }
    private void CopyMarbles(Board brd){
        for(int i=0; i < brd.getRows(); ++i){
            ArrayList<Slot> cp_row=new ArrayList<>();
            for(int j=0; j<brd.getCols(); ++j){
                Slot copy_s=new Slot(brd.getSlot(i, j));
                cp_row.add(copy_s);
            }
            board.getBoard().set(i,cp_row);
        }
    }
    @Override
    public boolean requestMove(Board brd) {
        CopyMarbles(brd);
        if(!canMove(brd, this.getPlayer())){
            return false;
        }
        Integer maxScore=Integer.MIN_VALUE;
        System.out.println("Thinking...");
        OUTER: for(int i=0; i < board.getRows(); ++i){
            for(int j=0; j < board.getCols(); ++j){
                if(board.getSlot(i, j).getOccupiedBy().equals(this.getPlayer().getMyMarble())){   // If it's player's marble
                    ArrayList<Slot> allValidSlots=this.getAllValidMove(i, j, board, this.getPlayer());
                    if(allValidSlots!=null){ // If there are more moves possible
                        brd.getSlot(i,j).setPreferredSlot(brd.getSlot(board.getSlot(i, j).getPreferredSlot().getX(), 
                                board.getSlot(i, j).getPreferredSlot().getY()));    // Don't remove board here !!
                        toFromArray.add(brd.getSlot(i, j));
                        Integer result=alpha_beta_caller(depth, i, j, new Board(brd));
                        if(maxScore < result){
                            maxScore=result;
                            toFromArray.set(0, brd.getSlot(i, j));
                        }
                            else toFromArray.remove(toFromArray.size()-1);
                    }
                }
            }
        }
        Slot from=toFromArray.get(0);
        Slot to=from.getPreferredSlot();
        System.out.println(this.getPlayer().getPlayerName()+ " says \"I would be requesting a move from ("+(from.getX()+1)+","+(from.getY()+1)+")"
                + " which is occupied by: "+brd.getSlot(from.getX(),from.getY()).getOccupiedBy()+" to ("+(to.getX()+1)+
                ","+(to.getY()+1)+") which is currently: "+brd.getSlot(to.getX(), to.getY()).getOccupiedBy()+"\"");
        this.isValidMove(from.getX(), from.getY(), to.getX(), to.getY(), brd, this.getPlayer());// Returns true always
        toFromArray.clear();
        return true;
    }
    
    private Integer alpha_beta_caller(int depth, int row, int col, Board board){
        return alpha_beta(depth, row, col, -1000, 1000, true, board);
    }
    
    private Integer alpha_beta(int depth, int row, int col, int alpha, int beta, boolean maximizingPlayer, Board board){
        System.gc();
        if(depth==0 || board.getSlot(row, col) == null){
            int heuristics=this.getLeftOverMoves(board,this.getPlayer());
            return heuristics;
        }
        if(maximizingPlayer){
            int v=-10000;
            isValidMove(row, col, board.getSlot(row, col).getPreferredSlot().getX(),
                board.getSlot(row, col).getPreferredSlot().getY(), board, this.getPlayer());
            if(!canMove(board, this.getPlayer().getOpponentPlayer())){
                return 10000;
            }
            for(int i=0; i < board.getRows(); ++i){
                for(int j=0; j < board.getCols(); ++j){
                    if(board.getSlot(i, j).getOccupiedBy().equals(this.getPlayer().getOpponentMarble())){   // If it's player's marble
                        ArrayList<Slot> allMoves=this.getAllValidMove(i, j, board, this.getPlayer().getOpponentPlayer());
                        if(allMoves!=null){
                            for(Slot child: allMoves){
                                v=Math.max(v,alpha_beta(depth-1, child.getX(),child.getY(), alpha, beta, false, new Board(board)));
                                alpha=Math.max(alpha, v);
                                if(beta <= alpha){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return alpha;
        }
        else{
            int v=10000;
            isValidMove(row, col, board.getSlot(row, col).getPreferredSlot().getX(),
                board.getSlot(row, col).getPreferredSlot().getY(), board, this.getPlayer().getOpponentPlayer());
            if(!canMove(board, this.getPlayer())){
                return alpha;
            }
            for(int i=0; i < board.getRows(); ++i){
                for(int j=0; j < board.getCols(); ++j){
                    if(board.getSlot(i, j).getOccupiedBy().equals(this.getPlayer().getMyMarble())){   // If it's player's marble
                        ArrayList<Slot> allValidSlots=this.getAllValidMove(i, j, board, this.getPlayer());
                        if(allValidSlots!=null){ // If there are more moves possible
                            for(Slot child: allValidSlots){
                                v=Math.min(v,alpha_beta(depth-1, child.getX(),child.getY(), alpha, beta, true, new Board(board)));
                                beta=Math.min(beta, v);
                            }
                            if(beta <= alpha){
                                break;
                                }
                        }
                    }
                }
            }
        return beta;
        }
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////