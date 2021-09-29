package test;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;
import unsw.loopmania.NormalCharacter;

public class TreeStumpTest {
    /**
     * Test treeStump's damage stats
     */
    @Test
    public void testTreeStumpStats() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(treeStump.getDefence(), 0.8);
    }

    /**
     * Test equipping treeStump 
     */
    @Test
    public void testAddTreeStump() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        TreeStump treeStump = d.addUnequippedTreeStump();
        d.convertItemToEquipped(treeStump.getX(), treeStump.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }
    

    /**
     * Test equip and unequip treeStump
     */
    @Test
    public void testAddRemoveTreeStump() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        TreeStump treeStump = d.addUnequippedTreeStump();
        d.convertItemToEquipped(treeStump.getX(), treeStump.getY(), 2, 1);

        assertEquals(d.getEquippedItems().size(), 1);
        d.convertItemToUnequipped(2, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);
    }

    /**
     * Test battle with TreeStump. should only be effective against bosses
     * Should be triple damage of character 
     */
    @Test
    public void testBattleWithTreeStump() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        TreeStump treeStump = d.addUnequippedTreeStump();
        d.convertItemToEquipped(treeStump.getX(), treeStump.getY(), 2, 1);

        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));
        d.addEnemy(doggie);
        List<BasicEnemy> enemies = d.getEnemies();

        assertEquals(treeStump.checkBoss(enemies), true);

        character.takeDamage(d, doggie);
        assertEquals(character.getHealth(), 92);
    }
}
