package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {

    private Scene scene;
    private Stage primaryStage;

    private Parent mainMenuRoot;
    private Parent gameModeRoot;
    private Parent worldSelectorRoot;
    private Parent classSelectorRoot;
    private Parent profileSelectorRoot;
    private Parent gameOverRoot;
    private Parent shopRoot;
    private Parent winRoot;
    private Parent battleRoot;
    private Parent skillTreeRoot;

    private DifficultyController difficultyController;
    private ClassSelectorController classSelectorController;
    private SkillTreeController skillTreeController;

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);
        
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        this.mainMenuRoot = menuLoader.load();

        this.difficultyController = new DifficultyController();
        FXMLLoader difficultyLoader = new FXMLLoader(getClass().getResource("DifficultyView.fxml"));
        difficultyLoader.setController(difficultyController);
        this.gameModeRoot = difficultyLoader.load();

        WorldSelectorController worldSelectorController = new WorldSelectorController();
        FXMLLoader worldSelectorLoader = new FXMLLoader(getClass().getResource("WorldSelector.fxml"));
        worldSelectorLoader.setController(worldSelectorController);
        this.worldSelectorRoot = worldSelectorLoader.load();

        this.classSelectorController = new ClassSelectorController();
        FXMLLoader classSelectorLoader = new FXMLLoader(getClass().getResource("ClassSelector.fxml"));
        classSelectorLoader.setController(classSelectorController);
        this.classSelectorRoot = classSelectorLoader.load();

        ProfileSelectorController profileSelectorController = new ProfileSelectorController();
        FXMLLoader profileSelectorLoader = new FXMLLoader(getClass().getResource("ProfileSelector.fxml"));
        profileSelectorLoader.setController(profileSelectorController);
        this.profileSelectorRoot = profileSelectorLoader.load();

        this.skillTreeController = new SkillTreeController();
        FXMLLoader skillTreeLoader = new FXMLLoader(getClass().getResource("SkillTree.fxml"));
        skillTreeLoader.setController(skillTreeController);
        this.skillTreeRoot = skillTreeLoader.load();

        GameOverController gameOverController = new GameOverController(primaryStage);
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        gameOverLoader.setController(gameOverController);
        this.gameOverRoot = gameOverLoader.load();

        WinController winController = new WinController(primaryStage);
        FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinView.fxml"));
        winLoader.setController(winController);
        this.winRoot = winLoader.load();

        this.scene = new Scene(mainMenuRoot);
        

        gameOverController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});

        winController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});

        // New flow of Milestone 3
        // Main Menu -> Profile Selector
        // If (saved profile) -> Game
        // If (not saved profile) -> World Selector -> Game Mode Selector -> Class Selector

        mainMenuController.setProfileSelectorController(profileSelectorController);
        mainMenuController.setProfileSelectorSwitcher(() -> {switchToRoot(scene, profileSelectorRoot, primaryStage);});

        profileSelectorController.setWorldSelectorSwitcher(() -> {switchToRoot(scene, worldSelectorRoot, primaryStage);});

        profileSelectorController.setProfileInUse();


        difficultyController.setClassSelectorSwitcher(() -> {switchToRoot(scene, classSelectorRoot, primaryStage);});

        profileSelectorController.setLoopManiaApplication(this);

        profileSelectorController.setWorldSelectorController(worldSelectorController);

        worldSelectorController.setLoopManiaApplication(this);
        worldSelectorController.setGameModeSwitcher(() -> {switchToRoot(scene, gameModeRoot, primaryStage);});

        // End of flow


        
        // deploy the main onto the stage
        // gameRoot.requestFocus();
        scene.getRoot().setStyle("-fx-font-family: 'Arial Unicode MS'");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public LoopManiaWorldController loadGameFromSavedProfile(JSONObject json, ProfileSelectorController profileSelectorController) throws FileNotFoundException, IOException {
        
        String filename = json.getString("world_type");
        
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader(filename);
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // Load the controllers which require a LoopManiaWorldController/LoopManiaWorld to create

        BattleController battleController = new BattleController(mainController.getWorld());
        FXMLLoader battleLoader = new FXMLLoader(getClass().getResource("BattleView.fxml"));
        battleLoader.setController(battleController);
        this.battleRoot = battleLoader.load();

        ShopController shopController = new ShopController(mainController);
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopView.fxml"));
        shopLoader.setController(shopController);
        this.shopRoot = shopLoader.load();


        // Connect the roots & set controllers
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainController.setBattleSwitcher(() -> {switchToRoot(scene, battleRoot, primaryStage);});
        mainController.setWinSwitcher(() -> {switchToRoot(scene, winRoot, primaryStage);});
        mainController.setShopSwitcher(() -> {
            switchToRoot(scene, shopRoot, primaryStage);
            shopController.resetSellItems();
            shopController.displayGoldValue();
        });
        mainController.setBattleController(battleController);

        battleController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        battleController.setGameOverSwitcher(() -> {switchToRoot(scene, gameOverRoot, primaryStage);});

        shopController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        profileSelectorController.setGameSwitcher(() -> {switchToRoot(scene, gameRoot, primaryStage);});

        mainController.setSkillTreeController(skillTreeController);
        mainController.setSkillTreeSwitcher(() -> {switchToRoot(scene, skillTreeRoot, primaryStage);});
        skillTreeController.setLoopManiaWorldController(mainController);
        skillTreeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return mainController;
    }

    public LoopManiaWorldController loadGameFromNewProfile(String map, WorldSelectorController worldSelectorController) throws FileNotFoundException, IOException {

        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader(map);
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // Load the controllers which require a LoopManiaWorldController/LoopManiaWorld to create

        BattleController battleController = new BattleController(mainController.getWorld());
        FXMLLoader battleLoader = new FXMLLoader(getClass().getResource("BattleView.fxml"));
        battleLoader.setController(battleController);
        this.battleRoot = battleLoader.load();

        ShopController shopController = new ShopController(mainController);
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopView.fxml"));
        shopLoader.setController(shopController);
        this.shopRoot = shopLoader.load();

        // Connect the roots & set controllers
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainController.setBattleSwitcher(() -> {switchToRoot(scene, battleRoot, primaryStage);});
        mainController.setWinSwitcher(() -> {switchToRoot(scene, winRoot, primaryStage);});
        mainController.setShopSwitcher(() -> {
            switchToRoot(scene, shopRoot, primaryStage);
            shopController.resetSellItems();
            shopController.displayGoldValue();
        });
        mainController.setBattleController(battleController);

        battleController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        battleController.setGameOverSwitcher(() -> {switchToRoot(scene, gameOverRoot, primaryStage);});

        shopController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Possibly worldSelectorController as well??
        difficultyController.setLoopManiaWorldController(mainController);
        classSelectorController.setLoopManiaWorldController(mainController);
        skillTreeController.setLoopManiaWorldController(mainController);
        classSelectorController.setGameSwitcher(() -> {switchToRoot(scene, gameRoot, primaryStage);});

        mainController.setSkillTreeController(skillTreeController);
        mainController.setSkillTreeSwitcher(() -> {switchToRoot(scene, skillTreeRoot, primaryStage);});
        skillTreeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.startTimer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return mainController;
    }

    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        scene.getRoot().setStyle("-fx-font-family: 'Arial Unicode MS'");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 