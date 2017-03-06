/****************************************************************
*           Introduction To Artificial Intelligence             * 
*       CSCI 697         Program 1          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Monday February 2, 2017           *
****************************************************************/
package routeoptimization.Algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import routeoptimization.Visual.GraphicsPanel;
import routeoptimization.Visual.MainPanel;

/**
 *
 * @author Anwar
 */
public abstract class SearchAlgorithm{
    protected String source;                        // Source city 
    protected String destination;                   //Destination City
    protected Map<String, Node> destinationMap;     // Map to read city, destinations and distance to destinations
    protected Map<String, Integer> longsMap;        // Map to store City and it's longitude value
    protected ArrayList<String> visited;            // To keep track of cities visited
    protected int nodesGenerated;                   // To count on the number of nodes generated
    protected int routeDistance;                    // Gives total distance to be travelled/ which may not be optimal because of other factors(like traffic)
    protected boolean useHeuristics;                // Is checked, if the search is guided
    protected Node pathLastNode;                    // Store the last node from the graph(To find the path by back tracking)
    protected int nodesExpanded;                    // To keep count of nodes expanded
    protected int max_level;                        // For DFID, depth level
    protected ArrayList<Node> openList;             // The nodes that are opened
    protected ArrayList<String> closedList;         // Nodes that are closed
    protected GraphicsPanel graphics_panel;
    protected MainPanel main_panel;
    protected int sleepTime;
    public SearchAlgorithm(){
        source=destination=null;
        destinationMap=new TreeMap<>();
        longsMap=new TreeMap<>();
        visited=new ArrayList<>();
        nodesGenerated=routeDistance=nodesExpanded=max_level=0;
        useHeuristics=false;
        pathLastNode=null;
        openList=new ArrayList<>();
        closedList=new ArrayList<>();
        routeDistance=-1;
    }
    protected void displayStringList(ArrayList<String> ar){
        System.out.print("( ");
        ar.forEach((s) -> {
            System.out.print(s.toUpperCase()+", ");
        });
        System.out.println(")");
    }
    protected void displayNodeList(ArrayList<Node> ar){
        System.out.print("( ");
        ar.forEach((s) -> {
            System.out.print(s.getCityName().toUpperCase()+" ");
            if(s.getFValue() != -1) System.out.print(s.getFValue()+", ");
            if(s.getDepth() != -1) System.out.print(s.getDepth()+", ");
        });
        System.out.println(")");
    }
    protected boolean foundDestination(String city, String destination){
        if(city.equals(destination)){
            routeDistance=0;
            this.main_panel.jl_path.setText("");
            return true;
        }
        return false;
    }
    protected void showPath(Node n){
        if(n==null) return;
        if(n.getParent()==null){    // To skip printing the last ->
            System.out.print(n.getCityName().toUpperCase());
            this.main_panel.jl_path.setText(this.main_panel.jl_path.getText()+
                n.getCityName().toUpperCase());
            return;
        }
        this.graphics_panel.setRoad(Color.BLUE,n.getCityName(), n.getParent().getCityName());
        showPath(n.getParent());
        System.out.print("->"+n.getCityName().toUpperCase());
        this.main_panel.jl_path.setText(this.main_panel.jl_path.getText()+
                "->"+n.getCityName().toUpperCase());
    }
    protected abstract void findRoute();
    
    protected void validate(Node startNode, Node endNode) {
       if(startNode==null){
            System.out.println("Not a valid source!");
            System.exit(1);
        }
       if(endNode==null){
            System.out.println("Not a valid destination!");
            System.exit(1);
       }
    }
    // ArrayList has reference types, so simply calling remove will not work
    // Because newly generated nodes(children) are different from the ones that are present in the array
    protected void findAndRemoveNode(ArrayList<Node> openList, Node currNode) {
        for(int i=0;i<openList.size(); ++i){
            if(openList.get(i).getCityName().equals(currNode.getCityName())){
                openList.remove(i);
                return;
            }
        }
    }
    
    protected int calculateHValue(String source, String destination){
        int l1=longsMap.get(source);
        int l2=longsMap.get(destination);
        return Math.abs(l1-l2)*8;
    }
    protected int calculateGValue(String source, String destination){
        Node s=destinationMap.get(source);
        Map<String, Integer> d_map=s.getChildNodes();
        for(Map.Entry<String, Integer> e: d_map.entrySet()){
            if(e.getKey().equals(destination))
                return e.getValue();
        }
        return 1000;
    }
    public void showPath(){
        System.out.print("( ");
        this.showPath(pathLastNode);
        System.out.print(" )");
    }
    public int getRouteDistance(){
        findDistance(pathLastNode);
        if(routeDistance==-1){
            System.out.println("\nDestination Not Found!!");
            main_panel.jl_path.setText("Destination Not Found!!");
        }
        main_panel.jl_path_length.setText(String.valueOf(routeDistance));
        return routeDistance;//
    }
    public int getNodesExpanded(){
        return nodesExpanded;
    }
    public int getNodesGenerated(){
        return nodesGenerated;
    }
    private void findDistance(Node n){
        if(n==null) return;
        routeDistance+=getDistance(n.getParent(),n);
        findDistance(n.getParent());
    }
    private int getDistance(Node source, Node destination){
        if(source==null) return 0;
        Map<String, Integer> allChildrenNodes=source.getChildNodes();
        return allChildrenNodes.get(destination.getCityName());
    }

}
/////////////////////////  END OF FILE /////////////////////////////////