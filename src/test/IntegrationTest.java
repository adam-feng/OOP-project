package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Sword;
import unsw.loopmania.Zombie;
import unsw.loopmania.Vampire;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.NormalCharacter;

/**
 * Integration tests
 */
public class IntegrationTest {
    /**
     * Battles slugs without weapons, then adding weapons
     */
    @Test
    public void equipUnequipWeapons() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition slugposition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugposition);

        // character and slug fight
        // character does damage first 
        character.dealDamage(d, slug);
        // slug should be health 5
        assertEquals(slug.getHealth(), 5);
        // slug does damage
        character.takeDamage(d, slug);
        // character health should be 95
        assertEquals(character.getHealth(), 95);
        // character does damage again
        character.dealDamage(d, slug);
        //slug should be dead 
        assertEquals(slug.getHealth(), -5);
        
        character.moveDownPath();

        //equip sword
        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 1, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        //new slug
        Slug slug2 = new Slug(new PathPosition(2, orderedPath));
        
        // character and new slug fight
        character.dealDamage(d, slug2);
        // slug should be health 0 now since character does 20 damage
        assertEquals(slug.getHealth(), -5);

        //unequip sword 
        d.convertItemToUnequipped(1, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);

        // new slug 
        Slug slug3 = new Slug(new PathPosition(3, orderedPath));
        character.moveDownPath();
        
        // character and slug3 fight
        character.dealDamage(d, slug3);
        // slug health should be 5 again since sword is unequipped
        assertEquals(slug3.getHealth(), 5);
    }

    /**
     * Character battles multiple enemies
     */
    @Test
    public void multipleEnemyTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Slug slug = new Slug(new PathPosition(1, orderedPath));
        Zombie zombie = new Zombie(new PathPosition(2, orderedPath));
        Vampire vampire = new Vampire(new PathPosition(3, orderedPath));

        d.addEnemy(slug);
        d.addEnemy(zombie);
        d.addEnemy(vampire);

        assertEquals(d.getEnemies().size(), 3);
        // enemies should all be in fight since they are all either in battle or support radius
        assertEquals(d.getEnemiesToFight().size(), 3);
        assertEquals(d.getEnemyFightQueue().size(), 3);
    }

    /**
     * Character battles a slug with an allied soldier
     */
    @Test
    public void testBattleWithAllies() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        d.addBuildingEntity(barracks);

        character.moveDownPath();
        assertEquals(d.canSpawnAlliedSoldier(barracks), true);
        AlliedSoldier a = barracks.spawnSoldier(d, new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        assertEquals(d.getAlliedSoldiers().size(), 1);

        Slug slug = new Slug(new PathPosition(2, orderedPath));
        character.moveDownPath();

        // battle
        character.dealDamage(d, slug);
        a.takeDamage(d, slug);
        assertEquals(character.getHealth(), 100);
        assertEquals(a.getHealth(), 25);
        assertEquals(slug.getHealth(), 5);
    }
}
