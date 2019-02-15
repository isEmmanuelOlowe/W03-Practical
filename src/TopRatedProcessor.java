import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.SortedMap;
import java.io.FileOutputStream;

public class TopRatedProcessor extends DataProcessor {

  //nested map which will store city: cuisine: top restaurant
  private SortedMap<String, SortedMap<String, ArrayList<Feature>>> topRatedList = new TreeMap<String, SortedMap<String, ArrayList<Feature>>>();

  /**
  * Will perform process that obtains the restaurants with greatest rating for cusiine in a city.
  *
  * @param feature the name of the output file
  */
  public void processTopRated(String fileName) {

    for (Feature feature: this.dataset) {
      //check the city is in the map
      if (!this.topRatedList.containsKey(feature.getCity())) {
        //Sets a default value for the city of toprated restaurant and cuisine
        SortedMap<String, ArrayList<Feature>> empty = new TreeMap<String, ArrayList<Feature>>();
        //adds it to the map
        this.topRatedList.put(feature.getCity(), empty);
      }
      //updates the top rated restaurants
      isTopRated(feature);
    }
    String[] componets = fileName.split("\\.");
    //determines if user is trying to create html file
    if (componets.length == 2){
      if (componets[1].equals("html")) {
        printTopRatedHTML(fileName);
      }
    }
    else {
      //just prints data to text format
      printTopRated(fileName);
    }
  }

  private void isTopRated(Feature feature) {

    SortedMap<String, ArrayList<Feature>> topRated = this.topRatedList.get(feature.getCity());
    for (String style: feature.getStyle()) {
      if (!topRated.containsKey(style)) {
        ArrayList<Feature> restaurants= new ArrayList<Feature>();
        restaurants.add(feature);
        topRated.put(style, restaurants);
      }
      else if (topRated.get(style).get(0).getRating() == feature.getRating()) {
      //adds the restaurant to the top rated list for that cusine style
      topRated.get(style).add(feature);
      }
      else if (topRated.get(style).get(0).getRating() < feature.getRating()) {
        topRated.get(style).clear();
        topRated.get(style).add(feature);
      }
    }
  }

  private void printTopRated (String fileName) {
    try {
      //FileOutputStream added to guarrentee file overwrite if it exists
      PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, false));

      for (String key: this.topRatedList.keySet()) {
        writer.println("The total Rated Restaurants in " + key + " for cuisines are: ");
        SortedMap<String, ArrayList<Feature>> topRated = this.topRatedList.get(key);
        for (String style: topRated.keySet()) {
          writer.println("\t" + style + ": ");
          for (Feature restaurant: topRated.get(style)) {
            writer.println("\t\t--Name:" + restaurant.getName() + " Rating:" + restaurant.getRating());
          }
        }
      }
      writer.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Error occurrred Writing to " + fileName);
    }
  }

  private void printTopRatedHTML (String fileName) {
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, false));
      //html prologue
      writer.println("<html>");
      writer.println("\t<head>");
      writer.println("\t\t<title> The Top Rated Restaurants</title>");
      writer.println("\t</head>");
      writer.println("\t<body>");
      writer.println("\t\t<h1> The Name and Rating of the Best Rated"
      + " Restaurants for Each cuisine style in each city</h1>");
      for (String key: this.topRatedList.keySet()) {
        writer.println("\t\t<h2>" + key + "</h2>");
        SortedMap<String, ArrayList<Feature>> topRated = this.topRatedList.get(key);
        for (String style: topRated.keySet()) {
          writer.println("\t\t<h3>" + style + "</h3>");
          for (Feature restaurant: topRated.get(style)) {
            writer.println("\t\t<h4>" + restaurant.getName() + "</h4>");
            writer.println("\t\t<h5> Rating: " + restaurant.getRating());
            writer.println("<h5>Reviews: </h5>");
            for (String review: restaurant.getReviews()) {
              writer.println("<p>" + review + "</p>");
            }
          }
        }
      }
      //html epilogue
      writer.println("\t</body>");
      writer.println("</html>");
      writer.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Error occurrred Writing to " + fileName);
    }
  }
}
