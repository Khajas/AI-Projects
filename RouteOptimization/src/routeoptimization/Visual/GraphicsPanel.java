/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization.Visual;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import routeoptimization.Algorithms.AStar;
import routeoptimization.Algorithms.BFS;
import routeoptimization.Algorithms.DFID;
import routeoptimization.Algorithms.DFS;
import routeoptimization.Algorithms.H_ONLY;
import routeoptimization.Algorithms.Node;

/**
 *
 * @author Anwar
 */

public class GraphicsPanel extends JPanel implements Runnable{
    ArrayList<City> citiesLoc;
    Graphics2D localGraphics;
    ArrayList<Road> roads;
    public boolean initCitiesFlag;
    public MainPanel mp;
    public Map<String, Node> distanceMap;
    public String source, destination;
    public String algorithmName;
    public Map<String, Integer> longsMap;
    public int sleepTime;
    public volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock;
    GraphicsPanel(Map<String, Integer> longsMap){
        super(new BorderLayout());
        super.setPreferredSize(new Dimension(500,500));
        super.setVisible(true);
        super.setOpaque(false);
        citiesLoc=new ArrayList<>();
        this.assignLocs();
        this.initCitiesFlag=true;
        roads=new ArrayList<>();
        this.makeRoads();
        distanceMap=new HashMap<>();
        this.longsMap=longsMap;
        super.add(new JLabel("French Cities"), BorderLayout.WEST);
        this.pauseLock=new Object();
    }
    public void highLightCity(String name){
        for(City c: citiesLoc){
            if(c.getCityName().equals(name)){
                c.setShape("enlarge");
            }
            else c.setShape("normal");
        }
        this.repaint();
//        this.localGraphics.setColor(Color.BLACK);
    }
    public void refreshMap(){
        this.initCitiesFlag=true;
        for(Road r: roads){
            r.setColor(Color.BLACK);
            r.setMark(true);
        }
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        localGraphics=(Graphics2D)g;
        localGraphics.setStroke(new BasicStroke(3));
        if(this.initCitiesFlag){
            citiesLoc.forEach((c) -> {
                drawCity(localGraphics,c.getLatt(),c.getLng(),
                        c);      // Calais
            });
            this.initCitiesFlag=true;
        }
        for(Road r: roads){
            if(r.getMark()){
                drawRoad(r.getColor(), localGraphics, r.getSourceCity(), r.getDestinationCity());
            }
        }
    }
    private void drawRoad(Color clr,Graphics2D g, String source, String destination){
        City c1=null, c2=null;
        for(City c: citiesLoc){
            if(c.getCityName().equals(source)) c1=c;
            if(c.getCityName().equals(destination)) c2=c;
        }
        g.setColor(clr);
        if(c1!=null && c2!=null){
            g.drawLine(c1.getLatt(), c1.getLng(), c2.getLatt(), c2.getLng());
        }
        g.setColor(Color.BLUE);
    }
    
    private void drawCity(Graphics g,int x, int y, City c){
        g.setColor(Color.BLACK); //g.drawLine(x, y, 100, 100);
        int a_axis=0;
        int b_axis=0;
        if(c.getShape().equals("enlarge")
                | c.getCityName().equals(source)
                | c.getCityName().equals(destination)){
            a_axis=15;
            b_axis=15;
        }
        else if(c.getShape().equals("normal")){
            a_axis=5;
            b_axis=5;
        }
        g.drawOval(x,y,a_axis,b_axis);
    }
    public void setRoad(Color c,String source, String destination){
        for(Road r: roads){
            if(r.getDestinationCity().equals(source) &&
                    r.getSourceCity().equals(destination)){
                r.setMark(true);
                r.setColor(c);
            }
            if(r.getDestinationCity().equals(destination) &&
                    r.getSourceCity().equals(source)){
                r.setMark(true);
                r.setColor(c);
            }
        }
    }
    private void assignLocs(){
        citiesLoc.add(new City("calais",220,5));      // Calais
        citiesLoc.add(new City("caen",125,125));    // Caen
        citiesLoc.add(new City("rennes",90,230));     // Rennes
        citiesLoc.add(new City("brest",0,215));      // Brest
        citiesLoc.add(new City("nantes",100,315));    // Nantes, pull down y->325
        citiesLoc.add(new City("bordeaux",120,400));    //Bordeaux
        citiesLoc.add(new City("toulouse",175,475));    // Toulose
        citiesLoc.add(new City("limoges",200,350));    // Limoges
        citiesLoc.add(new City("paris",240,150));    // Paris
        citiesLoc.add(new City("nancy",350,125));    // Nancy
        citiesLoc.add(new City("strasbourg",425,115));    // Struasbourg
        citiesLoc.add(new City("dijon",320,240));    // Dijon
        citiesLoc.add(new City("lyon",310,320));    // Lyon
        citiesLoc.add(new City("grenoble",400,370));    // Grenoble
        citiesLoc.add(new City("avignon",300,430));    // Avignon
        citiesLoc.add(new City("montpellier",270,480));    // Montepellier
        citiesLoc.add(new City("marseille",375,470));    // Marseille
        citiesLoc.add(new City("nice",450,450));    // Nice
    }
    private void makeRoads(){
        roads.add(new Road(true,Color.BLACK,"calais","caen"));
        roads.add(new Road(true,Color.BLACK,"calais","paris"));
        roads.add(new Road(true,Color.BLACK,"calais","nancy"));
        roads.add(new Road(true,Color.BLACK,"caen","paris"));
        roads.add(new Road(true,Color.BLACK,"paris","nancy"));
        roads.add(new Road(true,Color.BLACK,"nancy","strasbourg"));
        roads.add(new Road(true,Color.BLACK,"strasbourg","dijon"));
        roads.add(new Road(true,Color.BLACK,"nancy","dijon"));
        roads.add(new Road(true,Color.BLACK,"paris","dijon"));
        roads.add(new Road(true,Color.BLACK,"paris","rennes"));
        roads.add(new Road(true,Color.BLACK,"brest","rennes"));
        roads.add(new Road(true,Color.BLACK,"paris","limoges"));
        roads.add(new Road(true,Color.BLACK,"dijon","lyon"));
        roads.add(new Road(true,Color.BLACK,"rennes","nantes"));
        roads.add(new Road(true,Color.BLACK,"nantes","limoges"));
        roads.add(new Road(true,Color.BLACK,"limoges","lyon"));
        roads.add(new Road(true,Color.BLACK,"nantes","bordeaux"));
        roads.add(new Road(true,Color.BLACK,"bordeaux","limoges"));
        roads.add(new Road(true,Color.BLACK,"bordeaux","toulouse"));
        roads.add(new Road(true,Color.BLACK,"limoges","toulouse"));
        roads.add(new Road(true,Color.BLACK,"lyon","avignon"));
        roads.add(new Road(true,Color.BLACK,"lyon","grenoble"));
        roads.add(new Road(true,Color.BLACK,"grenoble","avignon"));
        roads.add(new Road(true,Color.BLACK,"avignon","marseille"));
        roads.add(new Road(true,Color.BLACK,"marseille","nice"));
        roads.add(new Road(true,Color.BLACK,"toulouse","montpellier"));
        roads.add(new Road(true,Color.BLACK,"montpellier","avignon"));
        roads.add(new Road(true,Color.BLACK,"rennes","caen"));
    }

    @Override
    public void run() {
        running = true;
        paused = false;
        this.mp.jl_path_length.setText("Calculating!");
        if(algorithmName.equals("BFS")){
            System.out.println("\nBFS");
            BFS routeBFS=new BFS(this,mp,source,destination, distanceMap);
            routeBFS.findRoute();
            System.out.println("\nPath-length: "+routeBFS.getRouteDistance()+"kms");
            System.out.println(routeBFS.getNodesExpanded()+" nodes Expanded");
        }
        else if(algorithmName.equals("DFS")){
            System.out.println("\nDFS");
            DFS routeDFS=new DFS(this,mp,source, destination, distanceMap);
            routeDFS.findRoute();
            System.out.println("\nPath-length: "+routeDFS.getRouteDistance()+"kms");
            System.out.println(routeDFS.getNodesExpanded()+" nodes Expanded");
        }
        else if(algorithmName.equals("DFID")){
            System.out.println("\nDFID with Level = 3");
            DFID routeDFID=new DFID(this, mp,source, destination, 3, distanceMap); // Level =3
            routeDFID.findRoute();
            System.out.println("\nPath-length: "+routeDFID.getRouteDistance()+"kms");
            System.out.println(routeDFID.getNodesExpanded()+" nodes Expanded");
        }
        else if(algorithmName.equals("H_ONLY")){
            System.out.println("\nH_ONLY");
            H_ONLY routeH_Only=new H_ONLY(this,mp,source, destination, distanceMap, longsMap);
            routeH_Only.findRoute();        
            System.out.println("\nPath-length: "+routeH_Only.getRouteDistance()+"kms");
            System.out.println(routeH_Only.getNodesGenerated()+" nodes Generated");            
        }
        else if(algorithmName.equals("AStar(H=0)")){
            System.out.println("\nA* with H=0");
            AStar routeAStarH0=new AStar(this,mp,source, destination, distanceMap, longsMap, false);
            routeAStarH0.findRoute();
            System.out.print("\nAStar Solution Path with h=0: ");         routeAStarH0.showPath();
            System.out.println("\nPath-length: "+routeAStarH0.getRouteDistance()+"kms");
            System.out.println(routeAStarH0.getNodesGenerated()+" nodes Generated");
        }
        else if(algorithmName.equals("AStar(H=E-W Dist)")){
            System.out.println("\nA* with H=EAST-WEST DISTANCE");
            AStar routeAStar=new AStar(this, mp,source, destination, distanceMap, longsMap, true);
            routeAStar.findRoute();
            System.out.print("\nA-start-search Solution: ");         routeAStar.showPath();
            System.out.println("\nPath-length: "+routeAStar.getRouteDistance()+"kms");
            System.out.println(routeAStar.getNodesGenerated()+" nodes Generated");
        }
    }
    /**--------------------------------------------------------
     * Method sleepThread():
     *  The following method makes the thread to sleep for the specified time.
     * Time is set by the radio button sets(fast, medium, slow).
     *-------------------------------------------------------*/
    public void sleepThread(){
        try{
            Thread.sleep(sleepTime);
            this.repaint();
        }
        catch(InterruptedException ie){
            System.out.println("Error! : "+ie.getMessage());
        }
    }
        /**--------------------------------------------------------
     * Method threadState():
     *  The following is called for each operation on the array list items,
     * like, before swapping the values to check the state of the thread.
     *-------------------------------------------------------*/
    public void threadState(){
        while(running){
            synchronized (pauseLock) {
                if (!running){         // may have changed while waiting to
                    break;            // synchronize on pauseLock
                }
                if (paused){
                    try {
                        pauseLock.wait(); // will cause this Thread to block until 
                                          // another thread calls pauseLock.notifyAll()
                                          // Note that calling wait() will 
                                          // relinquish the synchronized lock that this 
                                          // thread holds on pauseLock so another thread
                                          // can acquire the lock to call notifyAll()
                                          // (link with explanation below this code)
                    } catch (InterruptedException ex) {
                        System.out.println("Error! : "+ex.getMessage());
                        break;
                    }
                    if (!running){ // running might have changed since we paused
                        break;
                    }
                }
            }
            return; //To exit out of the loop. This is must.
        }
    }
    /**--------------------------------------------------------
     * Method stop():
     *  The following method is called when the stop button is pressed.
     *  It sets the predicate running to false, and interrupts the thread.
     *-------------------------------------------------------*/
    public void stop() {
        running = false;
        Thread.currentThread().interrupt();
    }
    /**--------------------------------------------------------
     * Method pause():
     *  The following method pauses a thread.
     *-------------------------------------------------------*/
    public void pause() {
        // you may want to throw an IllegalStateException if !running
        paused = true;
    }
    /**--------------------------------------------------------
     * Method resumes():
     *  The following method resumes a thread, releases the lock.
     *-------------------------------------------------------*/
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }
}

class Road{
    private boolean markRoad;
    private final String source;
    private final String destination;
    private Color clr;
    Road(boolean mark, Color clr, String source, String destination){
        this.markRoad=mark;
        this.source=source;
        this.destination=destination;
        this.clr=clr;
    }
    void setColor(Color c){
        this.clr=c;
    }
    Color getColor(){
        return clr;
    }
    boolean getMark(){
        return markRoad;
    }
    void setMark(boolean b){
        markRoad=b;
    }
    String getSourceCity(){
        return this.source;
    }
    String getDestinationCity(){
        return this.destination;
    }
}

class City{
    private final String name;
    private final int lng;
    private final int latt;
    private String shape;
    City(String name, int latt, int lng){
        this.name=name;
        this.lng=lng;
        this.latt=latt;
        this.shape="normal";
    }
    void setShape(String shape){
        this.shape=shape;
    }
    String getShape(){
        return this.shape;
    }
    String getCityName(){
        return this.name;
    }
    int getLng(){
        return this.lng;
    }
    int getLatt(){
        return this.latt;
    }
}