package worldofzuul.DisplayInterfaces;

public interface CommandExecutionTexts {
    void unknownCommand(String bonusInfo);
    void displayHelpText();
    void outOfBoundsText();
    void displayTileDescription(String tileDescription);

    /**
     * Is called from {@link worldofzuul.Parser Parser} contrary to the other methods in this interface, which are called from {@link worldofzuul.Game}
     */
    void readyForNextCommand();
}
