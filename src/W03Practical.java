import java.util.*;
import java.io.*;

public class W03Practical {
  public static void main(String[] args){
    if(args.length == 3){
      String fileName = args[0];
      String outputFile = args[1];
      String minimumRating = args[2];

      DataProcessor dataset = new DataProcessor();

      readCSV(dataset, fileName);
      dataset.process(minimumRating);
      dataset.printTo(outputFile);
    }
    else{
      System.out.println("Usage: java W03Practical <input_file> <output_file> <minimum_rating>");
    }
  }

  public static void readCSV(DataProcessor dataset, String fileName){
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
        dataset.addFeature(line.replace("\"").split(","));
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
}
