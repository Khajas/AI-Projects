/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class RouteOptimization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        String source, destination;
        ReadDistances rd=new ReadDistances("C:\\Users\\Anwar\\Desktop\\AI\\RouteOptimization\\src\\routeoptimization\\roads1.txt");
        HashMap<String, Node> distanceMap=rd.getDistanceMap();
//        rd.readDistanceMap();
        Scanner sc=new Scanner(System.in);
        source=sc.nextLine(); destination=sc.nextLine();
        System.out.println("\n\nBFS\n\n");
        BFS routeBFS=new BFS(source, destination, distanceMap);
        routeBFS.findRoute();
        System.out.println("\nRoute Distance: "+routeBFS.getRouteDistance());
        System.out.println(routeBFS.getNodesExpanded()+" nodes Expanded");
        System.out.println("\n\nDFS\n\n");
        DFS routeDFS=new DFS(source, destination, distanceMap);
        routeDFS.findRoute();
        System.out.println("\nRoute Distance: "+routeDFS.getRouteDistance());
        System.out.println(routeDFS.getNodesExpanded()+" nodes Expanded");
    }
}
