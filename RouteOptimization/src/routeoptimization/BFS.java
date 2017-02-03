/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Anwar
 */
public class BFS{
    private final String source;
    private final String destination;
    HashMap<String, Node> distanceMap;
    ArrayList<String> visited;
    int nodesExpanded;
    int routeDistance;
    BFS(String s, String d, HashMap<String, Node> hm){
        source=s; destination=d; distanceMap=hm;
        visited=new ArrayList<>();
        routeDistance=0;
        nodesExpanded=0;
    }
    public void findRoute(){
        // First look out for the source
        System.out.println("Finding route, distanceMap size: "+distanceMap.size()+"");
        Node n=distanceMap.get(source);
        if(n==null) System.out.println("Node is null");
        ArrayList<Node> ar=new ArrayList<>();
        ar.add(n);
        ArrayList<String> openList=new ArrayList<>();
        ArrayList<String> closed_list=new ArrayList<>();
        while(!ar.isEmpty()){
            n=ar.get(0);
            System.out.println("\nExpanding: "+n.getCityName());
            if(foundDestination(n.getCityName(), destination)){
                System.out.print("Breadth First Solution Path: "); showPath(n);
                return;
            }
            System.out.print("Children Are: "); n.readChildren();
            if(!visited.contains(n.getCityName())){
                visited.add(n.getCityName());
            }
            nodesExpanded++;
            HashMap<String, Integer> h=n.getChildrenNodes();
            System.out.print("New Children are: ( ");
            for(Entry<String, Integer> e: h.entrySet()){
                Node m=distanceMap.get(e.getKey());
                if(m==null) return;
                Node ch=new Node(distanceMap.get(e.getKey()),n);
                if(!visited.contains(ch.getCityName())){
                    System.out.print(e.getKey()+" ");
                    visited.add(e.getKey());
                    openList.add(e.getKey());
                    ar.add(ch);
                }
            }
            System.out.println(" )");
            openList.remove(ar.get(0).getCityName());
            closed_list.add(ar.get(0).getCityName());
            ar.remove(0);
            System.out.print("Open List is ");displayList(openList);
            System.out.print("Closed List is ");displayList(closed_list);
        }
    }
    private void displayList(ArrayList<String> ar){
        System.out.print("( ");
        ar.forEach((s) -> {
            System.out.print(s+' ');
        });
        System.out.println(")");
    }
    private boolean foundDestination(String city, String destination){
        return city.equals(destination);
    }
    public int getRouteDistance(){
        return 0;//
    }
    public int getNodesExpanded(){
        return nodesExpanded;
    }
    private void showPath(Node n){
        if(n==null) return;
        showPath(n.getParent());
        System.out.print(n.getCityName()+' ');
    }
}