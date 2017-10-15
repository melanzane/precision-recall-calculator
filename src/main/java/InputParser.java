import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Assumes UTF-8 encoding
 */
public class InputParser {

    //create a map which holds several values for a key.
    private Map<Long,ArrayList<Long>> multiMap = new HashMap<Long,ArrayList<Long>>();

    //private final Path fFilePath;
    private final File file;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     Constructor.
     @param aFileName full name of an existing, readable file.
     */
    public InputParser(File aFileName){
        //fFilePath = Paths.get(aFileName);
       file = aFileName;
    }

    /**
     * Processes each line and calls {@link #processLine(String)}}.
     */
    public final void processLineByLine() throws IOException {
        try (Scanner fileScanner = new Scanner(file, ENCODING.name())){
            while (fileScanner.hasNextLine()){
                processLine(fileScanner.nextLine());
            }
        }
    }

    /**
     Overridable method for processing lines in different ways.
     <P>This simple default implementation expects simple key-value pairs, separated by an
     ',' sign. Examples of valid input:
     <tt>29.txt,130.txt,0.766992046042737</tt>
     <tt>18.txt,118.txt,0.716312632606823</tt>
     */
    protected void processLine(String aLine){

        //skip the title of the file
        if(!aLine.equals("source,target,similarity")) {
            //use a second Scanner to parse the content of each line
            //scanner assumes the line has a certain structure
            //after each ',' it removes the '.txt' and stores the first value as the key and the second as the value in a key value pair map
            Scanner scanner = new Scanner(aLine);
            scanner.useDelimiter(",");
            if (scanner.hasNext()) {
                String key = scanner.next();
                Long finalKey = Long.parseLong(key.split("\\.txt")[0]);

                String value = scanner.next();
                Long finalValue = Long.parseLong(value.split("\\.txt")[0]);

                //stores the values in a HashMap. A key can have several values.
                storeKeyValueMap(finalKey, finalValue);

            } else {
                log("Empty or invalid line. Unable to process.");
            }
        }
    }
    /**
     * If the key doesn't exists yet it creates a new key value pair. The key is a Long object and the value is a ArrayList with Long values
     * If the key already exists it enriches its ArrayList of values
     */
    private void storeKeyValueMap(Long key, Long value) {

        if (!multiMap.containsKey(key)) {
            ArrayList<Long> newListWithValues = new ArrayList<>();
            newListWithValues.add(value);
            multiMap.put(key, newListWithValues);

        } else {
            ArrayList<Long> existingListWithValues = multiMap.get(key);
            existingListWithValues.add(value);
            multiMap.put(key, existingListWithValues);
        }
    }

    /**
     * Prints the content of the key value pair HashMap
     */
    private void printMap() {
        Iterator it = multiMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            log("The values for the key: " + pair.getKey() + " are: " + pair.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * @return the multimap with values
     */
    public Map<Long, ArrayList<Long>> getMultiMap() {
        return this.multiMap;
    }

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }

    private String quote(String aText){
        String QUOTE = "'";
        return QUOTE + aText + QUOTE;
    }
} 