/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konane;

/**
 *
 * @author Anwar
 */
public class Slot {
    private String occupancy;
    private Slot northSlot, southSlot, eastSlot, westSlot;
    
    //Constructor
    public Slot(String occupancy, Slot northSlot, Slot southSlot, Slot eastSlot, Slot westSlot) {
        this.occupancy = occupancy;
        this.northSlot = northSlot;
        this.southSlot = southSlot;
        this.eastSlot = eastSlot;
        this.westSlot = westSlot;
    }
    
    // Setters
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
    

    // Getters
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
}
