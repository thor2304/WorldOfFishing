package worldofzuul.DisplayInterfaces;

public interface CommandExecutionTexts {
    void unknownCommand(String bonusInfo);
    void displayHelpText();
    void outOfBoundsText();
    void displayTileDescription(String tileDescription);

    /**
     * Is used in {@link worldofzuul.Parser Parser} contrary to the other methods in this interface, which are called from Game
     */
    void readyForNextCommand();
}
