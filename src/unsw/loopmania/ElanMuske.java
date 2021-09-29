package unsw.loopmania;

/**
 * Elan Muske - a Boss enemy type
 */
public class ElanMuske extends Boss {

    /**
     * Elan Muske constructor
     * @param position
     */
    public ElanMuske(PathPosition position) {
        super(position, 1, 1, 40, 100, "ElanMuske");
    }

    /**
     * Move Elan Muske
     */
    @Override
    public void move(int tick, LoopManiaWorld world) {
        moveUpPath();
    }

    /**
     * Take damage from character
     */
    @Override
    public void takeDamage(double damage, WeaponItem weapon) {
        this.setHealth(this.getHealth() - damage);
    }

    /**
     * Drops doggie coin upon chance
     */
    @Override
    public String dropItems() {
        return "DoggieCoin";
    }

    /**
     * Drop gold amount after defeat
     */
    @Override
    public int dropGold() {
        return 3000;
    }

    /**
     * Xp drop amount upon defeat
     */
    @Override
    public int dropXp() {
        return 10000;
    }

    /**
     * Heal enemies in support radius
     * @param world
     */
    public void healSurroundings(LoopManiaWorld world) {
        for (BasicEnemy e: world.getEnemies()) {
            e.setHealth(e.getHealth() + 5);
            if (e.getType().equals("Zombie")) {
                if (e.getHealth() > 20) {
                    e.setHealth(20);
                }
            } if (e.getType().equals("Slug")) {
                if (e.getHealth() > 15) {
                    e.setHealth(15);
                }
            } if (e.getType().equals("Vampire")) {
                if (e.getHealth() > 30) {
                    e.setHealth(30);
                }
            }
        } 
    }
    
    /**
     * Returns true if Elan Muske can spawn, false if not
     * @param world
     * @return
     */
    public boolean canSpawn(LoopManiaWorld world) {
        if (world.getNumCyclesCompleted() ==40 && world.getXp() > 10000) {
            for(BasicEnemy e: world.getEnemies()) {
                if (e.getType().equals("ElanMuske")) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
