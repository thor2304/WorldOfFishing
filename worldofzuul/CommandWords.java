package worldofzuul;
import java.util.HashMap;


public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;
    private DisplayToPlayer display; //should be removed if the class ends up having no calls to DisplayToPlayer

    /**No args constructor for CommandWords
     * <p>Creates and stores a HashMap of validCommands</p>
     * The unknown command is simply ommited from the list, and thus will not show up when displaying valid commands
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }

        display = new DisplayToPlayer(this);
    }

    /**Gets the commandword from the list of valid commands
     * <p>If the command is not in the list the commandWord for unknown command is returned</p>
     *
     * @param commandWord The String to check
     * @return the commandWord associated with the string searched, and ? if the command is not valid
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }

    /**Unused method, to check whether a command is known or unknown
     *
     * @param aString the string containing a command to check
     * @return true, if command is known, false if command is unknown
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**Calls display all commands from DisplayToPlayer
     * think about reworking this to a dirct call to DisplayToPlayer
     */
    public void showAll() 
    {
        display.displayAllCommands();
    }

    /**gets the list of valid commands in the game
     * <p>Be careful not to alter the values in the HashMap, only read the results or create a deep copy</p>
     * (if you wish to manipulate the commandwords you have to copy them as well
     *
     * @return a reference to the actual Hashmap containing the validcommands
     */
    public HashMap<String, CommandWord> getValidCommands(){
        return validCommands;
    }
}
