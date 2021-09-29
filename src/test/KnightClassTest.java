package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Sword;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Armour;
import unsw.loopmania.KnightDecorator;

public class KnightClassTest {
    /**
     * Test spawn and set character
     */
    @Test
    public void KnightStatsTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        KnightDecorator character = new KnightDecorator(position);
        d.setCharacter(character);

        assertEquals(character.getHealth(), 120);
    }

    /**
     * Test character's damage with sword +20%
     */
    @Test
    public void dealDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        KnightDecorator character = new KnightDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 1, 1);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 3.0);
    }

    /**
     * Test character taking damage with armour +20%
     */
    @Test
    public void takeDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        KnightDecorator character = new KnightDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        Armour armour = d.addUnequippedArmour();
        d.convertItemToEquipped(armour.getX(), armour.getY(), 1, 1);


        //manufacturing a battle
        character.takeDamage(d, slug);
        assertEquals(character.getHealth(), 118.0);
    }

    /**
     * test Inspire = allied soldiers get +10% damage and 10% health
     */
    @Test
    public void inspireTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        KnightDecorator character = new KnightDecorator(position);
        d.setCharacter(character);

        AlliedSoldier alliedSoldier = new AlliedSoldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        alliedSoldier.knightEnhance();
        assertEquals(alliedSoldier.getDamage(), 11);
        assertEquals(alliedSoldier.getHealth(), 33);
    }
}
