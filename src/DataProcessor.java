import java.util.*;
//will handle dataset related problems
public class DataProcessor {
  ArrayList<Feature> dataset = new ArrayList<Feature>();

  //converts string array to Feature object and adds it to data list
  public void addFeature(String line){
    //converts first index to 0
    int number = Integer.parseInt(line[0]);
    //obtains all the different cuisine
    String[] styles = line[2].replace("[", "").replace("]", "").split(";");
    //converts rating to double
    double rating = Double.parseDouble(line[3]);
    //converts number of reviewNo to Double
    double reviewNo = Integer.parseInt(line[5]);

    //creates a new feature from the row in the CSV file
    Feature feature = new Feature(number, line[1], styles, rating, line[4], reviewNo, line[6], line[7]);
    //Adds this feature to the dataset
    dataset.add(feature);
  }
  public void process(double minimumRating){
    HashMap<String, int[]> ratingList;
    //
    for(Feature feature: dataset){
      if(map.exist(feature.name)){
        map.put(feature.name, )
      }
      }
    }
  }

  //
}
