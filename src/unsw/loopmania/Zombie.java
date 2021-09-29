package unsw.loopmania;

import java.util.Random;

/**
 * Zombie enemy
 */
public class Zombie extends BasicEnemy {
    /**
     * Zombie constructor
     * @param position
     */
    public Zombie(PathPosition position) {
    
        super(position, 2, 3, 10, 20, "Zombie");
    }

    /**
     * Take damage from character
     */
    public void takeDamage(double damage, WeaponItem weapon) {
        this.setHealth(this.getHealth() - damage);
    }

    /**
     * Drop items upon defeat
     */
    public String dropItems() {
        Random rand = new Random();

        int drop = rand.nextInt(20);


        if (drop < 4) {
            return "Sword";
        } if (drop < 5) {
            return "Stake";
        } if (drop < 7) {
            return "Staff";
        } 
        if (drop < 8) {
            return "Card";
        } if (drop < 9) {
            return "Armour";
        } if (drop < 10) {
            return "Helmet";
        } if (drop < 11) {
            return "Shield";
        } if (drop < 13) {
            return "Potion";
        } if (drop == 15) {
            int rareItemChance = rand.nextInt(5);
                if (rareItemChance == 1){
                    return "OneRing";
                } else if (rareItemChance == 2) {
                    return "Anduril";
                } else if (rareItemChance == 3) {
                    return "TreeStump";
                }
        } if (drop ==14) {
            int classItemChance = rand.nextInt(1);
            if (classItemChance == 0) {
                return "Lance";
            } else if (classItemChance == 1) {
                return "ShortSword";
            }
        }
        return "";
    }

    /**
     * Drop gold upon defeat
     */
    @Override
    public int dropGold() {
        return 200;
    }

    /**
     * Drop xp upon defeat
     */
    @Override
    public int dropXp() {
        return 500;
    }

    /**
     * Move zombie
     */
    public void move(int tick, LoopManiaWorld world) {
        if (tick == 1) {
            int directionChoice = (new Random()).nextInt(6);
            if (directionChoice >= 3){
                moveUpPath();
            }
            else if (directionChoice < 3){
                moveDownPath();
            }
        }
    }

}