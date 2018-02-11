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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import routeoptimization.Algorithms.Node;

/**
 *
 * @author Anwar
 */
public class ReadLongs {
    private final String fileName;
    private final Map<String, Integer> cityLongs;
    public ReadLongs(String name){
        fileName=name;
        cityLongs=new TreeMap<>();
    }
    // Read from file apply regex and store the values in to hashMap which is being returned
    public Map<String, Integer> getLongMap(){
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
            String pattern_city_long="^(\\w+)[ ]([-]*\\d+)";
            Pattern p_city_long=Pattern.compile(pattern_city_long);
            Matcher m_city_long=p_city_long.matcher(line);
            if(m_city_long.find()){
   //             System.out.println("City: "+m_city_long.group(1)+" Long value: "+m_city_long.group(2));
                cityLongs.put(m_city_long.group(1).toLowerCase(), Integer.valueOf(m_city_long.group(2)));
            }
        }
        return cityLongs;
    }
    public void readLongMap(){
        cityLongs.entrySet().forEach((e) -> {
            System.out.println(e.getKey()+" "+e.getValue());
        });
    }
}
//////////////////  END OF FILE /////////////////////////////////