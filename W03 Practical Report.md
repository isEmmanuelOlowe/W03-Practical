# W03 Practical Report

## Overview

### Initial Specification

#### Requirements

* To process a data from a file
  * Read from a CSV file
  * Create and write to a new file
* The output file must contain:
  * Total number of restaurants for each city
  * Total number of restaurants that have a rating equal to or greater than minimum rating for each city
  * Total number of restaurants that have a rating less than minimum rating for each city
  * Percentage of restaurants that have a rating equal to or greater than minimum rating in each city
  * A summary at the end of the output file consisting of of the total umber of restaurants with a rating equal to or greater than and less than, minimum rating with percentage as well.
  * All numbers must be printed to 1 decimal place (`DecimalFormat` class may be used).

#### Assumptions

#### Deliverables

Program must run with command

```bash
java W03Practical <input_file> <output_file> <minimum_rating>
```

and if incorrect input is received program must the prompt in terminal

```bash
Usage: java W03Practical <input_file> <output_file> <minimum_rating>
```

The program must then be able to read all the data from the CSV file  and process it to be written to desired output file.

#### Problem Decomposition

* The program must separate all the fields by ","
* The program must be able to assign data types to fields co compare them with minimum rating
* The program able to sort the cities in alphabetical order while also gathering the information with relation to minimum rating.
* Output this formatted to file
* Detect errors in format of fields
* Detect errors in terminal input
* Detect errors in location of input file

## Design

### Initial Specification

[insert Class Diagram]

It was decided the final solution would be implemented in 3 classes. A class (`Feature`) was created to store each line of data. This was though as effective as it would help ensure data from the file is not changed during run time (by making them private and creating getters to limit their visibility) and allow for all the data in the file to be assigned proper data types. Problems that may arise from using this model is that if data is missing then empty space can not be converted to `int` or any other use case. Simple if statements can be used to ensure that this is factored for. This allows for an `ArrayList` with a specific type of `Feature` to store all the data in the dataset.

The `DataProcessor` was created with the intent to create a layer of abstraction upon the main class `W03Practical` and allow for the core elements of the task to be spilt into specific methods. e.g. reading the dataset, processing it to summarise on restaurants meeting the minimum rating, writing the process summaries to text file. The`ArrayList` type was used to store the dataset as I don't know how many line there are in the dataset and also this has be able to work on multiple variations of the initial dataset so to allow for this variable amount of elements an `ArrayList` was used. The `SortedMap<String, int[]>` was used to allow for all the keys of the map to be stored in alphabetical order and also to ensure cities are unique and allow for the storage of both number of restaurants which meet criteria and number of restaurants which don't. It was noted the the dataset contained missing data in ranking, rating and number of review fields. If there were tried to be converted to `int` or `double` eventually a runtime error would occur as an empty string would try to be converted to double. So a method was created to deal with this called `toDouble` it would convert the number to a double and if the string is empty give a automatic value of 0. It was noted that every field in the dataset had a number so this method was not required to be applied to it.

## Testing

### Initial Specification

#### Test case 1

The purpose of this test is to show that the program is fully operational given expected input. The following command was use to run the program.

```bash
java W03Practical clean_large.csv clean_large.txt 3.5
```

##### Expected Result

It is expected that the program shall not show any errors and that a new file containing all the data summaries in alphabetical order shall be produced.

##### Actual Result

[insert image]

#### Test case 2

The purpose of this test is to show that the program clearly gives error in case of incorrect input

1. incorrect `<minimum_rating>` format

   ```bash
   java W03Practical clean_large.csv clean_large.txt three
   ```

2. no command line input

   ```bash
   java W03Practical
   ```

3. non-existent dataset file referenced.

   ```bash
   java W03Practical doesnt_exist.csv clean_large.txt 3.5
   ```

   

##### Expected Results

1. "`Cannot parse as double: three`" error will be reported to terminal

2. "`Usage: java W03Practical <input_file> <output_file> <minimum_rating>`"
3. "`Unable to read dataset: doesnt_exist.csv`"

##### Actual Results

[insert images]

#### Stacscheck Output



## Evaluation

### Initial Specification

Through the testing of the program it was show the program meets the requires. Test case 1 showed

## Conclusion

In this practical a program was developed to allow for the reading and processing of information in a dataset.

Difficulties encountered during implementation were that string numbers which were in the format of "1.0", "5.0" were unable to be parsed directly to integers. To bypass this error they were first parsed to double then cast to integers.