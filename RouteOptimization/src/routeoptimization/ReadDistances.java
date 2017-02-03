/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routeoptimization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Anwar
 */
public class ReadDistances {
    private final String fileName;
    private final HashMap<String, Node> distanceMap;
    ReadDistances(String name){
        fileName=name;
        distanceMap=new HashMap<>();
    }
    // Read from file apply regex and store the values in to hashMap which is being returned
    public HashMap<String, Node> getDistanceMap(){
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
        System.out.println("Size of distance map: "+distanceMap.size());
        return distanceMap;
    }
    public void readDistanceMap(){
        for(Entry<String, Node> e: distanceMap.entrySet()){
            System.out.println(e.getKey());
            Node n=e.getValue();
         //   n.readChildren();
        }
    }
}
