/**
* Java program to process dataset from kaggle.
*
* @author 180003815
*/
public class W03Practical {

  /**
  * The main method which interacts with DataProcessor class.
  *
  * @param args the command line arguments for which the program requires
  */
  public static void main(String[] args) {
    final int expexctedArguments = 3;

    if (args.length == expexctedArguments) {
      String fileName = args[0];
      String outputFile = args[1];
      String process = args[2];

      DataProcessor dataset = new DataProcessor();

      dataset.readCSV(fileName);

      //determines which process to run dependent on terminal input
      switch (process) {
        case "cuisine":
          dataset.processCuisine(outputFile);
          break;
        //case "rated":
          //dataset.processRated();
          //break;
        default:
          dataset.processMinimumForCity(process);
          dataset.printMinimumForCity(outputFile);
          break;
      }
    }
    else {
      System.out.println("Usage: java W03Practical <input_file> <output_file> <minimum_rating>");
    }
  }
}
