package worldofzuul.Display;

import worldofzuul.Domain.Domain;
import worldofzuul.Errors.OutOfBoundsError;
import worldofzuul.Errors.TileProtectedFromFishingError;
import worldofzuul.Errors.TooManyHoursToFishError;

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

    public void play() {
        display.displayWelcome(domain.getCurrentTileDescription());

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //finished was true and therefore the game has ended
        display.displayGoodbye();
    }

    private boolean processCommand(Command command) {
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

    private void sellFish() {
        double newGold = domain.returnGoldForSoldFish();
        display.displayNewGold(newGold);
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

    private void fish(Command command) {
        try{
            Map<String, Integer> fishingResult = domain.fishCurrentTile(command.getSecondWord());
            display.displayFishingResult(fishingResult);
        }catch (NumberFormatException e){
            display.displaySimpleInfo(e.getMessage());
        }catch (TooManyHoursToFishError e){
            display.DisplayTooManyHours();
        }catch (TileProtectedFromFishingError e){
            display.displaySimpleInfo(e.getMessage());
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

}
