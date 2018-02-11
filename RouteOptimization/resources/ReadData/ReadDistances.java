/****************************************************************
*           Introduction To Artificial Intelligence             * 
*       CSCI 697         Program 1          SPRING 2017         *
*       Professor:  Dr. Reva Freedman                           *
*       Programmer:     Anwar Siddiqui                          *
*       Section:      Independent study                         *
*       Date Submitted:       Monday February 2, 2017           *
****************************************************************/
package routeoptimization.ReadData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import routeoptimization.Algorithms.Node;

/**
 *
 * @author Anwar
 */
public class ReadDistances {
    private final String fileName;
    private final Map<String, Node> distanceMap;
    public ReadDistances(String name){
        fileName=name;
        distanceMap=new TreeMap<>();
    }
    // Read from file apply regex and store the values in to hashMap which is being returned
    public Map<String, Node> getDistanceMap(){
        File file=new File(fileName);
        Scanner sc = null;
        try{
            sc= new Scanner(file);
        }
        catch(FileNotFoundException e){
            System.out.println(fileName+": Not found!");
            System.exit(1);
        }
        Node n = null; String city=null;
        while(sc.hasNext()){
            // Read the line here and apply regex to read the city name and road name
            String line=sc.nextLine();
            String pattern_source_city="(\\w+):", pattern_destination="(\\w+)[ ](\\d+)";
            Pattern p_source_city=Pattern.compile(pattern_source_city);
            Pattern p_destination_city=Pattern.compile(pattern_destination);
            Matcher m_source_city=p_source_city.matcher(line);
            Matcher m_destination=p_destination_city.matcher(line);
            if(m_source_city.find()){
                if(n!=null){
                    distanceMap.put(city, n);
                }
                city=m_source_city.group(1).toLowerCase();
                n=new Node(city);
            }
            else if(m_destination.find()){
                if(n!=null)
                    n.addDestination(m_destination.group(1).toLowerCase(), Integer.valueOf(m_destination.group(2)));
            }
        }
        distanceMap.put(city, n);
        System.out.println("Cities Read from file: "+distanceMap.size());
        return distanceMap;
    }
    public void readDistanceMap(){
        distanceMap.entrySet().stream().map((e) -> {
            System.out.println(e.getKey());
            return e;
        }).forEachOrdered((e) -> {
            Node n=e.getValue();
            //   n.readChildren();
        });
    }
    class SortNode implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return n1.getCityName().compareTo(n2.getCityName());
        }
    }
}
//////////////////  END OF FILE /////////////////////////////////