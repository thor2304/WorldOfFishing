package worldofzuul;

import worldofzuul.DisplayInterfaces.*;

public class DisplayToPlayer implements DisplaySimpleInfo, DisplayCommands {

    /**This method displays a simple string to the user
     * <p>
     * In iteration 1 this seems silly, but in iteration 2 will become quite handy
     * </p>
     * Specific for iteration 2 think about max string length
     * @param info The string to display to the user
     */
    @Override
    public void DisplaySimpleInfo(String info){
        System.out.println(info);
    }

    /**This method displays all the available commands/actions to the user
     *
     * @param commandWords the CommandWords object that contains the valid commands
     */
    @Override
    public void DisplayAllCommands(CommandWords commandWords) {
        for(String command : commandWords.getValidCommands().keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

    /**This method is used for displaying a single command to the user.
     *Perhaps useless, remove at will
     * @param text Displayed in the beginning of the display to the user
     * @param commandWord the commandword to display
     */
    @Override
    public void DisplayCommand(String text, CommandWord commandWord) {
        System.out.println(text + commandWord);
    }
}
