/****************************************************************
*           Introduction To Artificial Intelligence             * 
*       CSCI 697         Program 1          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Monday February 2, 2017           *
****************************************************************/
package routeoptimization.Algorithms;
// The following class is to implement A Star Search Algorithm
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
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
public class AStar extends SearchAlgorithm{
    //Constructor
    public AStar(GraphicsPanel gp, MainPanel mp,String s, String d, Map<String, Node> dm, Map<String, Integer> lm, boolean useHeuristics){
        source=s; destination=d; longsMap=lm; destinationMap=dm;
        this.useHeuristics=useHeuristics;
        super.graphics_panel=gp;
        super.main_panel=mp;
    }
    @Override
    public void findRoute(){
        Node startNode=destinationMap.get(source);      // Find the source node
        Node endNode=destinationMap.get(destination);   // and destination node
        validate(startNode, endNode);                   // Validate the source and destination nodes
        int h=0;                                        // h value for the source node 
        if(useHeuristics) h=calculateHValue(source, destination);   // If we were to use heuristics, then calculate h value
        int g=0;
        startNode.setHValue(h);                         // Set the f, g, h, values
        startNode.setGValue(g);
        startNode.setFValue(g+h);
        openList.add(startNode);                        // Add the node to the openList
        while(!openList.isEmpty()){                     // As long as the list is not empty and destination is not found
            Node n= openList.get(0);                    // Get the most preferred node
            System.out.print("\nExpanding "+n.getCityName().toUpperCase()+" f="+n.getFValue()+", g="+n.getGValue()+", h="+n.getHValue());
            if(!n.getCityName().equals(source)){
                graphics_panel.sleepThread();
                graphics_panel.threadState();
                this.graphics_panel.setRoad(Color.RED,n.getCityName(),
                n.getParent().getCityName());
            }
            if(foundDestination(n.getCityName(), destination)){
                pathLastNode=n;                         // If the destination node is found, set the pathLastNode(for path & distance finding)
                return;                                 //  and return
            }
            visited.add(n.getCityName());               // Mark the current node as visited
            System.out.print("\nChildren are "); n.readChildren();      // Read current node's children
            Map<String, Integer> cm=n.getChildNodes();
            for(Entry<String, Integer> e: cm.entrySet()){
                Node ch=new Node(destinationMap.get(e.getKey()), n);                        // Each new child
                int g_ch=calculateGValue(n.getCityName(),ch.getCityName())+n.getGValue();   // Get the G value
                int h_ch=0;                                                                 // Default
                if(useHeuristics) h_ch=calculateHValue(ch.getCityName(), destination);      // Calculate h if we're to use heuristics
                int f_ch=g_ch+h_ch;                                                         //Calucate and set f,g,h 
                ch.setHValue(h_ch);
                ch.setGValue(g_ch);
                ch.setFValue(f_ch);
                if(!visited.contains(ch.getCityName())){            // If the node is never visited
                    ++nodesGenerated;                               // Increment the nodes generated
                    main_panel.jl_nodes_expanded.setText(String.valueOf(nodesGenerated)+" Generated");
                    openList.add(ch);                               // Add it to the open list
                    visited.add(ch.getCityName());                  // and mark visited
                }
                else{                                                               
                    // check the current f value and new f value and update if needed
                    Node old_ch=getNodeByName(openList, ch.getCityName());  // Else get the old node
                    if(old_ch!=null){                                        
                        if(old_ch.getFValue() > ch.getFValue()){            // Check if the current F value is lower than old node's F value
                            System.out.println("***Revaluing open node "+ch.getCityName()+" from "+old_ch.getFValue()+" to "+ch.getFValue());
                            replaceWithNewNode(old_ch, ch, openList);       // If so, then update the F value
                        }
                    }
                }
            }
            openList.remove(0);                                     // Remove the node from the open list
            closedList.add(n.getCityName()+" "+n.getFValue());      // and add it to the close list
            Collections.sort(openList, new SortFValues());          // Sort the open list based on F values
            System.out.print("Open List is ");displayNodeList(openList);        // Display the open list
            System.out.print("Closed List is ");displayStringList(closedList);  // and close list
        }
    }
    private Node getNodeByName(ArrayList<Node> nodesList, String nodeName){
        for(Node n: nodesList){                     // Loop through the list
            if(n.getCityName().equals(nodeName))    // locate the node with the nodeName
                return n;                           // and return it
        }
        return null;                                // If it's not found, return null
    }
    private void replaceWithNewNode(Node old_ch, Node ch, ArrayList<Node> ar){
        for(int i=0; i < ar.size(); ++i){                               // Loop through the list
            if(ar.get(i).getCityName().equals(old_ch.getCityName())){   // locate the old node
                ar.set(i,ch);                                           //  and replace it with new node          
                return;                                                 // return
            }
        }
    }
}
//////////////////  END OF FILE /////////////////////////////////