import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest {

    ClassLoader classLoader;
    Map<Long,ArrayList<Long>> oracleListMultiMap;
    Map<Long,ArrayList<Long>> rankedListMultiMap;

    @BeforeAll
    public void initialize() throws IOException {

        this.classLoader = getClass().getClassLoader();

        File oracleFile = new File(classLoader.getResource("test_oracle2.txt").getFile());
        InputParser oracleListInputParser = new InputParser(oracleFile);
        oracleListInputParser.processLineByLine();
        this.oracleListMultiMap = oracleListInputParser.getMultiMap();


        File rankedListFile = new File(classLoader.getResource("test_ranked_list.txt").getFile());
        InputParser rankedListInputParser = new InputParser(rankedListFile);
        rankedListInputParser.processLineByLine();
        this.rankedListMultiMap = rankedListInputParser.getMultiMap();
    }

    @Test
    public void testInputParser() throws IOException {


        File file = new File(classLoader.getResource("test_oracle2.txt").getFile());
        InputParser oracleListInputParser = new InputParser(file);

        assertNotNull(oracleListInputParser);

        oracleListInputParser.processLineByLine();
        Map<Long,ArrayList<Long>> oracleListMultiMap = oracleListInputParser.getMultiMap();

        assertEquals(oracleListMultiMap.size(), 2);

        Collection<Long> collectionithValuesFromKey1 = oracleListMultiMap.get(1L);
        Collection<Long> collectionithValuesFromKey2 = oracleListMultiMap.get(2L);

        assertEquals(collectionithValuesFromKey1.size(), 2);
        assertEquals(collectionithValuesFromKey2.size(), 2);

    }

    @Test
    public void testRecallCalculator() {

        Calculator recallCalculator = new RecallCalculator(oracleListMultiMap, rankedListMultiMap);
        recallCalculator.calculate();
        Double expectedRecall = 0.5d;

        assertEquals(recallCalculator.getResult(), expectedRecall);
    }

    @Test
    public void testPrecisionCalculator() {

        Calculator precisionCalculator = new PrecisionCalculator(oracleListMultiMap, rankedListMultiMap);
        precisionCalculator.calculate();
        Double expectedPrecision = 0.5d;

        assertEquals(precisionCalculator.getResult(), expectedPrecision);
    }
}