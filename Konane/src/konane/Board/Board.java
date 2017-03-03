/****************************************************************
*           Introduction To Artificial Intelligence             * 
*   Title: Konane(Hawaiian Checkers, Alpha Beta Pruning)        *
*       CSCI 697         Program 2          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Thursday March 2, 2017            *
****************************************************************/
package konane.Board;

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
public class Board {
    private int rows, cols;
    static int board_no=0;
    private ArrayList<ArrayList<Slot>> board;
    private int myNum=0;
    // An empty board
    public Board(int rows, int cols){
        this.rows=rows;
        this.cols=cols;
        Slot north, south, east, west;
        this.board=new ArrayList<>();
        north=south=east=west=new Slot(-1,-1,"free",null, null, null, null,null, -100000, null);
        for(int i=0; i<rows; ++i){
            ArrayList<Slot> each_row=new ArrayList<>();
            for(int j=0; j<cols; ++j){
                Slot s=new Slot(i,j,"free",north, south, east, west,null,-100000,null);
                each_row.add(s);
            }
            this.board.add(each_row);
        }
        board_no++;
        myNum=board_no;
    }
    // Copy Constructor for Board
    public Board(Board brd){
        this(brd.getRows(), brd.getCols());
        this.copyMarbles(brd);
        if(false){
            System.out.println("New Board created!!!");
            this.printBoard();
        }
        board_no++;
        myNum=board_no;
    }
    private void copyMarbles(Board brd){
        for(int i=0; i < brd.getRows(); ++i){
            ArrayList<Slot> cp_row=new ArrayList<>();
            for(int j=0; j<brd.getCols(); ++j){
                Slot copy_s=new Slot(brd.getSlot(i, j));
                cp_row.add(copy_s);
            }
            this.getBoard().set(i,cp_row);
        }
    }
    
    // Intialize the board
    public void initBoard(){
        Slot north=null, south=null, east=null, west=null;
        for(int i=0; i<rows; ++i){
            for(int j=0; j<cols; ++j){
                Slot s=board.get(i).get(j);
                if(i!=0 ) north= board.get(i-1).get(j);
                if(i!=(rows-1)) south= board.get(i+1).get(j);
                if(j!=(cols-1)) east= board.get(i).get(j+1);
                if(j!=0) west= board.get(i).get(j-1);
                s.setNeighbours("free",north, south, east, west);
            }
        }
    }
    /** The following method place marbles, such that
     *  alternate row starts with white or black marbles
     *  the continue
     */
    public void placeMarbles(){
        boolean oddRow=true;   // 1 row is odd
        for(ArrayList<Slot> row: board){
            boolean marbleWhite=true;
            if(!oddRow) marbleWhite=false;
            for(Slot s: row){
                if(marbleWhite)
                    s.setOccupiedBy("white");
                else s.setOccupiedBy("black");
                marbleWhite=!marbleWhite; // Next should be black marble
            }
            oddRow=!oddRow; // Next row should be an even row
        }
    }
    
    
    // Occupy a slot
    public void occupySlot(String occupyBy, Slot s){
        s.setOccupiedBy(occupyBy);
    }
    
    // Leave a slot
    public void leaveSlot(Slot s){
        s.leaveSlot();
    }
    
    public Slot getSlot(int row, int col){
        if(row <this.rows & col < this.cols & row >= 0 & col >=0)
            return board.get(row).get(col);
        return null;
    }
    public void removeTwoFromMiddle(){
        Slot middleSlot=board.get(rows/2-1).get(cols/2);
        this.freeSlot(middleSlot);
        this.freeSlot(middleSlot.getWestSlot());
    }
    public void removeTwoFromCorner(){
        Slot cornerSlot=board.get(rows-1).get(cols-1);
        this.freeSlot(cornerSlot);
        this.freeSlot(cornerSlot.getWestSlot());
    }
    public void freeSlot(Slot s){
        s.setOccupiedBy("free");
    }
    private void freeSlot(int rowNum, int colNum){
        if(rowNum<0 || rowNum >= rows
                || colNum < 0 || colNum >= cols)
            return;
        Slot s=board.get(rowNum).get(colNum);
        this.freeSlot(s);
    }
    
    // Print board
    public final void printBoard(){
        System.out.println("Board Status("+this.rows+"X"+this.cols+") & board #"+this.myNum);
        System.out.print("   ");
        for(char c='A'; c < 'A'+cols; ++c) System.out.print(c+" ");
        System.out.println();
        for(int i=0; i<rows; ++i){
            for(int j=0; j<cols; ++j){
                if(j==0) System.out.print(String.format("%02d ",i+1));
                String occupiedBy=board.get(i).get(j).getOccupiedBy();
                if(occupiedBy.equals("white"))
                        System.out.print("O ");
                else if(occupiedBy.equals("black"))
                        System.out.print("X ");
                else System.out.print("  ");
                }
            System.out.println();
            }
        System.out.println();
    }
    // Setters
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setBoard(ArrayList<ArrayList<Slot>> board) {
        this.board = board;
    }
    
    // Getters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ArrayList<ArrayList<Slot>> getBoard() {
        return board;
    }
    public void demoRemove(){
        Slot cornerSlot=board.get(1).get(3);
        this.freeSlot(cornerSlot);
        cornerSlot=board.get(2).get(1);
        this.freeSlot(cornerSlot);
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////