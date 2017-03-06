/****************************************************************
*           Introduction To Artificial Intelligence             * 
*       CSCI 697         Program 1          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Monday February 2, 2017           *
****************************************************************/
package routeoptimization.Algorithms;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Anwar
 */
public class Node {
    private final String cityName;
    private Node parent;
    private int depth;
    private int f, g, h;
    private final Map<String, Integer> destinations;
    public Node(String city){
        cityName=city;
        destinations=new TreeMap<>();
        parent=null;
        depth=-1;   // Set -1 as initial values, it will help in printing list for DFID & AStar using single fns
        f=g=h=-1;
    }
    public Map<String, Integer> getDestinations(){
        return destinations;
    }
    Node(Node n, Node p){
        cityName=n.getCityName();
        destinations=n.getDestinations();
        parent=p;
        depth=f=g=h=-1;
    }
    public void setDepth(int depth){
        this.depth=depth;
    }
    public int getDepth(){
        return depth;
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
    public Map<String, Integer> getChildNodes(){
        return destinations;
    }
    public String getCityName(){
        return cityName;
    }
    public void readChildren(){
        System.out.print("( ");
        destinations.entrySet().forEach((e) -> {
            System.out.print(e.getKey().toUpperCase()+" ");
        });
        System.out.println(")");
    }
    public void readChildrenWithDepth(int depth){
        System.out.print("( ");
        destinations.entrySet().forEach((e) -> {
            System.out.print("("+e.getKey().toUpperCase()+" "+depth+") ");
        });
        System.out.println(")");
    }
    public int getHValue(){
        return h;
    }
    public void setHValue(int h){
        this.h=h;
    }
    public int getGValue(){
        return g;
    }
    public void setGValue(int g){
        this.g=g;
    }
    public int getFValue(){
        return f;
    }
    public void setFValue(int f){
        this.f=f;
    }
}

// All comaprator's second sorting priority is alphabetical

class SortHValues implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getHValue()== o2.getHValue()){
            return o1.getCityName().compareTo(o2.getCityName());
        }
        return o1.getHValue()- o2.getHValue();
    }
}
class SortGValues implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getGValue()== o2.getGValue()){
            return o1.getCityName().compareTo(o2.getCityName());
        }
        return o1.getGValue()- o2.getGValue();
    }
}
class SortFValues implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getFValue()== o2.getFValue()){
            return o1.getCityName().compareTo(o2.getCityName());
        }
        return o1.getFValue()- o2.getFValue();
    }
}
//////////////////  END OF FILE /////////////////////////////////