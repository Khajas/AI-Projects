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
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import routeoptimization.Visual.GraphicsPanel;
import routeoptimization.Visual.MainPanel;

/**
 *
 * @author Anwar
 */
public class DFID extends SearchAlgorithm{
    public DFID(GraphicsPanel gp, MainPanel mp,String s, String d, int level, Map<String, Node> hm){
        source=s; destination=d; max_level=level;
        destinationMap=hm;
        super.graphics_panel=gp;
        super.main_panel=mp;
        super.main_panel.jl_algo_name.setText("DFID(all 1,2,3)");
    }
    @Override
    public void findRoute(){
        int currDepth=0;                                            // Set initial depth
        Node sourceNode=destinationMap.get(source);                 // Get the source node
        Node destinaionNode=destinationMap.get(destination);        // Get the destination node
        validate(sourceNode, destinaionNode);                       // Validate the source and destination
        sourceNode.setFValue(-1); sourceNode.setDepth(0);           // Initialize the f and depth values    
        while(currDepth <= max_level){                              // As long depth is no reached
            System.out.println("\nDepth Level: "+currDepth);
            main_panel.jl_path.setText("Searching with depth level: "+currDepth);
            graphics_panel.sleepThread();
            graphics_panel.refreshMap();
            graphics_panel.sleepThread();
            DFS(sourceNode,currDepth);                              // Call DFS on source node with current depth
            openList.clear();                                       // Clear all the history(lists)
            closedList.clear();
            visited.clear();
            ++currDepth;                                            // Increment the depth
        }
    }
    
    private void DFS(Node startNode, int level){
        Stack<Node> nodeStack=new Stack<>();    // Node stack for DFS
        startNode.setDepth(0);                  // Start at zero leve
        nodeStack.push(startNode);              // Push the start node to stack
        openList.add(startNode);                // Add it to open list
        while(!nodeStack.empty()){              // As long as the stack is not empty
            Node currNode=nodeStack.pop();             // get the top most node
            System.out.println("\nExpanding: "+currNode.getCityName().toUpperCase());
            if(!currNode.getCityName().equals(source)){
                this.graphics_panel.setRoad(Color.RED,currNode.getCityName(),
                currNode.getParent().getCityName());
                graphics_panel.sleepThread();
                graphics_panel.threadState();
                if(!graphics_panel.running) return;
            }
            visited.add(currNode.getCityName());
            if(foundDestination(currNode.getCityName(), destination)){
                System.out.print("DFID Search Solution: "); showPath(currNode);
                graphics_panel.sleepThread();
                this.graphics_panel.colorCity(openList.get(0).getCityName(), Color.CYAN);
                pathLastNode=currNode;          // If the destinaton is found, show the path & update pathLastNode
                return;
            }
            ++nodesExpanded;
            main_panel.jl_nodes_expanded.setText(String.valueOf(nodesExpanded));
            if(level<=currNode.getDepth()){
                System.out.println("Depth has reached");                            // If depth reached
                main_panel.jl_path.setText("Depth has reached, destination not found!");
                findAndRemoveNode(openList, currNode);                              // Remove the current node
                if(currNode.getParent()!=null)
                    this.graphics_panel.setRoad(Color.GREEN,currNode.getCityName(),
                        currNode.getParent().getCityName());//
                closedList.add(currNode.getCityName()+" "+currNode.getDepth());     // Update the close list
                this.graphics_panel.colorCity(currNode.getCityName(), Color.CYAN);
                System.out.print("Open List is ");displayNodeList(openList);        // Show open and closed lists
                System.out.print("Closed List is ");displayStringList(closedList);
                continue;
            }
            System.out.print("Children Are: "); currNode.readChildrenWithDepth(currNode.getDepth()+1);  // Read children of current node
            Map<String, Integer> allChildNodes=new TreeMap<>(currNode.getChildNodes()); 
            System.out.print("New Children are: ( ");
            Stack<Node> childStack=new Stack<>();
            for(Map.Entry<String, Integer> e: allChildNodes.entrySet()){
                Node cityNode=destinationMap.get(e.getKey());                   // For each of the children nodes
                if(cityNode==null) return;              
                Node childNode=new Node(cityNode,currNode);
                childNode.setDepth(currNode.getDepth()+1);                      // Set the depth
                if(!visited.contains(childNode.getCityName())){                 // If they're not visited
                    System.out.print(childNode.getCityName().toUpperCase()+" "+childNode.getDepth()+" ");
                    if(currNode.getDepth() >= level) continue;                  // ****If the current node is above or at current level, continue to check other nodes
                    visited.add(childNode.getCityName());                       // Else visit the child
                    childStack.push(childNode);                                 // push them to child stack
                    this.graphics_panel.colorCity(childNode.getCityName(), Color.red);
                    openList.add(0,childNode);                                  // Add to the front of the open list
                }
            }
            while(!childStack.isEmpty()){                   // As long as there are children push them to nodes stack
                nodeStack.push(childStack.pop());
            }
            System.out.println(" )");
            findAndRemoveNode(openList,currNode);
            if(currNode.getParent()!=null)
                this.graphics_panel.setRoad(Color.GREEN,currNode.getCityName(),
                currNode.getParent().getCityName());//Find and remove the current node from open list
            this.graphics_panel.colorCity(currNode.getCityName(), Color.CYAN);
            closedList.add(currNode.getCityName()+" "+currNode.getDepth());     // Update the close list
            Collections.sort(openList, new SortFValues());                      // Sort the open list
            System.out.print("Open List is ");displayNodeList(openList);        // Display the open list and closed list
            System.out.print("Closed List is ");displayStringList(closedList);
        }
    }
}
//////////////////  END OF FILE /////////////////////////////////