package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Item;
import unsw.loopmania.Shop;
import unsw.loopmania.Sword;

/**
 * Shop test
 */
public class ShopTest {
    
    /**
     * Selling items
     */
    @Test
    public void sellTest(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        Shop shop = new Shop(d);

        // give them an item 
        Sword sword = new Sword(null, null);
        d.addUnequippedSword();

        // attempt to sell item
        shop.sellItem(sword);

        // check gold
        assertEquals(d.getGold(), 20);
        
        // check inventory for item
        boolean itemInInventory = false;
        for (Item i : d.getUnequippedInventoryItems()) {
            if (i.equals(sword)) {
                itemInInventory = true;
            }
        }
        assertEquals(itemInInventory, false);
        assertEquals(d.getUnequippedInventoryItems().size(), 0);
    }

    /**
     * Buying items
     */
    @Test
    public void buyTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        Shop shop = new Shop(d);

        // attempt to buy item 
        assertEquals(shop.buyItem("Sword"), "You do not have enough gold!");

        // give more money
        d.setGold(100);

        // attempt to buy item again, should be successful
        assertEquals(shop.buyItem("Sword"), "Sword bought!");
    }


}