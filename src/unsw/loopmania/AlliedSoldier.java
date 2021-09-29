package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Allied soldier - character's support 
 */
public class AlliedSoldier extends StaticEntity {
    
    private int damage;
    private int health;
    private BasicEnemy tranceEnemy;

    /**
     * AlliedSoldier constructor
     * @param x
     * @param y
     */
    public AlliedSoldier(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "AlliedSoldier");
        this.damage = 10;
        this.health = 30;
        this.setTranceEnemy(null);
    }

    /**
     * Returns enemy that sets allied soldier in trance
     * @return BasicEnemy
     */
    public BasicEnemy getTranceEnemy() {
        return tranceEnemy;
    }

    /**
     * Set enemy that sets allied soldier in trance
     * @param enemy
     */
    public void setTranceEnemy(BasicEnemy enemy) {
        this.tranceEnemy = enemy;
    }

    /**
     * Deals damage to enemy. 
     * Cannot equip weapons, so damage will always be 10
     * @param enemy
     */
    public void dealDamage(BasicEnemy enemy) {
        enemy.takeDamage(this.damage, null);
    }

    /**
     * Takes damage from an enemy
     * @param world
     * @param enemy
     * @return int
     */
    public int takeDamage(LoopManiaWorld world, BasicEnemy enemy) {
        double damage = enemy.getDamage();
        int chance = 15;
        int criticalAttack = 500;

        if (enemy.getType().equals("Zombie")) {
            Random rand = new Random();
            criticalAttack = rand.nextInt(chance);
            if (criticalAttack < 1) {
                damage = enemy.getDamage() * 2;
                this.health -= damage;
                if (this.getTranceEnemy() == null) {
                    world.zombivilisation();
                }
            }
        } 
        this.health -= damage;
        return criticalAttack;
    }

    /**
     * Get Allied soldier health
     * @return int
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets allied soldier health
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Enhance allied soldier's damage and health
     */
    public void knightEnhance() {
        damage *= 1.1;
        health *= 1.1;
    }

    /**
     * Get allied soldier's damage levle
     * @return double
     */
    public double getDamage() {
        return damage;
    }
}
