import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.SortedMap;
import java.text.DecimalFormat;


public class CuisineProcessor extends DataProcessor {
  //For finding number of cuisine style restaurants in each city process
  //nested map implementation shall be used
  private SortedMap<String, SortedMap<String, Integer>> cuisineList = new TreeMap<String, SortedMap<String, Integer>>();

  /**
  * Will perform process that shall obtain the number of restaurants in each city for a cuisine style.
  *
  * @param fileName the name of the file that process data will be printed to
  */
  public void processCuisine(String fileName) {

    for (Feature feature: this.dataset) {
      //checks if the features city is in the map
      if (!this.cuisineList.containsKey(feature.getCity())) {
        //sets a default starting value for the city
         SortedMap<String, Integer> empty = new TreeMap<String, Integer>();
        //adds city to the map with default value
        this.cuisineList.put(feature.getCity(), empty);
      }
      //updates the value to add wether or not it meets the requirement
      addCuisine(feature.getCity(), feature.getStyle());
      }
      printCuisine(fileName);
    }

  //adds cuisine to map
  private void addCuisine(String city, String[] styles) {
    SortedMap<String, Integer> cityCuisineList = this.cuisineList.get(city);
    for(String style: styles){
      if(!cityCuisineList.containsKey(style)) {
        //adds the cuisine to the list
        //since there is that cuisine default value of 1
        cityCuisineList.put(style, new Integer(1));
      }
      else {
        //updates the value of the cuisine
        //ensures map data is treated as integer
        Integer updatedValue = cityCuisineList.get(style) + new Integer(1);
        cityCuisineList.put(style, updatedValue);
      }
    }
  }

  //prints output the the output of the running of the process to a file
  private void printCuisine(String fileName) {
    try {
      PrintWriter writer = new PrintWriter(fileName);

      for (String key: this.cuisineList.keySet()) {
        writer.println(key + " has:");
        SortedMap<String, Integer> cuisineStyles = this.cuisineList.get(key);
        for (String style: cuisineStyles.keySet()) {
          writer.println("\t" + cuisineStyles.get(style) + " "  + style
          + " restaurants.");
        }
      }
      writer.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Error occurrred Writing to " + fileName);
    }

  }
}
