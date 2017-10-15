import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class PrecisionCalculator extends Calculator {

    private Integer foundLinks = 0;
    private Integer correctLinks = 0;
    private Double result;

    public PrecisionCalculator(Map<Long, ArrayList<Long>> oracleMultiMap, Map<Long, ArrayList<Long>> rankedListMultiMap) {
        super(oracleMultiMap, rankedListMultiMap);
    }


    @Override
    public Double getResult(){
        return result;
    }

    /**
     * Calculates the correct and found links, then calls {@link #computePrecision()}}.
     */
    @Override
    protected void calculate() {
        Iterator rankedListIterator = rankedListMultiMap.entrySet().iterator();

        while (rankedListIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)rankedListIterator.next();

            Collection<Long> collectionWithValuesFromRL = rankedListMultiMap.get(pair.getKey());
            // make a copy of the oracle multi map to not modify the original oracle multi map later
            Collection<Long> collectionithValuesFromO = (Collection<Long>) oracleMultiMap.get(pair.getKey()).clone();

            Integer countOfAllObjectsInRankedList = collectionWithValuesFromRL.size();
            Integer countOfAllObjectsInOracle = collectionithValuesFromO.size();

            // remove redundant values which have been found in the ranked list.
            collectionithValuesFromO.removeAll(collectionWithValuesFromRL);

            Integer countOfAllObjectsInOracleAfterRemoval = collectionithValuesFromO.size();

            // count how much objects have are left after removal of the found ranked list objects
            Integer differenceAfterRemoving = countOfAllObjectsInOracle - countOfAllObjectsInOracleAfterRemoval;

            foundLinks = foundLinks + countOfAllObjectsInRankedList;
            correctLinks = correctLinks + differenceAfterRemoving;
        }
        computePrecision();
    }

    private void computePrecision() {
        this.result = Double.valueOf(correctLinks)
                /Double.valueOf(foundLinks);
    }
}
