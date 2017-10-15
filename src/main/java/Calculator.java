import java.util.ArrayList;
import java.util.Map;

/** Abstract skeleton class for the calculators */
abstract class Calculator {

    Map<Long,ArrayList<Long>> oracleMultiMap;
    Map<Long,ArrayList<Long>> rankedListMultiMap;

    public Calculator(Map<Long,ArrayList<Long>> oracleMultiMap, Map<Long,ArrayList<Long>> rankedListMultiMap){
        this.oracleMultiMap = oracleMultiMap;
        this.rankedListMultiMap = rankedListMultiMap;
    }

    public abstract Double getResult();

    protected abstract void calculate();
}
