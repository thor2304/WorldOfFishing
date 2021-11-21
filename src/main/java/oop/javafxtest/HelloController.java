package oop.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import oop.javafxtest.worldofzuul.Display.Command;
import oop.javafxtest.worldofzuul.Display.CommandWord;
import oop.javafxtest.worldofzuul.Display.GameLoop;
import oop.javafxtest.worldofzuul.Runner;

public class HelloController {

    @FXML
    Label generalInfoLabel;

    @FXML
    Label makrelCount;

    @FXML
    Label sildCount;

    @FXML
    Label laksCount;

    @FXML
    Label ålCount;

    @FXML
    GridPane fishCaughtInfoBox;



    @FXML
    protected void goUp() {
        String textToDisplay = Runner.gameLoop.goTile("north");
        updateGeneralLabel(textToDisplay);
    }

    @FXML
    protected void goDown() {
        String textToDisplay = Runner.gameLoop.goTile("south");
        updateGeneralLabel(textToDisplay);
    }

    @FXML
    protected void goRight() {
        String textToDisplay = Runner.gameLoop.goTile("east");
        updateGeneralLabel(textToDisplay);
    }

    @FXML
    protected void goLeft() {
        String textToDisplay = Runner.gameLoop.goTile("west");
        updateGeneralLabel(textToDisplay);
    }

    @FXML
    protected void showHelp(){
        updateGeneralLabel(Runner.gameLoop.showHelp());
    }

    @FXML
    protected void restartGame(){
        Runner.run();
    }

    @FXML
    protected void fish(){
        updateGeneralLabel(Runner.gameLoop.fish());
        //Remember to also update the display to the right
    }

    @FXML
    protected void hoursToFishChanging(){
        // change the hours to fish
    }

    @FXML
    protected void sellAllFish(){
        updateGeneralLabel(Runner.gameLoop.sellAllFish());
    }

    @FXML
    protected void showFishCaught(){
        fishCaughtInfoBox.setVisible(!fishCaughtInfoBox.isVisible());
    }

    public void updateGeneralLabel(String newText){
        generalInfoLabel.setText(newText);
    }
}