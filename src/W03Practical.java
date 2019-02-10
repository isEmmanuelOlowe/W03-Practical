import java.util.*;
import java.io.*;

public class W03Practical {
  public static void main(String[] args){
    if(args.length == 3){
      String fileName = args[0];
      String outputFile = args[1];
      String minimumRating = args[2];

      DataProcessor dataset = new DataProcessor();

      dataset.readCSV(fileName);
      dataset.process(minimumRating);
      dataset.printTo(outputFile);
    }
    else{
      System.out.println("Usage: java W03Practical <input_file> <output_file> <minimum_rating>");
    }
  }
}
