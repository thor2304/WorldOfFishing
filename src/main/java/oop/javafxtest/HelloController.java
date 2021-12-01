package oop.javafxtest;

import javafx.beans.binding.Bindings;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import oop.javafxtest.worldofzuul.Display.Command;
import oop.javafxtest.worldofzuul.Display.CommandWord;
import oop.javafxtest.worldofzuul.Display.GameLoop;
import oop.javafxtest.worldofzuul.Domain.Domain;
import oop.javafxtest.worldofzuul.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class HelloController {

    ImageView imageBoatView;

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
    GridPane baseGridPane;

    @FXML
    Label dailyExpensesLabel;

    @FXML
    Label checkWinConditionLabel;

    @FXML
    private void initialize()
    {
        initGoldLabel();
        initFishLabel();
        updateFishCountLabels();
        updateDailyExpensesLabel();
        String pathBoat = "src/main/resources/oop/javafxtest/images/Boat.png";
        File boatFile = new File(pathBoat);
        String pathWaterTile = "src/main/resources/oop/javafxtest/images/WaterTile.jpg";
        File waterTile = new File(pathWaterTile);

        try{
            Image waterTileImageTemp = new Image(new FileInputStream(waterTile));
            Image boatImage = new Image(new FileInputStream(boatFile));

            for (int i = 0; i < Domain.getSettingsBoardSize(); i++) {
                ColumnConstraints colum1 = new ColumnConstraints();
                colum1.setPercentWidth(100);
                baseGridPane.getColumnConstraints().add(colum1);

                RowConstraints row1 = new RowConstraints();
                row1.setPercentHeight(100);
                baseGridPane.getRowConstraints().add(row1);
            }

            baseGridPane.getColumnConstraints().remove(0);
            baseGridPane.getColumnConstraints().remove(0);
            baseGridPane.getRowConstraints().remove(0);

            double tileHeight = baseGridPane.getPrefHeight()/ baseGridPane.getRowCount();
            double tileWidth = baseGridPane.getPrefWidth() / baseGridPane.getColumnCount();

            for (int i = 0; i < Domain.getSettingsBoardSize(); i++) {
                for (int j = 0; j < Domain.getSettingsBoardSize(); j++) {

                    ImageView waterTileViewTemp = new ImageView();
                    waterTileViewTemp.setImage(waterTileImageTemp);

                    waterTileViewTemp.setFitWidth(tileWidth);
                    waterTileViewTemp.setFitHeight(tileHeight);

                    baseGridPane.add(waterTileViewTemp,j,i,1,1);
                }
            }

            imageBoatView = new ImageView();
            imageBoatView.setImage(boatImage);
            imageBoatView.setFitHeight(tileHeight);
            imageBoatView.setFitWidth(tileWidth);


            baseGridPane.add(imageBoatView, 1,1,1,1);
            move();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



    }
    @FXML
    protected void move(){
        baseGridPane.getChildren().remove(imageBoatView);
        baseGridPane.add(imageBoatView, Runner.gameLoop.currentXCoordinate(),Runner.gameLoop.currentYCoordinate(),1,1);

    }

    @FXML
    protected void goUp() {
        String textToDisplay = Runner.gameLoop.goTile("north");
        updateGeneralLabel(textToDisplay);

        move();
    }

    @FXML
    protected void goDown() {
        String textToDisplay = Runner.gameLoop.goTile("south");
        updateGeneralLabel(textToDisplay);
        move();
    }

    @FXML
    protected void goRight() {
        String textToDisplay = Runner.gameLoop.goTile("east");
        updateGeneralLabel(textToDisplay);
        move();
    }

    @FXML
    protected void goLeft() {
        String textToDisplay = Runner.gameLoop.goTile("west");
        updateGeneralLabel(textToDisplay);
        move();
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
    protected void tileProtection(){
        Runner.gameLoop.protecTile();
        generalInfoLabel.setText("You have protected this tile.");
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
        if (Runner.gameLoop.checkWinCondition()){
            checkWinConditionLabel.setVisible(true);
        }
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
        updateDailyExpensesLabel();
    }

    @FXML
    protected void keyPressed(KeyEvent keyEvent){
        String letter = keyEvent.getCode().toString();
        switch(letter){
            case "W": goUp();
            break;
            case "S": goDown();
            break;
            case "D": goRight();
            break;
            case "A": goLeft();
            default:
        }
    }

    private void initFishLabel(){
        currentHoursToFishLabel.setText("" + Runner.gameLoop.getHoursTofish());
    }

    private void initGoldLabel(){
        currentGoldLabel.setText("" + Runner.gameLoop.getCurrentGold());
    }

    private void updateDailyExpensesLabel(){
        dailyExpensesLabel.setText("" + Runner.gameLoop.getDailyExpenses());
    }

    private void updateFishCountLabels(){
        Map<String, Integer> caughtFish = Runner.gameLoop.getBoatCaughtFish();
        makrelCount.setText("" + caughtFish.get("Makrel"));
        laksCount.setText("" + caughtFish.get("Laks"));
        sildCount.setText("" + caughtFish.get("Sild"));
        ålCount.setText("" + caughtFish.get("Ål"));
    }
}