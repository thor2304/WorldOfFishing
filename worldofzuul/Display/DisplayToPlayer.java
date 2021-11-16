package worldofzuul.Display;

import worldofzuul.Display.DisplayInterfaces.*;

import java.util.Map;

/**
 * <h2>This is the class responsible for everything displayed to the player</h2>
 * <h2>
 * How to Use: </h2>
 * <h3>When displaying using existing functions:</h3>
 * Make sure an instance of {@link DisplayToPlayer} exists, good practice is to call it display
 * <p> use this instance to call the desired method</p>
 * If you only need to display a simple message with a string you <b><i>can</i></b> use
 * {@link #displaySimpleInfo(String)} but you <b>really</b> should consider if it is a type of message which will be displayed
 * in a special way in iteration 2
 * <p></p>
 *
 * <h3>Display with a new function</h3>
 * Good practice when modifying this class consists of 3 steps:
 * <p><b>Step 0: </b></p>
 * Do a <i><b>Git commit</b></i> with your current work, so the changes in this class exist in a separate commit.
 * This makes it easier to troubleshoot if it doesnt work!
 * <p></p>
 *
 * <p><b>Step 1: </b></p>
 * Create an interface with a fitting name that describes the method(s) to implement.
 * This interface should be put in the package DisplayInterfaces.(an example of this is {@link DisplaySimpleInfo})
 * and should ideally have a <b>docstring</b> with information
 * about what information should be displayed by the methods when implemented
 * <p>
 * An interface cannot describe static methods (as far as our testing goes),
 * so avoid that when describing your interface.
 * </p>
 * <p>
 * Remember to add the interface to the list of implemented interfaces in {@link DisplayToPlayer}
 * </p>
 * <p></p>
 *
 * <p><b>Step 2: </b></p>
 * Implement the method in DisplayToPlayer.
 * <p>
 * if you are just being thorough and implementing a seperate method for a simple Display of a string
 * feel free to use {@link #displaySimpleInfo(String)} as this help make the transition to iteration 2 smoother
 * </p>
 * <p></p>
 *
 * <p><b>Step 3: </b></p>
 * Remember to call your beautiful new method from the class you want,
 * see the point in this doc called <i>When displaying using existing functions:</i> for info on how to make that work.
 * <p>
 * The very last thing to do is to make a <b><i>Git commit</i></b> with the final result of this change, remember to include:
 * the name of your new Interface and perhaps your new method. This makes it easier to Code review
 * </p>
 */
public class DisplayToPlayer implements DisplaySimpleInfo, DisplayCommands, DisplayEndAndWelcome,
        CommandExecutionTexts, DisplayAddedGold, DisplayFishingResult, DisplayTooManyHours {
    private CommandWords commandWords;

    public DisplayToPlayer(CommandWords commandWords) {
        this.commandWords = commandWords;
    }

    /**
     * This method displays a simple string to the user
     * <p>
     * In iteration 1 this seems silly, but in iteration 2 will become quite handy
     * </p>
     * Specific for iteration 2 think about max string length
     *
     * @param info The string to display to the user
     */
    @Override
    public void displaySimpleInfo(String info) {
        System.out.println(info);
    }

    /**
     * This method displays all the available commands/actions to the user
     */
    @Override
    public void displayAllCommands() {
        for (String command : this.commandWords.getValidCommands().keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

    /**
     * This method is used for displaying a single command to the user.
     * Perhaps useless, remove at will
     *
     * @param text        Displayed in the beginning of the display to the user
     * @param commandWord the {@link CommandWord} to display
     */
    @Override
    public void displayCommand(String text, CommandWord commandWord) {
        System.out.println(text + commandWord);
    }


    /**
     * Is responsible for showing the player a welcome screen at the beginning of the game
     */
    @Override
    public void displayWelcome(Tile currentTile) {
        System.out.println("\n"); //two newlines

        System.out.println(ASCIIArt.WelcomeScreen);

        System.out.println(ASCIIArt.Boat);
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("You´re a fisherman, who needs to catch some fish, to earn money for your new started fish company. ");
        System.out.println("I wish you the best of luck!");
        System.out.println("Lets start the fishing now: \n");
        System.out.println(currentTile.getLongDescription());
    }

    /**
     * Shows the player a goodbye message and ends the game
     */
    @Override
    public void displayGoodbye() {
        System.out.println("Thank you for playing.  Good bye.");
    }


    /**
     * Tells the player, that the attempted action cannot be done by writing:
     * <p><b><i>I don't know what you mean... </i></b></p>
     * followed by the passed in String as bonusinfo
     *
     * @param bonusInfo is added to the end of the default Unknown command text
     */
    @Override
    public void unknownCommand(String bonusInfo) {
        System.out.println("I don't know what you mean... " + bonusInfo);
    }

    /**
     * Displays a help text to the player
     * <p>Should be called when the player types "help"
     * <p>Ends with printing a list of the valid commands
     */
    @Override
    public void displayHelpText() {
        System.out.println("You are lost. You are alone. You drift");
        System.out.println("at sea.");
        System.out.println();
        System.out.println("Your command words are:");
        this.displayAllCommands();
    }

    /**
     * Prints out the following lines:
     * <p>You've hit land!</p>
     * <p>You've done goofed</p>
     */
    @Override
    public void outOfBoundsText() {
//        System.out.println("You've hit land!");
//        System.out.println("You've done goofed");
        this.displaySimpleInfo("You've hit land! \n You've done goofed");
    }

    /**
     * Displays the description of the tile we are in
     * <p>in iteration 1 this is simply a call to {@link #displaySimpleInfo(String)} with the string passed in</p>
     * however this will likely change in iteration 2
     *
     * @param tileDescription
     */
    @Override
    public void displayTileDescription(String tileDescription) {
        this.displaySimpleInfo(tileDescription);
    }

    /**
     * Very simple in iteration 1
     * <p>it uses {@link #displaySimpleInfo(String)} to display:</p>
     * <b>"> "</b>
     * <p>Is called from {@link Parser}</p>
     */
    @Override
    public void readyForNextCommand() {
        this.displaySimpleInfo("> ");
    }

    @Override
    public void displayNewGold(double amount) {
        this.displaySimpleInfo("You got " + amount + " gold for selling all your fish");
    }

    @Override
    public void displayGold(double amount) {
        this.displaySimpleInfo("You have " + amount + " gold");
    }

    /**
     * <p>The method displayFishingResult is used to display how many fished the boat caught and how long it took</p>
     * <p>The method uses {@link #displaySimpleInfo(String)} to display:</p>
     * <p>The fishing went well! you caught " + catchAmount + "and it took " + hoursFished</p>
     *
     * @param catchAmount catchAmount is the number of fish caught by the boat
     * @param hoursFished hoursFished is the hours it takes the boat to complete the fishing
     */
    @Override
    public void displayFishingResult(double catchAmount, int hoursFished, Fish fishType) {
        this.displaySimpleInfo("The fishing went well! you caught " + catchAmount + " " + fishType + " and it took " + hoursFished);

    }

    /**
     * Used to display to the player how many fish are currently in storage
     *
     * @param fishMap the map containing fish and number of fish
     */
    @Override
    public void displayCurrentFish(Map<Fish, Integer> fishMap) {
        String printString = "";
        for (Fish fish : fishMap.keySet()) {
            printString += "You have caught " + fishMap.get(fish) + " " + fish + "\n";
        }
        this.displaySimpleInfo(printString);
    }

    public void displayCurrentFish(double fish) {
        this.displaySimpleInfo("You have caught " + fish + " fish");
    }

    @Override
    public void DisplayTooManyHours() {
        this.displaySimpleInfo("You have fished to many hours!" + "\n" + "You can only fish for 12 hours.");
    }
}
