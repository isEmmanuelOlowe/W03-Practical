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

  protected ArrayList<Feature> dataset = new ArrayList<Feature>();

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
    String[] styles = line[styleIndex].replace("[", "").replace("]", "").replace("'", "").split(";");
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
}
