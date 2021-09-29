package unsw.loopmania;

import java.util.List;
import org.javatuples.Pair;

/**
 * Interface for placement strategy
 */
public interface PlacementStrategy {
    
    public abstract boolean canPlace(int x, int y, List<Pair<Integer, Integer>> orderedPath);

}
