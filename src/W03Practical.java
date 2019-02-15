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
      //determines which process to run dependent on terminal input
      switch (process) {
        case "cuisine":
          CuisineProcessor cuisineDatasetProcess = new CuisineProcessor();
          cuisineDatasetProcess.readCSV(fileName);
          cuisineDatasetProcess.processCuisine(outputFile);
          break;
        case "rated":
          TopRatedProcessor topRatedProcess = new TopRatedProcessor();
          topRatedProcess.readCSV(fileName);
          topRatedProcess.processTopRated(outputFile);
          break;
        default:
          RestaurantProcessor restaurantDatasetProcess = new RestaurantProcessor();
          restaurantDatasetProcess.readCSV(fileName);
          restaurantDatasetProcess.processMinimumForCity(process, outputFile);
          break;
      }
    }
    else {
      System.out.println("Usage: java W03Practical <input_file> <output_file> <minimum_rating>");
    }
  }
}
