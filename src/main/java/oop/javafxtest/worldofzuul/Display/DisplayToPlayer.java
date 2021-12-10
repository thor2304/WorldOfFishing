package oop.javafxtest.worldofzuul.Display;

import oop.javafxtest.worldofzuul.Display.DisplayInterfaces.*;

import java.util.Map;
import java.util.Set;

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
public class DisplayToPlayer implements DisplaySimpleInfo, DisplayEndAndWelcome,
        CommandExecutionTexts, DisplayAddedGold, DisplayFishingResult, DisplayTooManyHours {

    /**
     * This method displays a simple string to the user
     * <p>
     * In iteration 1 this seems silly, but in iteration 2 will become quite handy
     * </p>
     * Specific for iteration 2 think about max string length
     *
     * @param info The string to display to the user
     * @return
     */
    @Override
    public String displaySimpleInfo(String info) {
        System.out.println(info);
        return info;
    }


    /**
     * Is responsible for showing the player a welcome screen at the beginning of the game
     * @return
     */
    @Override
    public String displayWelcome(String descriptionOfCurrentTile) {
        System.out.println("\n"); //two newlines

        System.out.println(ASCIIArt.WelcomeScreen);

        System.out.println(ASCIIArt.Boat);

        String out =
                "You´re a fisherman, who needs to catch some fish, to earn money for your newly started fish company.\n" +
                "You need to earn a lot of money to win\n" +
                "So take care of the fish in your ocean, or you wont succeed\n"+
                "I wish you the best of luck!\n" +
                "Lets start the fishing now: \n\n" +
                descriptionOfCurrentTile;

        System.out.println(out);

        return out;
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
     * @return
     */
    @Override
    public String displayHelpText(Set<String> allValidCommands) {
        String out;
        System.out.println("You are lost. You are alone. You drift");
        out = "You are lost. You are alone. You drift\n";
        System.out.println("at sea.");
        out += "at sea.\n\n";
        System.out.println();
        System.out.println("Your command words are:");
        out += "Your command words are:\n";
        for (String command : allValidCommands) {
            System.out.print(command + "  ");
            out += command + "  ";
        }
        out += "\n";
        System.out.println();
        return out;
    }

    /**
     * Prints out the following lines:
     * <p>You've hit land!</p>
     * <p>You've done goofed</p>
     * @return
     */
    @Override
    public String outOfBoundsText() {
//        System.out.println("You've hit land!");
//        System.out.println("You've done goofed");
        return this.displaySimpleInfo("You've hit land! \n You've done goofed");
    }

    /**
     * Displays the description of the tile we are in
     * <p>in iteration 1 this is simply a call to {@link #displaySimpleInfo(String)} with the string passed in</p>
     * however this will likely change in iteration 2
     *
     * @param tileDescription
     * @return
     */
    @Override
    public String displayTileDescription(String tileDescription) {
        return this.displaySimpleInfo(tileDescription);
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
    public String displayNewGold(double amount) {
        return this.displaySimpleInfo("You got " + amount + " gold for selling all your fish");
    }

    @Override
    public void displayGold(double amount) {
        this.displaySimpleInfo("You have " + amount + " gold");
    }

    /**
     * <p>The method displayFishingResult is used to display how many fished the boat caught and how long it took</p>
     * <p>The method uses {@link #displaySimpleInfo(String)} to display:</p>
     * <p>The fishing went well! you caught " + catchAmount</p>
     *
     * If we want to print how many hours we fished, it should be passed as a param in the Map.
     * probably as key "hoursToFish"
     *
     * @param fishResult The input Map of how many fish were caught of each type
     * @return
     */
    @Override
    public String displayFishingResult(Map<String, Integer> fishResult) {
        String out = "";
        for (String fish: fishResult.keySet()) {
            out += this.displaySimpleInfo("You caught " + fishResult.get(fish) + " " + fish + "\n");
        }
        return out;
    }

    /**
     * Used to display to the player how many fish are currently in storage
     *
     * @param fishMap the map containing fish and number of fish
     */
    @Override
    public void displayCurrentFish(Map<String, Integer> fishMap) {
        String printString = "";
        for (String fish : fishMap.keySet()) {
            printString += "You have caught " + fishMap.get(fish) + " " + fish + "\n";
        }
        this.displaySimpleInfo(printString);
    }

    @Override
    public void DisplayTooManyHours() {
        this.displaySimpleInfo("You have fished to many hours!" + "\n" + "You can only fish for 12 hours.");
    }
}
