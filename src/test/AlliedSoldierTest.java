package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

//import org.graalvm.compiler.core.common.cfg.Loop;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.AlliedSoldier;

/**
 * Tests for Allied Soldier
 */
public class AlliedSoldierTest {
    /**
     * Test allied soldier's attack stats
     */
    @Test
    public void dealDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));

        AlliedSoldier alliedSoldier = new AlliedSoldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        //allied soldier attack
        alliedSoldier.dealDamage(slug);
        assertEquals(slug.getHealth(), 5.0);
    }

    /**
     * Ensures allied soldiers also take damage to their health
     */
    @Test
    public void takeDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));

        LoopManiaWorld d = new LoopManiaWorld(3, 3, orderedPath);
        AlliedSoldier alliedSoldier = new AlliedSoldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        //allied soldier attack
        alliedSoldier.takeDamage(d, slug);
        assertEquals(alliedSoldier.getHealth(), 25.0);
    }
}
