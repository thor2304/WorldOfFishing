package worldofzuul.Display.DisplayInterfaces;

import worldofzuul.Display.Parser;
import worldofzuul.Domain.Game;

public interface CommandExecutionTexts {
    void unknownCommand(String bonusInfo);
    void displayHelpText();
    void outOfBoundsText();
    void displayTileDescription(String tileDescription);

    /**
     * Is called from {@link Parser Parser} contrary to the other methods in this interface, which are called from {@link Game}
     */
    void readyForNextCommand();
}
