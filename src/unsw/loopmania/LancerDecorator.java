package unsw.loopmania;

import java.util.Random;

public class LancerDecorator extends Character {
    

    public LancerDecorator(PathPosition position) {
        super(position, 10, 100);
        setBaseHealth(100);
    }

    /**
     * handles class attack 
     * multiplies when attacking with a lance
     */
    @Override
    public double handleClassAttack(LoopManiaWorld world) {
       
        Item weapon = world.getWeapon();
        if (weapon.getType().equals("Lance")) {
            return 1.5;
        }
        else {
            return 0.75;
        }
    }

    /**
     * calculates the damage dealt
     */
    @Override
    public double dealDamage(LoopManiaWorld world, BasicEnemy enemy) {
        double damage = dealDamageBeforeClassModifiers(world, enemy);
        damage *= handleClassAttack(world);
        damage *= handleCritical();
        Random rand = new Random();
        if (rand.nextInt(100) < 20) {
            damage = 150;
        }

        WeaponItem weaponItem = (WeaponItem) world.getWeapon();
        enemy.takeDamage(damage, weaponItem);
        return damage;
    }


    /**
     * calulates the damage taken
     */
    @Override
    public double takeDamage(LoopManiaWorld world, BasicEnemy enemy) {
        setHealth(getHealth() - damageTakenBeforeClassModifiers(world, enemy));
        return damageTakenBeforeClassModifiers(world, enemy);
    }

    /**
     * handles the max health of the character
     */
    public void handleMaxHealth() {
        if (this.getMaxHp() == 1) {
            setBaseHealth(110);
        } else if (this.getMaxHp() == 2) {
            setBaseHealth(120);
        } else if (this.getMaxHp() == 3) {
            setBaseHealth(130);
        }
    }
}
