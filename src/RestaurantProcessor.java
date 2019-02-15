import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.SortedMap;
import java.text.DecimalFormat;
import java.io.FileOutputStream;

public class RestaurantProcessor extends DataProcessor {

  //for finding minimum rating process
  private SortedMap<String, int[]> ratingList = new TreeMap<String, int[]>();
  private double minimumRating;

  /**
  * Determines the Restaurants which are greater the minmum rating.
  *
  * @param sMinimumRating the minimum rating
  * @param fileName the name of the output file
  */
  public void processMinimumForCity(String sMinimumRating, String fileName) {

    //attempts to convert the minimum rating to an integer
    try {
      this.minimumRating = Double.parseDouble(sMinimumRating);
    }
    catch (NumberFormatException e) {
      System.out.println("Cannot parse as double: " + sMinimumRating);
    }
    //loops over every feature in dataset to determine city
    //and if it meets minimum rating requirements
    for (Feature feature: this.dataset) {
      //checks if the features city is in the map
      if (!this.ratingList.containsKey(feature.getCity())) {
        //sets a default starting value for the city
        int[] empty = {0, 0};
        //adds city to the map with default value
        this.ratingList.put(feature.getCity(), empty);
      }
      //updates the value to add wether or not it meets the requirement
      this.ratingList.put(feature.getCity(), rating(this.ratingList.get(feature.getCity()),
      feature.getRating()));
      }
      String[] componets = fileName.split("\\.");
      if (componets.length == 2) {
        if (componets[1].equals("html")) {
          //determines if user is trying to create html file
          printMinimumForCityHTML(fileName);
        }
      }
      else {
        //just prints data to text format
        printMinimumForCity(fileName);
      }
    }

  /**
  * Prints a summary of the process data about restaurants and citie to specified file.
  *
  * @param fileName the location in which the file should be stored
  */
  private void printMinimumForCity(String fileName) {

    //for convert to percentage
    final int percent = 100;
    String pattern = "##0.#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    //stores the total of restaurants meeting or not minimum rating
    int resAboveMin = 0;
    int resBelowMin = 0;
    //will create and write to desired file
    try {
      //FileOutputStream added to guarrentee file overwrite if it exists
      PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, false));
      //prints summaries for all cities
      for (String key: this.ratingList.keySet()) {
        int[] ratings = this.ratingList.get(key);
        //adds the restaurants which meet the respective ratings groups
        resAboveMin += ratings[0];
        resBelowMin += ratings[1];
        //prints summary of city and restaurants which meet minimum to specificed file
        writer.println(key + " has " + (ratings[0] + ratings[1]) + " restaurants.");
        writer.println("Number of restaurants with rating above (or equal to) "
        + decimalFormat.format(this.minimumRating) + ": " + ratings[0]);
        writer.println("Number of restaurants with rating below " + decimalFormat.format(this.minimumRating)
        + ": " + ratings[1]);
        writer.println("Percentage of restaurants with rating above (or equal to) "
        + decimalFormat.format(this.minimumRating) + ": " + decimalFormat.format(((double) ratings[0] * percent / (ratings[0] + ratings[1]))) + "%");
        writer.println("");
      }

      //prints the overall summary
      writer.println("Overall");
      writer.println("Number of cities: " + this.ratingList.keySet().size());
      writer.println("Number of restaurants: " + (resAboveMin + resBelowMin));
      writer.println("Number of restaurants with rating above (or equal to) "
      + decimalFormat.format(this.minimumRating) + ": " + resAboveMin);
      writer.println("Number of restaurants with rating below "
      + decimalFormat.format(this.minimumRating) + ": " + resBelowMin);
      writer.println("Percentage of restaurants with rating above (or equal to) "
      + decimalFormat.format(this.minimumRating) + ": " + decimalFormat.format(((double) resAboveMin * percent / (resAboveMin + resBelowMin))) + "%");
      writer.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Error occurrred Writing to " + fileName);
    }
  }

  private void printMinimumForCityHTML(String fileName) {

        //for convert to percentage
        final int percent = 100;
        String pattern = "##0.#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        //stores the total of restaurants meeting or not minimum rating
        int resAboveMin = 0;
        int resBelowMin = 0;
        //will create and write to desired file
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, false));
      //html prologue
      writer.println("<html>");
      writer.println("\t<head>");
      writer.println("\t\t<title>City Restaurant Ratings</title>");
      writer.println("\t</head>");
      writer.println("\t<body>");
      writer.println("\t\t<h1>Which City has most Restaurants above " + this.minimumRating + "</h1>");
      for (String key: this.ratingList.keySet()) {
        int[] ratings = this.ratingList.get(key);
        //adds the restaurants which meet the respective ratings groups
        resAboveMin += ratings[0];
        resBelowMin += ratings[1];
        writer.println("\t\t<h2>" + key + "</h2>");
        writer.println("\t\t<h2>Number of restaurants with rating above (or equal to) "
        + decimalFormat.format(this.minimumRating) + ": " + ratings[0] + "</h2>");
        writer.println("\t\t<h2>Number of restaurants with rating below "
        + decimalFormat.format(this.minimumRating) + ": " + ratings[1] + "</h2>");
        writer.println("\t\t<h2>Percentage of restaurants with rating above (or equal to) "
        + decimalFormat.format(this.minimumRating) + ": "
        + decimalFormat.format(((double) ratings[0] * percent / (ratings[0] + ratings[1]))) + "%</h2>");
      }
      //prints the overall summary
      writer.println("<h2>Overall</h2>");
      writer.println("<h2>Number of cities: " + this.ratingList.keySet().size() + "</h2>");
      writer.println("<h2>Number of restaurants: " + (resAboveMin + resBelowMin) + "</h2>");
      writer.println("<h2>Number of restaurants with rating above (or equal to) "
      + decimalFormat.format(this.minimumRating) + ": " + resAboveMin + "</h2>");
      writer.println("<h2>Number of restaurants with rating below "
      + decimalFormat.format(this.minimumRating) + ": " + resBelowMin + "</h2>");
      writer.println("<h2>Percentage of restaurants with rating above (or equal to) "
      + decimalFormat.format(this.minimumRating) + ": "
      + decimalFormat.format(((double) resAboveMin * percent / (resAboveMin + resBelowMin)))
      + "% </h2>");
      //html epilogue
      writer.println("\t</body>");
      writer.println("</html>");
      writer.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Error occurrred Writing to " + fileName);
    }
  }
  //determines if a restaurant meets the minimum rating requirement
  private int[] rating(int[] cityRating, double resRating) {

    int[] updatedRating = cityRating;
    //checks if it greaters or equal to minimum rating
    if (resRating >= this.minimumRating) {
      updatedRating[0] += 1;
    }
    else {
      updatedRating[1] += 1;
    }
    return updatedRating;
  }
}
