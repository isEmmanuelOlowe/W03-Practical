import java.util.*;

public class W03Practical {
  public static void main(String[] args){
    String fileName = args[0];
    //String outputFile = args[1];
    //String minimumRating = args[2];

    DataProcessor dataset = new DataProcessor();

    readCSV(dataset, fileName);
  }

  public static void readCSV(DataProcessor dataset, String fileName){
    //will temporarily store a line in the dataset
    String line = null;
    try {
      //FileReader reads text files in the default enconding
      FileReader fileReader = new FileReader(fileName);

      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while((line = bufferedReader.readLine()) != null){
        //adds the line of file to the dataset
        dataset.addFeature(line.split(","));
      }
    }
    catch(FileNotFoundException ex){
      System.out.println("Unable to open dataset: " + fileName);
    }
    catch(IOException ex){
      System.out.println("Error in reading dataset: " + fileName);
    }

    bufferedReader.close()
  }
}
