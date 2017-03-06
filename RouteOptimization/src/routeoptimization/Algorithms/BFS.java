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
import static java.lang.Thread.sleep;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import routeoptimization.Visual.GraphicsPanel;
import routeoptimization.Visual.MainPanel;

/**
 *
 * @author Anwar
 */
public class BFS extends SearchAlgorithm{
    // Constructor for BFS
    public BFS(GraphicsPanel gp, MainPanel mp, String s, String d, Map<String, Node> hm){
        super.source=s; super.destination=d;    // Set the destination and source
        super.destinationMap=new TreeMap<>(hm); // Set the destinationMap
        super.graphics_panel=gp;
        super.main_panel=mp;
    }
    @Override
    public void findRoute(){
        Node currNode=destinationMap.get(source);   // Get the node for source city
        super.validate(currNode, destinationMap.get(destination));  // Validate the node, method validate() returns if either source or destination is invalid
        super.openList.add(currNode);   // Add the currentNode to openList
        while(!openList.isEmpty()){ // As long there are nodes and destination is not found
            currNode=openList.get(0);   // Get the top most node
            System.out.println("\nExpanding: "+currNode.getCityName().toUpperCase());
            if(!currNode.getCityName().equals(source)){
                this.graphics_panel.setRoad(Color.RED,currNode.getCityName(),
                currNode.getParent().getCityName());
                graphics_panel.sleepThread();
                graphics_panel.threadState();
                if(!graphics_panel.running) return;
            }
            if(foundDestination(currNode.getCityName(), destination)){  // Check if it's our desintation
                System.out.print("Breadth First Solution Path: ");  // If so, then display the path
                pathLastNode=currNode;  // Set the current node as path's last node
                showPath(currNode);     // For backtracking and showing the optimal path
                return;
            }
            System.out.print("Children Are: "); currNode.readChildren();    // Read the children/destinations from current node
            if(!visited.contains(currNode.getCityName()))   // If the current node is not visited or expanded
                visited.add(currNode.getCityName());    // add it to the visited list 
            nodesExpanded++;                            // Increment the nodesExpanded variable
            main_panel.jl_nodes_expanded.setText(String.valueOf(nodesExpanded));
            Map<String, Integer> curr_node_childern=new TreeMap<>(currNode.getChildNodes()); // Get the children of current node
            System.out.print("New Children are: ( ");   // And finally show the children from current node
            for(Entry<String, Integer> e: curr_node_childern.entrySet()){
                Node cityNode=destinationMap.get(e.getKey());   // For each children get their node(full info)
                Node childNode=new Node(cityNode,currNode); // Create the child node such that new Child node's parent should be currNode
                if(!visited.contains(childNode.getCityName())){ // If this child is never visited
                    System.out.print(e.getKey().toUpperCase()+" "); // Then it's a new child
                    openList.add(childNode);        // Add it to the openList 
                    visited.add(e.getKey());        // and finally mark it as visited
                }
            }
            System.out.println(" )");
            closedList.add(openList.get(0).getCityName()); // The current is expanded(Visited) and it's been closed, so add it to closed list
            openList.remove(0); // and remove it from openList
            System.out.print("Open List is ");  displayNodeList(openList);    //Finally display the openList
            System.out.print("Closed List is "); displayStringList(closedList); // and closed list
        }
    }
}
//////////////////  END OF FILE /////////////////////////////////