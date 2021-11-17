package worldofzuul.Display.DisplayInterfaces;

import java.util.Set;

public interface CommandExecutionTexts {
    void unknownCommand(String bonusInfo);
    void displayHelpText(Set<String> allValidCommands);
    void outOfBoundsText();
    void displayTileDescription(String tileDescription);

    /**
     * Is called from {@link worldofzuul.Display.Parser} contrary to the other methods in this interface, which are called from {@link worldofzuul.Domain.Game}
     */
    void readyForNextCommand();
}
