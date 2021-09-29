package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

/**
 * Zombie pit building
 */
public class ZombiePitBuilding extends Building {
    /**
     * Zombie pit constructor
     * @param x
     * @param y
     */
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "ZombiePitBuilding");
    }

    /**
     * Get valid zombie spawn place
     * @param world
     * @return
     */
    public int getValidZombieSpawnPathIndex(LoopManiaWorld world) {

        int validIndex = 0;

        // Position of the ZombiePit
        int x = this.getX();
        int y = this.getY();

        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();

        List<Pair<Integer, Integer>> validSpawnPositions = new ArrayList<Pair<Integer, Integer>>();

        for (Pair<Integer, Integer> pathPos : orderedPath) {
            // if pathPos is adjacent to the pit's location...
            // int pathX = pathPos.getValue0();
            // int pathY = pathPos.getValue1();
            
            if (inSpawnRange(x, y, pathPos, world)) {
                validSpawnPositions.add(pathPos);
            }
        }

        // Just returns the first valid index right now.
        Random rand = new Random();
        int position = rand.nextInt(validSpawnPositions.size());
        validIndex = orderedPath.indexOf(validSpawnPositions.get(position));
        validSpawnPositions.remove(position);
        

        return validIndex;
    }

    /**
     * Returns true if building is in spawn range
     * @param x
     * @param y
     * @param pathPos
     * @param world
     * @return
     */
    private boolean inSpawnRange(int x, int y,Pair<Integer, Integer> pathPos, LoopManiaWorld world) {
        if (Math.pow((x-pathPos.getValue0()), 2) +  Math.pow((y-pathPos.getValue1()), 2) <= 9){
            if (world.readyToSpawn(world.getOrderedPath().indexOf(pathPos))) {
                return true;
            }
        } return false;
    }
}
