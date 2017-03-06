/****************************************************************
*           Introduction To Artificial Intelligence             * 
*       CSCI 697         Program 1          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Monday February 2, 2017           *
****************************************************************/
package routeoptimization;

import routeoptimization.Algorithms.Node;
import routeoptimization.ReadData.ReadDistances;
import routeoptimization.ReadData.ReadLongs;
import routeoptimization.Visual.GUI;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFrame;

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
//        String cityFile, longsFile;
        Scanner sc=new Scanner(System.in);
//        System.out.print("City File location: "); cityFile=sc.nextLine();
        ReadDistances rd=new ReadDistances("G:\\AI\\Assignments\\Assignment1\\RouteOptimization\\src\\routeoptimization\\DataSet\\roads1.txt");
//        ReadDistances rd=new ReadDistances(cityFile);
        Map<String, Node> distanceMap=rd.getDistanceMap();
//        rd.readDistanceMap();

  //      System.out.print("Longitude File location: "); longsFile=sc.nextLine();
        ReadLongs rl=new ReadLongs("G:\\AI\\Assignments\\Assignment1\\RouteOptimization\\src\\routeoptimization\\DataSet\\long1.txt");
//        ReadLongs rl=new ReadLongs(longsFile);
        Map<String, Integer> longsMap=rl.getLongMap();
//        rl.readLongMap();

//+++++ GUI BUILD UP

        GUI guiFrame=new GUI("Route Optimization", distanceMap, longsMap);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(800,700);
        guiFrame.setVisible(true);
//++++++++++++++++++++++++++++++++++++++++++++++++        

        System.out.print("Source: "); source=sc.nextLine();
        System.out.print("Destination: ");destination=sc.nextLine();
/*
        System.out.println("\nBFS");
        BFS routeBFS=new BFS(guiFrame.source, destination, distanceMap);
        routeBFS.findRoute();
        System.out.println("\nPath-length: "+routeBFS.getRouteDistance()+"kms");
        System.out.println(routeBFS.getNodesExpanded()+" nodes Expanded");

        System.out.println("\nDFS");
        DFS routeDFS=new DFS(source, destination, distanceMap);
        routeDFS.findRoute();
        System.out.println("\nPath-length: "+routeDFS.getRouteDistance()+"kms");
        System.out.println(routeDFS.getNodesExpanded()+" nodes Expanded");

        System.out.println("\nH_ONLY");
        H_ONLY routeH_Only=new H_ONLY(source, destination, distanceMap, longsMap);
        routeH_Only.findRoute();        
        System.out.println("\nPath-length: "+routeH_Only.getRouteDistance()+"kms");
        System.out.println(routeH_Only.getNodesGenerated()+" nodes Generated");
        
        System.out.println("\nA* with H=0");
        AStar routeAStarH0=new AStar(source, destination, distanceMap, longsMap, false);
        routeAStarH0.findRoute();
        System.out.print("\nAStar Solution Path with h=0: ");         routeAStarH0.showPath();
        System.out.println("\nPath-length: "+routeAStarH0.getRouteDistance()+"kms");
        System.out.println(routeAStarH0.getNodesGenerated()+" nodes Generated");

        System.out.println("\nA* with H=EAST-WEST DISTANCE");
        AStar routeAStar=new AStar(source, destination, distanceMap, longsMap, true);
        routeAStar.findRoute();
        System.out.print("\nA-start-search Solution: ");         routeAStar.showPath();
        System.out.println("\nPath-length: "+routeAStar.getRouteDistance()+"kms");
        System.out.println(routeAStar.getNodesGenerated()+" nodes Generated");

        System.out.println("\nDFID with Level = 3");
        DFID routeDFID=new DFID(source, destination, 3, distanceMap); // Level =3
        routeDFID.findRoute();
        System.out.println("\nPath-length: "+routeDFID.getRouteDistance()+"kms");
        System.out.println(routeDFID.getNodesExpanded()+" nodes Expanded");
  */      
    }
}
//////////////////  END OF FILE /////////////////////////////////