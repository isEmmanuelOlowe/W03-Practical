import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.SortedMap;
import java.text.DecimalFormat;

/**
* Responsible for the manipulation of the dataset.
*
* @author 180003815
*/
public class DataProcessor {

  private ArrayList<Feature> dataset = new ArrayList<Feature>();
  //For finding number of cuisine style restaurants in each city process
  //nested map implementation shall be used
  private SortedMap<String, SortedMap<String, Integer>> cuisineList = new TreeMap<String, SortedMap<String, Integer>>();
  //for finding minimum rating process
  private SortedMap<String, int[]> ratingList = new TreeMap<String, int[]>();
  private double minimumRating;

  /**
  * Reads in CSV dataset.
  *
  * @param fileName the location of the file
  */
  public void readCSV(String fileName) {

    //will temporarily store a line in the dataset
    String line = null;
    try {
      //FileReader reads text files in the default enconding
      FileReader fileReader = new FileReader(fileName);
      //allows for lines to be read
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      //firsts line is just feature headings
      line = bufferedReader.readLine();
      //reads the data from the flll;
      while ((line = bufferedReader.readLine()) != null) {
        //adds the line of file to the dataset
        this.addFeature(line.split(","));
      }
      //close file
      bufferedReader.close();
    }
    //in event file cannot be opened
    catch (FileNotFoundException ex) {
      System.out.println("Unable to open dataset: " + fileName);
    }
    //incase error in reading the dataset
    catch (IOException ex) {
      System.out.println("Error in reading dataset: " + fileName);
    }
  }


  private void addFeature(String[] line) {

    //The various index locations of the labels in the data
    final int numberIndex = 0;
    final int nameIndex = 1;
    final int cityIndex = 2;
    final int styleIndex = 3;
    final int rankingIndex = 4;
    final int ratingIndex = 5;
    final int priceRangeIndex = 6;
    final int reviewNoIndex = 7;
    final int reviewsIndex = 8;
    final int urlTaIndex = 9;
    final int idTaIndex = 10;
    //converts first index to 0
    int number = Integer.parseInt(line[numberIndex]);
    //obtains all the different cuisine
    String[] styles = line[styleIndex].replace("[", "").replace("]", "").replace(" ", "").replace("'", "").split(";");
    //converts ranking to Integer
    int ranking = (int) toDouble(line[rankingIndex]);
    //converts rating to double
    double rating = toDouble(line[ratingIndex]);
    //converts number of reviewNo to Double
    int reviewNo = (int) toDouble(line[reviewNoIndex]);
    //obtains all the different Reviews
    String[] reviews = line[reviewsIndex].replace("[", "").replace("]", "").replace(" ", "").replace("'", "").split(";");
    //creates a new feature from the row in the CSV file
    Feature feature = new Feature(number, line[nameIndex], line[cityIndex], styles, ranking,
    rating, line[priceRangeIndex], reviewNo, reviews, line[urlTaIndex], line[idTaIndex]);
    //Adds this feature to the dataset list
    this.dataset.add(feature);
  }

  //converts a string to a double
  //factors for if the field is empty
  private static double toDouble(String number) {

    //sets a default value incase strign is empty
    double newNumber = 0;
    if (!number.equals("")) {
      newNumber = Double.parseDouble(number);
    }
    return newNumber;
  }

  /**
  * Determines the Restaurants which are greater the minmum rating.
  *
  * @param sMinimumRating the minimum rating
  */
  public void processMinimumForCity(String sMinimumRating) {

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

  /**
  * Prints a summary of the process data about restaurants and citie to specified file.
  *
  * @param fileName the location in which the file should be stored
  */
  public void printMinimumForCity(String fileName) {

    //for convert to percentage
    final int percent = 100;
    String pattern = "##0.#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    //stores the total of restaurants meeting or not minimum rating
    int resAboveMin = 0;
    int resBelowMin = 0;
    //will create and write to desired file
    try {
      PrintWriter writer = new PrintWriter(fileName);
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
