package worldofzuul.Display;

import java.util.Scanner;

/**This class could be reworked to be static and experiment with perhaps making the class abstract to ensure no instanses are created
 *
 * This class should at some point be overhauled in coorporation with Command, CommandWord and CommandWords
 * These above classes should be merged in some combination
 *
 */
class Parser
{
    /** Stores the {@link CommandWords} object containing the list of valid commands */
    private CommandWords commands;
    /** Is actually a {@link Scanner} object  */
    private Scanner reader;
    private DisplayToPlayer display;

    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        display = new DisplayToPlayer();
    }

    /**Reads the user input and parses it into a Command object
     * <p>If the user command has a space, the rest is stored in the outgoing {@link Command} object as null</p>
     *
     * @return The constructed {@link Command} object containing both a {@link CommandWord}, and then a command modifier in the form of a string, as the secondword
     */
    public Command getCommand() 
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        //System.out.print("> ");
        display.readyForNextCommand();


        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next(); 
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**Added to allow for command displaying, and accessing the commandwords object: {@link #commands} outside of Parser
     *
     * Should be deprecated after {@link CommandWords} and {@link DisplayToPlayer} rework
     *
     * @return the {@link CommandWords} object containing valid commands
     */
    public CommandWords getCommandWords() {
        return commands;
    }

}
