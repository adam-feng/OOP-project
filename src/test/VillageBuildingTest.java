package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.VillageBuilding;
import unsw.loopmania.NormalCharacter;
/**
 * Village building test
 */
public class VillageBuildingTest {
    /**
     * Test add village
     */
    @Test
    public void testAddVillage(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        VillageBuilding villageBuilding = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(villageBuilding);
    
        assertEquals(d.getBuildingEntities().get(0), villageBuilding); 
        assertEquals(villageBuilding.getX(), 0);
        assertEquals(villageBuilding.getY(), 1);  
    }

    /**
     * Test character gaining health when walking through
     */
    @Test
    public void testHealthGain(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        VillageBuilding villageBuilding = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(villageBuilding);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        assertEquals(character.getHealth(), 100);
        

        // set health to 30
        character.setHealth(30);

        character.moveDownPath();
        assertEquals(villageBuilding.isCharacterHere(character), true);
        //handle village
        d.isAtVillage();
        assertEquals(character.getHealth(), 90);
    }

    /**
     * Max health that character can gain back is 100
     */
    @Test
    public void testMaxHealth() {

        // new character 
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        VillageBuilding villageBuilding = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(villageBuilding);
        PathPosition position = new PathPosition(0, orderedPath);
        NormalCharacter character2 = new NormalCharacter(position);
        d.setCharacter(character2);
        assertEquals(character2.getHealth(), 100);
        character2.setHealth(50);
        character2.moveDownPath();
        assertEquals(villageBuilding.isCharacterHere(character2), true);
        // text max health
        d.isAtVillage();
        assertEquals(character2.getHealth(), 100);
    }
}