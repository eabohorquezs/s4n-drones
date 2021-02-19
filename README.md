# S4N - Delivery drones - "Su corrientazo a domicilio"

# INDEX OF THIS README DOCUMENT

## 1 - SOLUTION DESCRIPTION
## 2 - HOW TO BUILD AND CONFIGURE
## 3 - HOW TO RUN
## 4 - HOW TO DEBUG
## 5 - HOW TO TEST
## 6 - HOW TO REVIEW COVERAGE REPORTS
## 7 - THIRD PARTY LIBRARIES
## 8 - PENDING IMPROVEMENTS
## 9 - WHY TO AVOID IN-LINE COMMENTS IN SOURCE CODE
## 10 - HOW TO BUILD AND RUN UNIT AND INTEGRATION TESTS OF THE PROJECT IN DOCKER
## 11 - HOW TO RUN AND TEST THE PROJECT IN DOCKER 

# 1 - SOLUTION DESCRIPTION
The main parts of the solution for "Su corrientazo a domicilio" are:

* com.s4n.drones.Main: It is the entry point of the work flow. This work flow will iterate over each drone of the fleet. The work flow will read their correspondent input files, process each line and print out the results.
* After processing the delivery route, the work flow will update the drone position.
* When the route drives drones outside of the coverage area, the drones won't perform the delivery and they won't change their initial positions either.
* Drones will start from the last delivery position, except in those cases when drones have performed more deliveries than the max quantity of deliveries allowed per trip. In those cases the next route will start from position (0, 0, N)
* Routes which drive drones outside of the coverage area are not counted as performed deliveries.
* At the end of the each line processing, the work flow will print out the final position in its correspondent output file.

* com.s4n.drones.Configurator: This singleton class loads the configuration of the program, which is set up in the file ./src/main/resources for production environments or ./src/test/resources for unit and integration tests. 

* com.s4n.drones.processor.Processor: It is a class in charge of processing the instructions of each delivery route. This class will find out if any route drives drones outside of coverage area. This class will also update the drone position after each instruction.

* com.s4n.drones.ui.ClientFacade: This class is in charge of the interaction with clients through input and output files.

* com.s4n.drones.conf.Configurator: This singleton class loads the configuration of the program.

* com.s4n.drones.model This package contains all our data class model. These classes are:
- Orientation: It is a Enum which defines the four cardinal points.
- Position: This class represent the position of the drones by a x and y coordinates and a Orientation.
- Drone: This class encapsulates all the information related to drones: position, input and output file names.

# 2 - HOW TO BUILD AND CONFIGURE
* This project creates a jar into <PROJECT-ROOT>/target/s4n-drones-1.0-SNAPSHOT.jar with the following command:

## mvn clean package

* It is important to configure this program in src/main/resources/config.properties
* - FINAL_DRONE_POSITION_REPORT_DIR Absolute path of the directory to write output reports (File separators are placed by the program, by replacing {0} for them)
* - INPUT_FILE_NAME_FORMAT Format of the input file names for instance: in01.txt (The drone code is placed by the program, by replacing {0} for it)
* - MAX_BLOCKS_RANGE Max number of blocks around that are included in our coverage area
* - MAX_DRONES_QUANTITY=1 Max quantity of drones of the restaurant fleet
* - MAX_QTY_OF_DELIVERIES_PER_TRIP Max amount of deliveries that each drone can perform per trip
* - OUTPUT_FILE_NAME_FORMAT Format of the output file names for instance: out01.txt (The drone code is placed by the program, by replacing {0} for it)
* - ROUTES_FILE_DIR Absolute path of the directory to read input files with the delivery routes (File separators are placed by the program, by replacing {0} for them)

# 3 - HOW TO RUN
* This project can be run with the following command:

## java -jar target/s4n-drones-1.0-SNAPSHOT.jar

# 4 - HOW TO DEBUG
* This project can be debugged with the following command:

## java -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y -jar target/s4n-drones-1.0-SNAPSHOT.jar

## Eclipse part
* Open the Debug Configurations and set up a remote application on port 8998. 
* Run the configuration. 
* Put breakpoints over source code.

* To debug a test (In this example MainTest):

## mvn -Dtest=MainTest -Dmaven.surefire.debug test

## Eclipse part
* Open the Debug Configuration in Eclipse and set up a remote application on port 5005. 
* Run the configuration.

# 5 - HOW TO TEST
* The (unit and integration) tests of this project can be run with the following command:

## mvn clean test

* The test environment must be configured in src/test/resources/config.properties
* For testing, it is necessary to set up just the following properties:
* - FINAL_DRONE_POSITION_REPORT_DIR
* - ROUTES_FILE_DIR 

# 6 - HOW TO REVIEW COVERAGE REPORTS
* The project coverage report can be found after run its tests in the location: <PROJECT-ROOT>/target/site/jacoco/index.html.
* At the moment, the total percentage of code lines coverage is 86%, between unit and integration test.
* There was not enough time to write accurate tests because of my responsabilities this week.

# 7 - THIRD PARTY LIBRARIES
* The following third party libraries were used:
- junit: This library was used for testing.
- mockito: This library was used for using mocks, spies and etc. in our tests.
- PowerMock: This library was used for mocking static classes among other functionalities in our tests.

# 8 - PENDING IMPROVEMENTS
* As drone files do not have to be processed sequentially, they could be processed with parallel streams.
* Processing of chars in each input line can be performed with reflection. 
* We could include functional tests.

# 9 - WHY TO AVOID IN-LINE COMMENTS IN SOURCE CODE
* I try to avoid as much as possible in-line comments because the source code must be self-explanatory enough. They are also difficult to mantain and that is why I think is a good practice to avoid them.

# 10 - HOW TO BUILD AND RUN UNIT AND INTEGRATION TESTS OF THE PROJECT IN DOCKER
* With a multi-stage build, we are going to build and test our project into a Docker image.
* At first, we have to trigger the full build:

## docker build . -t s4n-drones

* To retrieve test results, we have to:
- target the build stage

## docker build --target build-env . -t s4n-drones-test

* Next, we need to create a container from the image that that targets the test stage. 

## docker create s4n-drones-test

* This last step will produce an id that we will reference as TEST_RESULTS_CONTAINER_ID. We will use this TEST_RESULTS_CONTAINER_ID in the following steps.
* This command will also produce a folder in our current location into our localhost, this folder is called in this case test-results

## docker cp TEST_RESULTS_CONTAINER_ID:/usr/app/target ./test-results

* We can open our coverage report locally in ./test-results/site/jacoco/index.html

* In the last created folder we will also include the results of our integration tests

## docker cp TEST_RESULTS_CONTAINER_ID:/usr/app/docker-resources/test ./test-results/results

* We can review out integration test results in ./test-results/results/output/out1.txt


