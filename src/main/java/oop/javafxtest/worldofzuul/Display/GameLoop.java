package oop.javafxtest.worldofzuul.Display;

import oop.javafxtest.worldofzuul.Domain.Domain;
import oop.javafxtest.worldofzuul.Errors.OutOfBoundsError;
import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;
import oop.javafxtest.worldofzuul.Errors.TooManyHoursToFishError;

import java.util.Map;

/**Has to be public because it is called by Runner
 */
public class GameLoop {
    public DisplayToPlayer display;
    private Parser parser;
    Domain domain;

    /**
     * No args constructor, Currently the only constructor available.
     * A new game should not take any arguments.
     * <p>It creates an instance of  {@link Parser} and {@link DisplayToPlayer}, to use for itself.</p>
     */
    public GameLoop() {
        this.parser = new Parser();
        this.display = new DisplayToPlayer();
        //init Domain which inits Game and all others
        this.domain = new Domain();
    }

    public String play() {
        return display.displayWelcome(domain.getCurrentTileDescription());
        //finished was true and therefore the game has ended
//        display.displayGoodbye();
    }

    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            display.unknownCommand("");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            display.displayHelpText(parser.getCommandWords().getValidCommands().keySet());
        } else if (commandWord == CommandWord.GO) {
            goTile(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.FISHTILE) {
            fish(command);
        } else if (commandWord == CommandWord.SHOWFISH) {
            showFish();
        } else if (commandWord == CommandWord.SHOWGOLD) {
            showGold();
        } else if (commandWord == CommandWord.SELL) {
            sellFish();
        } else if (commandWord == CommandWord.TIME) {
            timeInfo(command);
        }
        return wantToQuit;
    }

    private String sellFish() {
        double newGold = domain.returnGoldForSoldFish();
        return display.displayNewGold(newGold);
    }

    public String sellAllFish(){
        return sellFish();
    }

    private void showGold() {
        double boatGold = domain.getBoatGold();
        display.displayGold(boatGold);
    }

    /**Will display to the player how many fish is currently in storage
     */
    private void showFish() {
        Map<String, Integer> caughtFish = domain.getBoatCaughtFish();
        display.displayCurrentFish(caughtFish);

    }

    public String showHelp(){
        return display.displayHelpText(parser.getCommandWords().getValidCommands().keySet());
    }

    private void fish(Command command) {
        try{
            Map<String, Integer> fishingResult = domain.fishCurrentTile();
            display.displayFishingResult(fishingResult);
        }catch (NumberFormatException e){
            display.displaySimpleInfo(e.getMessage());
        }
//        catch (TooManyHoursToFishError e){
//            display.DisplayTooManyHours();
//        }
        catch (TileProtectedFromFishingError e){
            display.displaySimpleInfo(e.getMessage());
        }
    }

    public String fish() {
        try{
            Map<String, Integer> fishingResult = domain.fishCurrentTile();
            return display.displayFishingResult(fishingResult);
        }catch (TileProtectedFromFishingError e){
            return display.displaySimpleInfo(e.getMessage());
        }
    }

    private void goTile(Command command) {
        if (!command.hasSecondWord()) {
            display.unknownCommand("Go where?");
            return;
        }

        try{
            String tileDescription = domain.goInDirection(command.getSecondWord());
            display.displayTileDescription(tileDescription);
        }catch (OutOfBoundsError e){
            display.outOfBoundsText();
        }
    }

    public String goTile(String direction) {
        try{
            String tileDescription = domain.goInDirection(direction);
            return display.displayTileDescription(tileDescription);
        }catch (OutOfBoundsError e){
            return display.outOfBoundsText();
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            display.unknownCommand("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private void timeInfo(Command command) {
        if (!command.hasSecondWord()) {
            display.displaySimpleInfo("" + domain.getBoatHoursToFish());
        } else {
            String secondWord = command.getSecondWord();
            if (secondWord == "set") {
                //maybe do something with this?
            }
        }
    }





    public int getHoursTofish(){
        return domain.getBoatHoursToFish();
    }

    public double getCurrentGold(){
        return domain.getBoatGold();
    }

    public Map<String, Integer> getBoatCaughtFish(){
        return domain.getBoatCaughtFish();
    }

    public void setBoatHoursToFish(int hoursToFish){
        domain.setBoatHoursToFish(hoursToFish);
    }

    public int currentYCoordinate() {
        return domain.getCurrentYCoordinate();
    }

    public int currentXCoordinate() {
        return domain.getCurrentXCoordinate();
    }

}
