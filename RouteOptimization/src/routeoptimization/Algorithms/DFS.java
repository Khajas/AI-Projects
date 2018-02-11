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
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import routeoptimization.Visual.GraphicsPanel;
import routeoptimization.Visual.MainPanel;

/**
 *
 * @author Anwar
 */

// Constructor for DFS
public class DFS extends SearchAlgorithm{
    public DFS(GraphicsPanel gp, MainPanel mp,String s, String d, Map<String, Node> hm){
        source=s; destination=d;            // Set source and destination strings
        destinationMap=new TreeMap<>(hm);   // Set the destination map
        super.graphics_panel=gp;
        super.main_panel=mp;
    }
    @Override
    public void findRoute(){
        Node currNode=destinationMap.get(source);               // Get the source city info(node)
        Node destinationNode=destinationMap.get(destination);   // Get the desintation city info(node)
        super.validate(currNode, destinationNode);              // Validate them, method returns if any of them is invalid
        Stack<Node> nodesStack=new Stack<>();                   // Stack of nodes(city that needs to be expanded)
        nodesStack.push(currNode);                              // Push the current city to the stack
        while(!nodesStack.isEmpty()){                           // As long there are more cities to explore and destination is not found
            currNode=nodesStack.pop();                          // Pop the top most city from the stack
            System.out.println("\nExpanding: "+currNode.getCityName().toUpperCase());
            if(!currNode.getCityName().equals(source)){
                this.graphics_panel.setRoad(Color.RED,currNode.getCityName(),
                currNode.getParent().getCityName());
                graphics_panel.sleepThread();
                graphics_panel.threadState();
                if(!graphics_panel.running) return;
            }
            if(foundDestination(currNode.getCityName(), destination)){  // Check if it's the destination that we're looking for
                this.graphics_panel.colorCity(openList.get(0).getCityName(), Color.CYAN);
                System.out.print("Depath First Search Solution: "); // If so
                pathLastNode=currNode;                          // mark the last node for back tracking
                showPath(currNode);                             // show the solution path
                return;                                         // and return
            }
            System.out.print("Children Are: "); currNode.readChildren();    // Else we need to explore more, check out the children of current node
            if(!visited.contains(currNode.getCityName())){      // If the current node is not visited
                visited.add(currNode.getCityName());            // Add it to the visited list
            }
            nodesExpanded++;
            main_panel.jl_nodes_expanded.setText(String.valueOf(nodesExpanded));// Increment the nodes expanded, coz we're expanding the current node
            Map<String, Integer> allChildrenNodes=new TreeMap<>(currNode.getChildNodes()); // Get the map of current node's children
            Stack<Node> childNodeStack=new Stack<>();                                      // Have the children stacked up(since you need to store all of them but visit the first one 
            System.out.print("New Children are: ( ");   
            for(Map.Entry<String, Integer> e: allChildrenNodes.entrySet()){
                Node cityNode=destinationMap.get(e.getKey());
                if(cityNode==null) return;
                Node childNode=new Node(cityNode,currNode);             // Make a new child node with parent as current node
                if(!visited.contains(childNode.getCityName())){         // If the child is not visited
                    System.out.print(childNode.getCityName().toUpperCase()+" ");
                    visited.add(childNode.getCityName());               // Mark is as visited
                    childNodeStack.push(childNode);                     // and push it to the child node stack
                }
            }
            while(!childNodeStack.isEmpty()){   // If the current node has children
                Node chd=childNodeStack.pop();  // Get the children
                nodesStack.push(chd);           // and push them into the node stack
                this.graphics_panel.colorCity(chd.getCityName(), Color.red);
                openList.add(0,chd);            // Append the open list from front
            }
            System.out.println(" )");
            findAndRemoveNode(openList,currNode);                               //Find and remove the current node from list
            if(currNode.getParent()!=null)
                this.graphics_panel.setRoad(Color.GREEN,currNode.getCityName(),
                currNode.getParent().getCityName());//
            this.graphics_panel.colorCity(currNode.getCityName(), Color.CYAN);
            closedList.add(currNode.getCityName());                             // add the current node to the closing list
            System.out.print("Open List is ");displayNodeList(openList);        // Print the open list
            System.out.print("Closed List is ");displayStringList(closedList);  // print the closed list
        }
    }
}
/////////////////// END OF SOURCE FILE  ///////////////////////////////////