/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization.Visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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

public final class GraphicsPanel extends JPanel implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<City> citiesLoc;
    Graphics2D localGraphics;
    ArrayList<Road> roads;
    public boolean initCitiesFlag;
    public MainPanel mp;
    public Map<String, Node> distanceMap;
    public String source, destination;
    public String algorithmName;
    public Map<String, Integer> longsMap;
    public int sleepTime=100;
    public boolean soundON=true;
    public volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock;
    private JLabel cityLabel;
    GraphicsPanel(Map<String, Integer> longsMap){
        super(null);
        super.setPreferredSize(new Dimension(500,500));
        super.setVisible(true);
        super.setOpaque(false);
        citiesLoc=new ArrayList<>();
        this.assignLocs();
        this.initCitiesFlag=true;
        roads=new ArrayList<>();
        this.makeRoads();
        cityLabel=new JLabel("");
        distanceMap=new HashMap<>();
        this.longsMap=longsMap;
        this.pauseLock=new Object();
        this.addCityNameLabels();
    }
    public void addCityNameLabels(){
        cityLabel=new JLabel("Map of french Cities");
        this.add(cityLabel);
        Dimension size = cityLabel.getPreferredSize();
        cityLabel.setBounds(0, 0, size.width, size.height);
        for(City c: citiesLoc){
            cityLabel=new JLabel(c.getCityName());
            this.add(cityLabel);
            size = cityLabel.getPreferredSize();
            cityLabel.setBounds(c.getLatt()+7, c.getLng()+7, size.width, size.height);
        }
    }
    
    public void highLightCity(String name){
        for(City c: citiesLoc){
            if(c.getCityName().equals(name)){
                c.setColor(Color.BLUE);
                c.setShape("enlarge");
            }
            else{
                c.setColor(Color.BLACK);
                c.setShape("normal");
            }
        }
        this.repaint();
    }
    public void colorCity(String name, Color clr){
        for(City c: citiesLoc){
            if(name.equals(c.getCityName()))
                c.setColor(clr);
        }
    }
    public void refreshMap(){
        this.initCitiesFlag=true;
        for(Road r: roads){
            r.setColor(Color.BLACK);
            r.setMark(true);
        }
        for(City c: citiesLoc){
            c.setColor(Color.BLACK);
        }
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        localGraphics=(Graphics2D)g;
        localGraphics.setStroke(new BasicStroke(4));
//        this.sleepThread();
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
    }
    
    private void drawCity(Graphics g,int x, int y, City c){
        g.setColor(Color.BLACK); //g.drawLine(x, y, 100, 100);
        int a_axis=0;
        int b_axis=0;
        if(c.getShape().equals("enlarge")
                | c.getCityName().equals(source)
                | c.getCityName().equals(destination)){
            a_axis=20;
            b_axis=20;
        }
        else if(c.getShape().equals("normal")){
            a_axis=10;
            b_axis=10;
        }
        g.setColor(c.getColor());
        g.drawOval(x,y,a_axis,b_axis);
    }
    public void setRoad(Color c,String source, String destination){
        roads.stream().map((r) -> {
            if(r.getDestinationCity().equals(source) &&
                    r.getSourceCity().equals(destination)){
                r.setMark(true);
                r.setColor(c);
            }
            return r;
        }).filter((r) -> (r.getDestinationCity().equals(destination) &&
                r.getSourceCity().equals(source))).map((r) -> {
                    r.setMark(true);
            return r;
        }).forEachOrdered((r) -> {
            r.setColor(c);
        });
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
        }else if(algorithmName.equals("DFS")){
                System.out.println("\nDFS");
                DFS routeDFS=new DFS(this,mp,source, destination, distanceMap);
                routeDFS.findRoute();
                System.out.println("\nPath-length: "+routeDFS.getRouteDistance()+"kms");
                System.out.println(routeDFS.getNodesExpanded()+" nodes Expanded");
        }else if(algorithmName.equals("DFID")){
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
                System.out.print("\nAStar Solution Path with h=0: ");
                routeAStarH0.showPath();
                System.out.println("\nPath-length: "+routeAStarH0.getRouteDistance()+"kms");
                System.out.println(routeAStarH0.getNodesGenerated()+" nodes Generated");
        }
        else if(algorithmName.equals("AStar(H=E-W Dist)")){
                System.out.println("\nA* with H=EAST-WEST DISTANCE");
                AStar routeAStar=new AStar(this, mp,source, destination, distanceMap, longsMap, true);
                routeAStar.findRoute();
                System.out.print("\nA-start-search Solution: ");
                routeAStar.showPath();
                System.out.println("\nPath-length: "+routeAStar.getRouteDistance()+"kms");
                System.out.println(routeAStar.getNodesGenerated()+" nodes Generated");
        }
    }
    public void playSound(String path){
        if(soundON)
            try {
                File yourFile = new File(path);
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;
                stream = AudioSystem.getAudioInputStream(yourFile);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }
    /**--------------------------------------------------------
     * Method sleepThread():
     *  The following method makes the thread to sleep for the specified time.
     * Time is set by the radio button sets(fast, medium, slow).
     *-------------------------------------------------------*/
    public void sleepThread(){
        this.playSound("resources/Sounds/Search.wav");
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
            running=true;
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
    private Color clr;
    City(String name, int latt, int lng){
        this.name=name;
        this.lng=lng;
        this.latt=latt;
        this.shape="normal";
        this.clr=Color.BLACK;
    }
    void setColor(Color clr){
        this.clr=clr;
    }
    Color getColor(){
        return this.clr;
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