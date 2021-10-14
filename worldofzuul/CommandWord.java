package worldofzuul;

public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"); //the ? is not stored in the list of valid commands, therefore the contents of the string inside unknown does not matter.
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    @Override //i added this override annotation, sloppy devs
    public String toString()
    {
        return this.commandString;
    }
}
