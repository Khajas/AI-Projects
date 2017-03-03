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

/**
 *
 * @author Anwar
 */
public class Slot {
    private String occupancy;
    private String preferredDirection; // Use by AI 
    private Slot northSlot, southSlot, eastSlot, westSlot;
    private int score;
    private final int posX, posY;
    private Slot preferredSlot;
    //Constructor
    public Slot(int x, int y, String occupancy,
            Slot northSlot, Slot southSlot, Slot eastSlot, Slot westSlot,
            String preferredDirection, int score, Slot preferredSlot) {
        this.posX=x;
        this.posY=y;
        this.occupancy = occupancy;
        this.northSlot = northSlot;
        this.southSlot = southSlot;
        this.eastSlot = eastSlot;
        this.westSlot = westSlot;
        this.score=score;
        this.preferredSlot=preferredSlot;
        this.preferredDirection=preferredDirection;
    }
    public Slot(Slot s){
        this(s.getX(), s.getY(), s.getOccupiedBy(),s.getNorthSlot(),
                s.getSouthSlot(), s.getEastSlot(), s.getWestSlot(),
                s.getPreferredDirection(), s.getScore(), s.getPreferredSlot());
    }
    // Setters
    public void setPreferredDirection(String dir){
        this.preferredDirection=dir;
    }
    public void setPreferredSlot(Slot s){
        this.preferredSlot=s;
    }
    public void setOccupiedBy(String occupancy) {
        this.occupancy = occupancy;
    }

    public void setNorthSlot(Slot northSlot) {
        this.northSlot = northSlot;
    }

    public void setSouthSlot(Slot southSlot) {
        this.southSlot = southSlot;
    }

    public void setEastSlot(Slot eastSlot) {
        this.eastSlot = eastSlot;
    }

    public void setWestSlot(Slot westSlot) {
        this.westSlot = westSlot;
    }
    
    public void setScore(int s){
        this.score=s;
    }

    // Getters
    public String getPreferredDirection(){
        return this.preferredDirection;
    }
    public Slot getPreferredSlot(){
        return this.preferredSlot;
    }    
    public String getOccupiedBy() {
        return occupancy;
    }

    public Slot getNorthSlot() {
        return northSlot;
    }

    public Slot getSouthSlot() {
        return southSlot;
    }

    public Slot getEastSlot() {
        return eastSlot;
    }

    public Slot getWestSlot() {
        return westSlot;
    }

    void setNeighbours(String occupancy, Slot north, Slot south, Slot east, Slot west) {
        this.occupancy=occupancy;
        this.northSlot=north;
        this.southSlot=south;
        this.eastSlot=east;
        this.westSlot=west;
    }

    void leaveSlot() {
        this.setOccupiedBy("free");
    }
    int getScore(){
        return this.score;
    }
    public int getX(){
        return this.posX;
    }
    public int getY(){
        return this.posY;
    }
}
/////////////////////////// END OF SOURCE FILE  ///////////////////////////////