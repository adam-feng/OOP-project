package unsw.loopmania;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import test.DoggieTest;

/**
 * Shop controller
 */
public class ShopController {
    public static final int sellItemHeight = 4;
    public static final int sellItemWidth = 4;
    public static final int buyItemHeight = 4;
    public static final int buyItemWidth = 2;

    private LoopManiaWorldController mainController;
    private Shop shop;

    @FXML
    private GridPane BuyItems;

    @FXML
    private GridPane SellItems;

    @FXML
    private Button BuyButton;

    @FXML
    private Button SellButton;

    @FXML
    private Label ItemValue;

    @FXML
    private Label ShopTitle;

    @FXML
    private Label GoldAvailable;

    @FXML
    private Label instruction;

    private LoopManiaWorld world;

    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image potionImage;
    private Image oneRingImage;
    private Image doggieCoinImage;
    private Image shortSwordImage;
    private Image lanceImage;
    private Image andurilImage;
    private Image treeStumpImage;

    private MenuSwitcher gameSwitcher;

    private Item selectedItem;
    private List<Item> buyItems;

    private int potionsBought;
    private int protectiveItemsBought;

    /**
     * Shop controller constructor
     * @param mainController
     */
    public ShopController(LoopManiaWorldController mainController) {
        this.mainController = mainController;
        this.world = mainController.getWorld();
        this.shop = new Shop(world);
        this.buyItems = shop.getShopItems();

        // Item images
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        potionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        oneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/src_images_doggiecoin.png")).toURI().toString());
        shortSwordImage = new Image((new File("src/images/short_sword.png")).toURI().toString());
        lanceImage = new Image((new File("src/images/lance.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());

        potionsBought = 0;
        protectiveItemsBought = 0;

    }

    /**
     * Selects an item to sell or buy (set by ActionEvent)
     */
    public void setSelectedItem(Item i) {
        this.selectedItem = i;
    }

    /**
     * Initialize buy and sell items
     */
    public void initialize() {
        GoldAvailable.setText(Integer.toString(world.getGold()));
        ShopTitle.setStyle("-fx-font: 24 arial;");

        // initialise sell items 
        resetSellItems();

        // initialise buy items
        for (int i = 0; i < this.buyItems.size(); i++) {
            int x = 1;
            int y = i/2;
            if (i%2 == 0) {
                x = 0;
            } 
            setBuySellItems(this.buyItems.get(i), x, y, "buy");
        }

    }

    /**
     * Resets sell items to reflect buy or sell actions
     */
    public void resetSellItems() {
        SellItems.getChildren().clear();
        if (world.getUnequippedInventoryItems().size() > 0) {
            for (int i = 0; i < world.getUnequippedInventoryItems().size(); i++) {
                int x = i % sellItemHeight;
                int y = i / sellItemWidth;
                setBuySellItems(world.getUnequippedInventoryItems().get(i), x, y, "sell");
            }
        }
    }

    /**
     * Maps item type to its corresponding image, and calls button creation method
     */
    public void setBuySellItems(Item i, int x, int y, String action) {
        String type = i.getType();
        GridPane g = BuyItems;
        if (action.equals("sell")) {
            g = SellItems;
        }

        if (type.equals("Sword")) {
            g.add(createButton(i, swordImage, action), x, y);
        } else if (type.equals("Staff")) {
            g.add(createButton(i, staffImage, action), x, y);
        } else if (type.equals("Stake")) {
            g.add(createButton(i, stakeImage, action), x, y);
        } else if (type.equals("Armour")) {
            g.add(createButton(i, armourImage, action), x, y);
        } else if (type.equals("Shield")) {
            g.add(createButton(i, shieldImage, action), x, y);
        } else if (type.equals("Helmet")) {
            g.add(createButton(i, helmetImage, action), x, y);
        } else if (type.equals("HealingPotion")) {
            g.add(createButton(i, potionImage, action), x, y);
        } else if (type.equals("OneRing")) {
            g.add(createButton(i, oneRingImage, action), x, y);
        } else if (type.equals("ShortSword")) {
            g.add(createButton(i, shortSwordImage, action), x, y);
        } else if (type.equals("Lance")) {
            g.add(createButton(i, lanceImage, action), x, y);
        } else if (type.equals("DoggieCoin")) {
            g.add(createButton(i, doggieCoinImage, action), x, y);
        } else if (type.equals("Anduril")) {
            g.add(createButton(i, andurilImage, action), x, y);
        } else if (type.equals("TreeStump")) {
            g.add(createButton(i, treeStumpImage, action), x, y);
        }
    }

    /**
     * Create button item for a buy or sell item.
     * Action on this button will select the current item.
     */
    public Button createButton(Item i, Image image, String action) {
        ImageView view = new ImageView(image);
        Button b = new Button("", view);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                displayInstruction(i.getType()+" selected to "+action+".");
                setSelectedItem(i);
                if (action.equals("buy")) {
                    showItemBuyValue(i);
                } else {
                    showItemSellValue(i);
                }
            }
        };

        b.setOnAction(event);
        return b;
    }

    /**
     * Display any updates so the player knows what is going on
     */
    public void displayInstruction(String eventMessage) {
        instruction.setText(eventMessage);
    }

    /**
     * Display item's buy or sell value
     */
    public void displayItemValue(int value) {
        ItemValue.setText(Integer.toString(value));
    }

    /**
     * Display how much gold the player has
     */
    public void displayGoldValue() {
        GoldAvailable.setText(Integer.toString(world.getGold()));
    }

    /**
     * Get item's buy price
     */
    public void showItemBuyValue(Item i) {
        // update Item Value label
        displayItemValue(i.getBuyPrice());
    }

    /**
     * Get item's sell price
     */
    public void showItemSellValue(Item i) {
        displayItemValue(i.getSellPrice());
    }

    /**
     * Buys an item
     * Calls buyItem method in shop class
     * Resets sellItems when an item is bought 
     * Does nothing if there is no selected item, or inventory is full
     */
    @FXML
    void BuyItem(ActionEvent event) {
        if (selectedItem == null) {
            displayInstruction("You have not selected an item yet!");
        } else if (world.getUnequippedInventoryItems().size() >= 16) {
            displayInstruction("Inventory is full.");

        } else if (!underLimit(selectedItem)) {
            if (selectedItem.getType().equals("HealingPotion")) {
                displayInstruction("Cannot buy this many potions in Survival Mode!");
            }
            else {
                displayInstruction("Cannot buy this many protective items in Berserker Mode!");
            }
        
        } else {
            String itemType = selectedItem.getType();

            String message = shop.buyItem(selectedItem.getType());
            displayInstruction(message);
            // add to inventory (updated in shop)
            mainController.loadItems(shop.getItemToBuy());
            resetSellItems();
            displayGoldValue();
            setSelectedItem(null);

            System.out.print("Selected item: ");
            System.out.println(itemType);
            if (itemType.equals("HealingPotion")) {
                potionsBought += 1;
            }
            else if (itemType.equals("Armour") || itemType.equals("Shield") || itemType.equals("Helmet")) {
                protectiveItemsBought += 1;
            }
        }
    }

    /**
     * Sells an item
     * calls sellItem method in shop class
     * Resets sellItems when an item is sold 
     * Does nothing if there is no selected item
     */
    @FXML
    void SellItem(ActionEvent event) {
        if (selectedItem == null) {
            displayInstruction("You have not selected an item yet!");
        } else if (selectedItem.getType().equals("OneRing") || selectedItem.getType().equals("Anduril") || selectedItem.getType().equals("TreeStump")) {
            displayInstruction("You can NOT sell this item!");
        } 
        else {
            displayInstruction(shop.sellItem(selectedItem));
            resetSellItems();
            displayGoldValue();
            setSelectedItem(null);
        }
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * Returns to main game upon action
     */
    @FXML
    void SwitchToGame() throws IOException {
        String gameMode = mainController.getGameMode();
        System.out.print("Game mode: ");
        System.out.println(gameMode);
        System.out.print("Potions bought: ");
        System.out.println(potionsBought);
        System.out.print("Protective items bought: ");
        System.out.println(protectiveItemsBought);
        
        potionsBought = 0;
        protectiveItemsBought = 0;
        gameSwitcher.switchMenu();
    }

    /**
     * Prevent item selling or buying depending on difficulty mode 
     * @param item
     * @return
     */
    private boolean underLimit(Item item) {
        String gameMode = mainController.getGameMode();

        String itemType = item.getType();

        if (gameMode.equals("Berserker")) {
            boolean protectiveItem = (
                itemType.equals("Armour") ||
                itemType.equals("Shield") ||
                itemType.equals("Helmet")
            );
            return (
                (protectiveItem && protectiveItemsBought < 1)
                || (!protectiveItem)
            );
        }
        else if (gameMode.equals("Survival")) {
            return (
                (itemType.equals("HealingPotion") &&
                potionsBought < 1) ||
                (!itemType.equals("HealingPotion"))
            );
        }
        else {
            return true;
        }
        
    }

}

