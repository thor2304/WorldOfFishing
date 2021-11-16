package worldofzuul.Display;

import worldofzuul.Domain.Tile;

public class GameLoop {
    public DisplayToPlayer display;
    private Parser parser;

    /**
     * No args constructor, Currently the only constructor available.
     * A new game should not take any arguments.
     * <p>It creates an instance of  {@link Parser} and {@link DisplayToPlayer}, to use for itself.</p>
     * It also creates a net of {@link Tile Tiles} which are technically stored only because they refer to each other
     */
    public GameLoop() {
        this.parser = new Parser();
        this.display = new DisplayToPlayer(parser.getCommandWords());
        //init Domain which inits Game and all others
    }

    public void play() {
        //printWelcome();
        display.displayWelcome(this.currentTile);


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
            //System.out.println("I don't know what you mean...");
            display.unknownCommand("");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            //printHelp();
            display.displayHelpText();
        } else if (commandWord == CommandWord.GO) {
            goTile(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.FISHTILE) {
            fish(command);
        } else if (commandWord == CommandWord.SHOWFISH) {
            boat.showFish();
        } else if (commandWord == CommandWord.SHOWGOLD) {
            boat.showGold();
        } else if (commandWord == CommandWord.SELL) {
            boat.sellFish();
        } else if (commandWord == CommandWord.TIME) {
            timeInfo(command);
        }
        return wantToQuit;
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            //System.out.println("Quit what?");
            display.unknownCommand("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    public void timeInfo(Command command) {
        if (!command.hasSecondWord()) {
            display.displaySimpleInfo("" + boat.getHoursToFish());
        } else {
            String secondWord = command.getSecondWord();
            if (secondWord == "set") {
                //maybe do something with this?
            }
        }
    }

}
