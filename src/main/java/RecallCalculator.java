import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class RecallCalculator extends Calculator{

    private Integer correctLinks = 0;
    private Integer allExistingLinks = 0;
    private Double result;

    public RecallCalculator(Map<Long, ArrayList<Long>> oracleMultiMap, Map<Long, ArrayList<Long>> rankedListMultiMap) {
        super(oracleMultiMap, rankedListMultiMap);
    }

    @Override
    public Double getResult() {
        return result;
    }

    /**
     * Calculates the correct found and all existing correct links, then calls {@link #computeRecall()}}.
     */
    @Override
    protected void calculate() {
        Iterator rankedListIterator = rankedListMultiMap.entrySet().iterator();

        while (rankedListIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)rankedListIterator.next();

            Collection<Long> collectionWithValuesFromRL = rankedListMultiMap.get(pair.getKey());
            Collection<Long> collectionithValuesFromO = (Collection<Long>) oracleMultiMap.get(pair.getKey()).clone();

            Integer countOfAllObjectsInOracle = collectionithValuesFromO.size();

            collectionithValuesFromO.removeAll(collectionWithValuesFromRL);

            Integer countOfAllObjectsInOracleAfterRemoval = collectionithValuesFromO.size();

            Integer differenceAfterRemoval = countOfAllObjectsInOracle - countOfAllObjectsInOracleAfterRemoval;

            correctLinks += differenceAfterRemoval;
        }

        Iterator oracleIterator = oracleMultiMap.entrySet().iterator();

        while (oracleIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)oracleIterator.next();
            allExistingLinks += oracleMultiMap.get(pair.getKey()).size();
        }

        computeRecall();
    }

    private void computeRecall(){
        this.result = Double.valueOf(correctLinks)
                /Double.valueOf(allExistingLinks);
    }
}
