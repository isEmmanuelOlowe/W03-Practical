import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

//will handle dataset related problems
public class DataProcessor {

  private ArrayList<Feature> dataset = new ArrayList<Feature>();
  private SortedMap<String, int[]> ratingList = new TreeMap<String, int[]>();
  private double minimumRating;

  public void readCSV(String fileName){
    //will temporarily store a line in the dataset
    String line = null;
    try {
      //FileReader reads text files in the default enconding
      FileReader fileReader = new FileReader(fileName);

      BufferedReader bufferedReader = new BufferedReader(fileReader);

      //firts line is just feature headings
      line = bufferedReader.readLine();
      //reads the data from the flll;
      while((line = bufferedReader.readLine()) != null){
        //adds the line of file to the dataset
        this.addFeature(line.split(","));
      }
      bufferedReader.close();
    }
    //in event file cannot be opened
    catch(FileNotFoundException ex){
      System.out.println("Unable to open dataset: " + fileName);
    }
    //incase error in reading the dataset
    catch(IOException ex){
      System.out.println("Error in reading dataset: " + fileName);
    }
  }
  //converts string array to Feature object and adds it to data list
  public void addFeature(String[] line){
    //converts first index to 0
    int number = Integer.parseInt(line[0]);
    //obtains all the different cuisine
    String[] styles = line[3].replace("[", "").replace("]", "").replace(" ", "").replace("'", "").split(";");
    //converts ranking to Integer
    int ranking = 0;
    if(!line[4].equals("")){
      ranking = (int)Double.parseDouble(line[4]);
    }
    //converts rating to double
    double rating = 0;
    if(!line[5].equals("")){
      rating = Double.parseDouble(line[5]);
    }
    //converts number of reviewNo to Double
    int reviewNo = 0;
    if(!line[7].equals("")){
      reviewNo = (int)Double.parseDouble(line[7]);
    }
    //obtains all the different Reviews
    String[] reviews = line[3].replace("[", "").replace("]", "").replace(" ", "").replace("'", "").split(";");
    //creates a new feature from the row in the CSV file
    Feature feature = new Feature(number, line[1], line[2], styles, ranking, rating, line[6], reviewNo, reviews, line[9], line[10]);
    //Adds this feature to the dataset
    this.dataset.add(feature);
  }

  //process the dataset to obtain restaurants which meet minimum rating
  public void process(String sMinimumRating){
    try{
      this.minimumRating = Double.parseDouble(sMinimumRating);
    }
    catch (NumberFormatException e){
      System.out.println("Cannot parse as double: " + sMinimumRating);
    }
    for(Feature feature: this.dataset){
      if(!this.ratingList.containsKey(feature.getCity())){
        int[] empty = {0, 0};
        this.ratingList.put(feature.getCity(), empty);
      }
      this.ratingList.put(feature.getCity(), rating(this.ratingList.get(feature.getCity()), feature.getRating()));
      }
    }

  public int[] rating(int[] cityRating, double resRating){
    int[] updatedRating = cityRating;
    if(resRating >= this.minimumRating){
      updatedRating[0] += 1;
    }
    else{
      updatedRating[1] += 1;
    }
    return updatedRating;
  }

  //Creates a new Txt file with the summary of some of data
  public void printTo(String fileName){
    String pattern = "##0.#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    //stores the total of restraunts meeting or not minimum rating
    int resAboveMin = 0;
    int resBelowMin = 0;
    //will create and write to desired file
    try{
      PrintWriter writer = new PrintWriter(fileName);
      //prints summaries for all cities
      for(String key: this.ratingList.keySet()){
        int[] ratings = this.ratingList.get(key);
        //adds the restraunts which meet the respective ratings groups
        resAboveMin += ratings[0];
        resBelowMin += ratings[1];
        //prints summary of city and restraunts which meet minimum to specificed file
        writer.println(key + " has " + (ratings[0] + ratings[1]) +" restaurants.");
        writer.println("Number of restaurants with rating above (or equal to) " +
        decimalFormat.format(this.minimumRating) + ": " + ratings[0]);
        writer.println("Number of restaurants with rating below " + decimalFormat.format(this.minimumRating)
        + ": " + ratings[1]);
        writer.println("Percentage of restaurants with rating above (or equal to) " +
        decimalFormat.format(this.minimumRating) + ": " + decimalFormat.format(((double)ratings[0] * 100 / (ratings[0] + ratings[1]))) + "%");
        writer.println("");
      }

      //prints the overall summary
      writer.println("Overall");
      writer.println("Number of cities: " + this.ratingList.keySet().size());
      writer.println("Number of restaurants: " + (resAboveMin + resBelowMin));
      writer.println("Number of restaurants with rating above (or equal to) " +
      decimalFormat.format(this.minimumRating) + ": " + resAboveMin);
      writer.println("Number of restaurants with rating below " +
      decimalFormat.format(this.minimumRating) + ": " + resBelowMin);
      writer.println("Percentage of restaurants with rating above (or equal to) "
      + decimalFormat.format(this.minimumRating) + ": " + decimalFormat.format(((double)resAboveMin * 100 / (resAboveMin + resBelowMin))) + "%");
      writer.close();
    }
    catch(FileNotFoundException ex){
      System.out.println("Error occurrred Writing to " + fileName);
    }
  }
}
