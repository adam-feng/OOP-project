package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.ShortSword;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.NormalCharacter;

/**
 * Test for Short Sword weapon
 */
public class ShortSwordTest {
    
    /**
     * Test Short Sword stats
     */
    @Test
    public void testShortSwordTest() {
        ShortSword shortSword = new ShortSword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(shortSword.getDamage(), 5);
    }

    /**
     * Equip Short Sword
     */
    @Test
    public void testAddShortSword(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        ShortSword shortSword = d.addUnequippedShortSword();
        d.convertItemToEquipped(shortSword.getX(), shortSword.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }

    /**
     * Battles a slug from Swift Foot
     */
    @Test
    public void testSwiftFootBattleShortSword() {

    }
}
