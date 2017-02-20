/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konane;

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
public class Board {
    private int rows, cols;
    private ArrayList<ArrayList<Slot>> board;
    // An empty board
    Board(int rows, int cols){
        this.rows=rows;
        this.cols=cols;
        Slot north, south, east, west;
        board=new ArrayList<>();
        north=south=east=west=new Slot("free",null, null, null, null);
        for(int i=0; i<rows; ++i){
            ArrayList<Slot> each_row=new ArrayList<>();
            for(int j=0; j<cols; ++j){
                Slot s=new Slot("free",north, south, east, west);
                each_row.add(s);
            }
            board.add(each_row);
        }
    }

    Board() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // Intialize the board
    void initBoard(){
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
    void placeMarbles(){
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
        if(row <this.rows & col < this.cols)
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
    private void freeSlot(Slot s){
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
    void printBoard(){
        System.out.println("Board Status("+this.rows+"X"+this.cols+")");
        System.out.print("   ");
        for(char c='A'; c < 'A'+cols; ++c) System.out.print(c+" ");
        System.out.println();
        for(int i=0; i<rows; ++i){
            for(int j=0; j<cols; ++j){
                if(j==0) System.out.print(String.format("%02d ",i+1));
                String occupiedBy=board.get(i).get(j).getOccupiedBy();
                switch (occupiedBy) {
                    case "white":
                        System.out.print("O ");
                        break;
                    case "black":
                        System.out.print("X ");
                        break;
                    default:
                        System.out.print("  ");
                        break;
                }
            }
            System.out.println();
        }
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
}