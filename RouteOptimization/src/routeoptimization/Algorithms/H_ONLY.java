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
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import routeoptimization.Visual.GraphicsPanel;
import routeoptimization.Visual.MainPanel;

/**
 *
 * @author Anwar
 */
public class H_ONLY extends SearchAlgorithm{
    public H_ONLY(GraphicsPanel gp, MainPanel mp,String s, String d, Map<String, Node> dm, Map<String, Integer> lm){
        source=s; destination=d;            // Set the source and destination 
        longsMap=lm; destinationMap=dm;     // Set the map of cities and longitudes
        super.graphics_panel=gp;
        super.main_panel=mp;
    }
    @Override
    public void findRoute(){
        Node startNode=destinationMap.get(source);          // Set the start and end node
        Node endNode=destinationMap.get(destination);
        validate(startNode, endNode);                       // Validate the start and end
        int h=calculateHValue(source, destination);         // Calcuate the H value
        startNode.setHValue(h);
        startNode.setGValue(0);
        startNode.setFValue(h);
        openList.add(startNode);                            // Add the start node to open list
        while(!openList.isEmpty()){
            Node currNode= openList.get(0);                        // Get the most preferred node
            h=currNode.getHValue();                                // Get the h value
            int g=0;
            int f=g+h;
            System.out.print("\nExpanding "+currNode.getCityName().toUpperCase()+" f="+f+", g="+g+", h="+h);
            if(!currNode.getCityName().equals(source)){
                this.graphics_panel.setRoad(Color.RED,currNode.getCityName(),
                currNode.getParent().getCityName());
                graphics_panel.sleepThread();
                graphics_panel.threadState();
            }
            if(foundDestination(currNode.getCityName(), destination)){
                System.out.print("\nH_ONLY Solution Path: "); 
                System.out.print("( "); 
                showPath(currNode); System.out.print(" )");
                pathLastNode=currNode;                         // Once the route is found display the route and update the pathLastNode
                return;
            }
            visited.add(currNode.getCityName());                               // Add the current node to the visited list
            System.out.print("\nChildren are "); currNode.readChildren();      // Read all the children 
            Map<String, Integer> allChildNodes=currNode.getChildNodes();                  // Get the map of child nodes
            for(Entry<String, Integer> e: allChildNodes.entrySet()){
                Node childNode=new Node(destinationMap.get(e.getKey()), currNode);    // For each new child, set the parent as current node
                if(!visited.contains(childNode.getCityName())){                 // If the child node is not visited
                    ++nodesGenerated;
                    main_panel.jl_nodes_expanded.setText(String.valueOf(nodesGenerated)+" Generated");// Increment the nodes generated
                    int h_ch=calculateHValue(childNode.getCityName(), destination); // Calculate the h value
                    childNode.setHValue(h_ch);
                    childNode.setFValue(h_ch);
                    openList.add(childNode);                                    // Add it to the open list
                    visited.add(childNode.getCityName());                       // and mark it as visited
                }
            }
            openList.remove(0);                                                 // Remove the current node
            closedList.add(currNode.getCityName()+" "+currNode.getHValue());    // add it to the closed list
            Collections.sort(openList, new SortHValues());                      // sort the open list
            System.out.print("Open List is "); displayNodeList(openList);       // display open and closed lists
            System.out.print("Closed List is ");displayStringList(closedList); 
        }
    }
}
//////////////////  END OF FILE /////////////////////////////////