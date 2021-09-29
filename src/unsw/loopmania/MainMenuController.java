package unsw.loopmania;

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * controller for the main menu.
 */
public class MainMenuController {

    private MenuSwitcher profileSelectorSwitcher;
    private ProfileSelectorController profileSelectorController;

    @FXML
    void switchToSelectProfile(ActionEvent event) throws FileNotFoundException {
        profileSelectorController.loadProfiles();
        profileSelectorSwitcher.switchMenu();
    }

    public void setProfileSelectorController(ProfileSelectorController profileSelectorController) {
        this.profileSelectorController = profileSelectorController;
    }

    public void setProfileSelectorSwitcher(MenuSwitcher profileSelectorSwitcher) {
        this.profileSelectorSwitcher = profileSelectorSwitcher;
    }

}
