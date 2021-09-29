package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;

import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * Controller for battle interface
 */
public class BattleController {

    private boolean isBattleFinished;

    private LoopManiaWorld world;
    // images
    private Image slugEnemyImage;
    private Image vampireEnemyImage;
    private Image zombieEnemyImage;
    private Image doggieBossImage;
    private Image elanMuskeImage;
    private Image heroImage;
    private Image campfireBuildingImage;
    private Image towerBuildingImage;

    private Image soldierImage;

    private List<BasicEnemy> aliveEnemies;

    private List<BasicEnemy> deadEnemies;
    
    private Timeline timeline;


    private MenuSwitcher gameSwitcher;

    @FXML
    private AnchorPane anchorPaneRoot;

    // main stage 
    @FXML
    private GridPane background;

    @FXML
    private StackPane character;

    @FXML
    private StackPane enemy;

    @FXML
    private Label message;

    private boolean isBattleStarted;
    private MenuSwitcher gameOverSwitcher;

    /**
     * Battle controller constructor
     * @param world
     */
    public BattleController(LoopManiaWorld world) {
        this.world = world;
        // Enemy images
        slugEnemyImage = new Image((new File("src/images/slug.png")).toURI().toString());
        zombieEnemyImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        vampireEnemyImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        
        // Boss images
        doggieBossImage = new Image((new File("src/images/doggie.png")).toURI().toString());
        elanMuskeImage = new Image((new File("src/images/ElanMuske.png")).toURI().toString());

        // Character/Hero image 
        heroImage = new Image((new File("src/images/human_new.png")).toURI().toString());

        // Supporting buildings images
        campfireBuildingImage = new Image((new File("src/images/campfire.png")).toURI().toString());
        towerBuildingImage = new Image((new File("src/images/tower.png")).toURI().toString());

        // Character support images
        //allied soldier image goes here
        soldierImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());

        aliveEnemies = new ArrayList<>();
        deadEnemies = new ArrayList<>();
        
        this.isBattleStarted = false;
        this.isBattleFinished = false;
    }
    
    /**
     * displays event status message
     * @param eventMessage
     */
    public void displayMessage(String eventMessage) {
        message.setText(eventMessage);
        
    }
    /**
     * Sets game switcher 
     * @param gameSwitcher
     */
    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * initialize stage
     */
    @FXML
    public void initialize() {
        
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        for (int x = 0; x < 18; x++) {
            for (int y = 0; y < 9; y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                background.add(groundView, x, y);
            }
        }
        //List<BasicEnemy> enemyFightQueue = world.getEnemyFightQueue();

        displayMessage("Press the Start Battle button!");
    }
    
    /**
     * Starts battle
     */
    public void startRound() {
        if (world.areAllies() && world.isTowerinRange() != null) {
            timeline = new Timeline();
            KeyFrame character = new KeyFrame(Duration.seconds(1.2),
            event -> {
                world.tickCharacter(this);
            });
    
            KeyFrame tower = new KeyFrame(Duration.seconds(2.4),
            event -> {
                world.tickTower(this);                
            });

            KeyFrame allies = new KeyFrame(Duration.seconds(3.6),
            event -> {
                world.tickAllies(this);
            });
    
            KeyFrame enemies = new KeyFrame(Duration.seconds(4.8),
            event -> {
                world.tickEnemies(this);
                if (world.getEnemyFightQueue().size() == 0 || world.getHealth() == 0) {
                    isBattleFinished = true;
                    world.resetZombieCase();
                    world.resetTranceCase();
                    timeline.stop(); 
                }
            });

            timeline.getKeyFrames().addAll(character,tower, allies, enemies);
        }
        else if (world.areAllies() || world.getWeapon() != null) {
            if (world.getWeapon().getType().equals("Staff")) {
                timeline = new Timeline();
                KeyFrame character = new KeyFrame(Duration.seconds(1.2),
                event -> {
                    world.tickCharacter(this);
                });
        
                KeyFrame allies = new KeyFrame(Duration.seconds(2),
                event -> {
                    world.tickAllies(this);
                });
        
                KeyFrame enemies = new KeyFrame(Duration.seconds(3.2),
                event -> {
                    world.tickEnemies(this);
                    if (world.getEnemyFightQueue().size() == 0 || world.getHealth() == 0) {
                        isBattleFinished = true;
                        world.resetZombieCase();
                        world.resetTranceCase();
                        timeline.stop(); 
                    }
                });
    
                timeline.getKeyFrames().addAll(character, allies, enemies);
            } else {
                timeline = new Timeline();
            KeyFrame character = new KeyFrame(Duration.seconds(0.2),
            event -> {
                world.tickCharacter(this);
            });
    
            KeyFrame enemies = new KeyFrame(Duration.seconds(1.6),
            event -> {
                world.tickEnemies(this);
                if (world.getEnemyFightQueue().size() == 0 || world.getHealth() == 0) {
                    isBattleFinished = true;
                    world.resetZombieCase();
                    world.resetTranceCase();
                    timeline.stop();
                    
                }
            });
            
            timeline.getKeyFrames().addAll(character, enemies);
            }
        }
        else if (world.isTowerinRange() != null) {
            timeline = new Timeline();
            KeyFrame character = new KeyFrame(Duration.seconds(1.2),
            event -> {
                world.tickCharacter(this);
            });
    
            KeyFrame tower = new KeyFrame(Duration.seconds(2.4),
            event -> {
                world.tickTower(this);
            });
    
            KeyFrame enemies = new KeyFrame(Duration.seconds(3.6),
            event -> {
                world.tickEnemies(this);
                if (world.getEnemyFightQueue().size() == 0 || world.getHealth() == 0) {
                    world.resetZombieCase();
                    world.resetTranceCase();
                    isBattleFinished = true;
                    timeline.stop(); 
                }
            });

            timeline.getKeyFrames().addAll(character, tower, enemies);
        } 
        else {
            timeline = new Timeline();
            KeyFrame character = new KeyFrame(Duration.seconds(0.8),
            event -> {
                world.tickCharacter(this);
            });
    
            KeyFrame enemies = new KeyFrame(Duration.seconds(1.6),
            event -> {
                world.tickEnemies(this);
                if (world.getEnemyFightQueue().size() == 0 || world.getHealth() == 0) {
                    world.resetZombieCase();
                    world.resetTranceCase();
                    isBattleFinished = true;
                    timeline.stop();
                }
            });
            
            timeline.getKeyFrames().addAll(character, enemies);
        }

        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();

    }

    /**
     * Load enemies character building images
     */
    void loadEverything() {
        List<BasicEnemy> enemyFightQueue = world.getEnemyFightQueue();
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        int x = 7;
        int y = 2;
        for (int i = 0; i < world.getAlliedSoldiers().size(); i++) {
            loadAlliedSoldier(x, y);
            y += 2;
        }

        if (world.isCampfireinRange()) {
            x = 2;
            y = 7;
            loadCampfire(x,y);

        } if (world.isTowerinRange() != null) {
            x = 2;
            y = 3;
            loadTower(x, y);
        }
        
        ImageView heroView = new ImageView(heroImage);
        heroView.setViewport(imagePart);
        background.add(heroView, 5, 4);

        x = 13;
        y = 3;
        for (BasicEnemy e : enemyFightQueue) {
            e.setBattleGroundCoordinates(x, y);
            loadEnemy(e, x, y);
            y += 2;
        }
    }

    /**
     * load enemy images
     */
    void loadEnemies() {
        int x = 13;
        int y = 3;

        for (BasicEnemy e : world.getEnemyFightQueue()) {
            e.setBattleGroundCoordinates(x, y);
            loadEnemy(e, x, y);
            y += 2;
        }
    }

    /**
     * load allied soldiers
     */
    void loadAllies() {
        int x = 7;
        int y = 2;
        for (int i = 0; i < world.getAlliedSoldiers().size(); i++) {
            if (world.getAlliedSoldiers().size() > 3) {
                x += i/3;
                y = 2 + 2 * (i % 3);
            }
            loadAlliedSoldier(x, y);
            y += 2;
        }
    }

    /**
     * start battle
     * @param event
     */
    @FXML
    void startBattle(ActionEvent event) {

        if (isBattleFinished) {
            displayMessage("Battle is finished! Press the Return to Game button.");
        }

        else if (!isBattleStarted) {
            isBattleStarted = !isBattleStarted;
            aliveEnemies = world.getEnemyFightQueue();
            
            //loadEverything();
            
            startRound();
        }
        else if (isBattleStarted && !isBattleFinished) {
            message.setText("Battle has already begun!");
        }
    }

    /**
     * remove enemy images upon defeat
     * @param x
     * @param y
     */
    public void removeEnemyImageByCoordinates(int x, int y) {
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        
        
        ImageView groundView = new ImageView(pathTilesImage);
        groundView.setViewport(imagePart);
        background.add(groundView, x, y);
            
    }

    /**
     * loads enemies into battleground
     * @param enemy
     * @param x
     * @param y
     */
    private void loadEnemy(BasicEnemy enemy, int x, int y) {
        String enemyType = enemy.getType();
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        ImageView enemyView = null;
        if (enemyType.equals("Zombie")) {
            enemyView = new ImageView(zombieEnemyImage);
        }
        else if (enemyType.equals("Slug")) {
            enemyView = new ImageView(slugEnemyImage);
        }
        else if (enemyType.equals("Vampire")){
            enemyView = new ImageView(vampireEnemyImage);
        } else if (enemyType.equals("Doggie")) {
            enemyView = new ImageView(doggieBossImage);
        } else {
            enemyView = new ImageView(elanMuskeImage);
        }
        enemyView.setViewport(imagePart);
        background.add(enemyView, x, y);
    }

    /**
     * load allied soldiers into battleground
     * @param x
     * @param y
     */
    private void loadAlliedSoldier(int x, int y) {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        ImageView allyView = new ImageView(soldierImage);
        allyView.setViewport(imagePart);
        background.add(allyView, x, y);
    }

    /**
     * load campfire into battleground
     * @param x
     * @param y
     */
    private void loadCampfire(int x, int y) {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        ImageView campFire = new ImageView(campfireBuildingImage);
        campFire.setViewport(imagePart);
        background.add(campFire, x, y);
    }

    /**
     * load tower into battleground
     * @param x
     * @param y
     */
    private void loadTower(int x, int y) {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        ImageView tower = new ImageView(towerBuildingImage);
        tower.setViewport(imagePart);
        background.add(tower, x, y);
    }


    /**
     * When battle is ended, returns to main game. Triggered by user.
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        // returns back to game
        if (world.getEnemyFightQueue().size() == 0) {
            world.setEnemyFightQueueFinished();
            removeImages();
            resetBattleStartedFinished(); 

            // handle the rewards for defeating enemies
            world.handleRewards(deadEnemies);
            resetDeadEnemies();
            gameSwitcher.switchMenu();
        }
        else {
            message.setText("You haven't finished the battle yet!");
        }

        
    }

    /**
     * reset dead enemies list
     */
    private void resetDeadEnemies() {
        deadEnemies = new ArrayList<>();
    }

    /**
     * reset battle started to finished
     */
    private void resetBattleStartedFinished() {
        this.isBattleStarted = false;
        this.isBattleFinished = false;
        displayMessage("Press the Battle button to begin!");
    }

    /**
     * remove images
     */
    public void removeImages() {
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        
        for (int x = 0; x < 18; x++) {
            for (int y = 0; y < 9; y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                background.add(groundView, x, y);
            }
        }
    }

    /**
     * set dead enemies
     * @param dead
     */
    public void setDeadEnemies(List<BasicEnemy> dead) {
        this.deadEnemies = dead;
    }

    /**
     * get dead enemies
     * @return
     */
    public List<BasicEnemy> getDeadEnemies() {
        return this.deadEnemies;
    }

    /**
     * set alive enemies
     * @param alive
     */
    public void setAliveEnemies(List<BasicEnemy> alive) {
        this.aliveEnemies = alive;
    }

    /**
     * get alive enemies
     * @return List
     */
    public List<BasicEnemy> getAliveEnemies() {
        return this.aliveEnemies;
    }

    /**
     * add dead enemy to list
     * @param enemy
     */
    public void addDeadEnemy(BasicEnemy enemy) {
        deadEnemies.add(enemy); 
    }
    
    /**
     * set game over switcher
     * @param gameOverSwitcher
     */
    public void setGameOverSwitcher(MenuSwitcher gameOverSwitcher) {
        this.gameOverSwitcher = gameOverSwitcher;
    }

    /**
     * handle game over
     */
    public void handleGameOver() {
        gameOverSwitcher.switchMenu();
    }
}
