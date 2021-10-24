package worldofzuul;

public class Game 
{
    private Parser parser;
    private Tile currentRoom;
        

    //constructors
    public Game() 
    {
        createTiles();
        parser = new Parser();
    }


    //world of zuul methods:

    private void createTiles()
    {
        Tile[][] plade = new Tile[3][3];
        for (int i = 0; i < plade.length ; i++) {
            for (int j = 0; j < plade[i].length; j++) {
                plade[i][j] = new Tile("Koordinat: "+ i + "," + j);

            }

        }
        for (int x = 0; x <plade.length ; x++) {
            for (int y = 0; y < plade[x].length; y++) {
                if (plade.length > x+1) {
                    plade[x][y].setExit("east",plade[x+1][y]);
                }
                if (plade[x].length > y+1){
                    plade[x][y].setExit("south",plade[x][y+1]);
                }
                if (x-1 >= 0){
                    plade[x][y].setExit("west",plade[x-1][y]);
                }
                if (y-1 >= 0) {
                    plade[x][y].setExit("north",plade[x][y-1]);
                }

            }

        }

        currentRoom = plade[1][1];
    }

    public void play() 
    {            
        printWelcome();

                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to World of Fishing!");
        System.out.println("World of fishing is a game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help, but i guess you're fucked then.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goTile(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goTile(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Tile nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You've hit land!");
            System.out.println("You've done goofed");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    // our own methods:

    /** loop the tile array and update them all
     * Be on the lookout for how migration will be implemented, perhaps migration will have to be implemented in here
     */
    public void updateAllTIles(){
        //Tile.updateFishNumbers(); //of course do this in the loop and instead of Tile, use the index in the array
    }
}
