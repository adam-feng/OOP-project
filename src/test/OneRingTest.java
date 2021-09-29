package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.OneRing;
import unsw.loopmania.NormalCharacter;

/**
 * Test for the One Ring rare item
 */
public class OneRingTest {
    /**
     * Test the One Ring revives character and sets health to 100
     */
    @Test
    public void testUseOneRing(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        OneRing ring = d.addUnequippedOneRing();
        
        assertEquals(d.getUnequippedInventoryItems().size(), 1);
        // kill character
        character.setHealth(0);

        // use oneRing
        d.characterDie();
        assertEquals(character.getHealth(), 100);
    }
}
