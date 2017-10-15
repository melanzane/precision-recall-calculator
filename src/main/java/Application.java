import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Application {




    public static void main(String... aArgs) throws IOException {

        // TODO: set the path to the file
        InputParser oracleListInputParser = new InputParser(new File(/** example: "/Users/userName/SomeDirectory/precision-recall-calculator/src/main/resources/EasyClinic-Data/oracle/oracle2.txt" */""));
        oracleListInputParser.processLineByLine();
        Map<Long,ArrayList<Long>> oracleListMultiMap = oracleListInputParser.getMultiMap();

        // TODO: set the path to the files
        InputParser rankedListInputParser75 = new InputParser(new File(""));
        rankedListInputParser75.processLineByLine();
        Map<Long,ArrayList<Long>> rankedList75MultiMap = rankedListInputParser75.getMultiMap();

        // TODO: set the path to the files
        InputParser rankedListInputParser65 = new InputParser(new File(""));
        rankedListInputParser65.processLineByLine();
        Map<Long,ArrayList<Long>> rankedList65MultiMap = rankedListInputParser65.getMultiMap();

        // TODO: set the path to the files
        InputParser rankedListInputParser55 = new InputParser(new File(""));
        rankedListInputParser55.processLineByLine();
        Map<Long,ArrayList<Long>> rankedList55MultiMap = rankedListInputParser55.getMultiMap();


        // compute the precision and recall for the rankedList65MultiMap
        Calculator recallCalculator = new RecallCalculator(oracleListMultiMap, rankedList65MultiMap);
        recallCalculator.calculate();
        log("The recall for the given file is: " + recallCalculator.getResult());

        Calculator precisionCalculator = new PrecisionCalculator(oracleListMultiMap, rankedList65MultiMap);
        precisionCalculator.calculate();
        log("The precision for the given file is: " + precisionCalculator.getResult());



    }

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }
}
