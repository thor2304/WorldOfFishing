package oop.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import oop.javafxtest.worldofzuul.Display.Command;
import oop.javafxtest.worldofzuul.Display.CommandWord;
import oop.javafxtest.worldofzuul.Display.GameLoop;
import oop.javafxtest.worldofzuul.Runner;

import java.util.Map;

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
    FlowPane fishingHoursDisplayPane;

    @FXML
    Label currentHoursToFishLabel;

    @FXML
    Label currentGoldLabel;

    @FXML
    Slider fishingHoursSlider;

    @FXML
    private void initialize()
    {
        initGoldLabel();
        initFishLabel();
        updateFishCountLabels();
    }

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
        initFishLabel();
        //Remember to also update the display to the right
    }

    @FXML
    protected void hoursToFishChanging(){
        // change the hours to fish
        for (Node node : fishingHoursDisplayPane.getChildren()){
            node.setVisible(false);
        }
        fishingHoursSlider.setVisible(!fishingHoursSlider.isVisible());


        System.out.println("trying my best");

        initFishLabel();
    }

    @FXML
    protected void sliderDone(){
        fishingHoursSlider.setVisible(!fishingHoursSlider.isVisible());

        Runner.gameLoop.setBoatHoursToFish( (int) fishingHoursSlider.getValue());

        initFishLabel();

        for (Node node : fishingHoursDisplayPane.getChildren()){
            node.setVisible(true);
        }
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
        initFishLabel();
        initGoldLabel();
        updateFishCountLabels();
    }

    private void initFishLabel(){
        currentHoursToFishLabel.setText("" + Runner.gameLoop.getHoursTofish());
    }

    private void initGoldLabel(){
        currentGoldLabel.setText("" + Runner.gameLoop.getCurrentGold());
    }

    private void updateFishCountLabels(){
        Map<String, Integer> caughtFish = Runner.gameLoop.getBoatCaughtFish();
        makrelCount.setText("" + caughtFish.get("Makrel"));
        laksCount.setText("" + caughtFish.get("Laks"));
        sildCount.setText("" + caughtFish.get("Sild"));
        ålCount.setText("" + caughtFish.get("Ål"));
    }
}