/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization;

import java.util.HashMap;

/**
 *
 * @author Anwar
 */
public class Node {
    private final String cityName;
    private Node parent;
    private final HashMap<String, Integer> destinations;
    Node(String city){
        cityName=city;
        destinations=new HashMap<>();
        parent=null;
    }
    public HashMap<String, Integer> getDestinations(){
        return destinations;
    }
    Node(Node n, Node p){
        cityName=n.getCityName();
        destinations=n.getDestinations();
        parent=p;
    }
    public void setParent(Node p){
        parent=p;
    }
    public Node getParent(){
        return parent;
    }
    public void addDestination(String city, int d){
        destinations.put(city, d);
    }
    public HashMap<String, Integer> getChildrenNodes(){
        return destinations;
    }
    public String getCityName(){
        return cityName;
    }
    public void readChildren(){
        System.out.print("( ");
        destinations.entrySet().forEach((e) -> {
            System.out.print(e.getKey()+" ");
        });
        System.out.println(")");
    }
}
