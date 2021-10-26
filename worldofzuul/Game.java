package worldofzuul;

public class Game 
{
    private Parser parser;
    private Tile currentTile;
    private Tile[][] tiles;
        

    //constructors
    public Game() 
    {
        createTiles();
        parser = new Parser();
    }


    //world of zuul methods:

    private void createTiles()
    {
        tiles = new Tile[3][3];
        for (int i = 0; i < tiles.length ; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile("Koordinat: "+ i + "," + j);

            }

        }
        for (int x = 0; x <tiles.length ; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (tiles.length > x+1) {
                    tiles[x][y].setExit("east",tiles[x+1][y]);
                }
                if (tiles[x].length > y+1){
                    tiles[x][y].setExit("south",tiles[x][y+1]);
                }
                if (x-1 >= 0){
                    tiles[x][y].setExit("west",tiles[x-1][y]);
                }
                if (y-1 >= 0) {
                    tiles[x][y].setExit("north",tiles[x][y-1]);
                }

            }

        }

        currentTile = tiles[1][1];
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
        System.out.println(currentTile.getLongDescription());
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

        Tile nextRoom = currentTile.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You've hit land!");
            System.out.println("You've done goofed");
        }
        else {
            currentTile = nextRoom;
            System.out.println(currentTile.getLongDescription());
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
        this.updateAllTIles();
    }

    // our own methods:

    /**<p>updateAllTiles starts by using a nested for loop to update all the tiles with the method updateFishNumbers</p>
     * <p>After all the tiles has the correct fish number of fish, it uses a nested for loop to migrate the fish which need to migrate and saves the number of the migrated fish r</p>
     * <p>The last nested for loop takes the migrated fish number and adds it to the current fish number </p>
     */
    public void updateAllTIles(){
        for (int x = 0; x < tiles.length ; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y].updateFishNumbers(); //this updates the number of fish in Tile x,y
            }
        }

        //migrate fish
        for (int x = 0; x < tiles.length ; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y].migrateFishPopulation(); //this migrates fish from Tile x,y to the neighbouring tiles
                //the fish are stored in a variable in each tile called migrated fish, and thus the migration is not yet complete
            }
        }

        //Complete migration
        for (int x = 0; x < tiles.length ; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y].completeMigration(); //this adds the migrated fish to the amount of fish in the tile
            }
        }
    }


    /** Returns the reference to the currentTile
     * <p>
     * If the Tile is manipulated it is the actual tile that is manipulated
     * </p>
     *
     * @return a reference to the currentTile
     */
    public Tile getCurrentTile(){
        // normally we would use copy() or a self implemented safe copy, to ensure, that we dont mess with the actual current room
        // but in this case, we DO want to mess with the actual current room and data stored within
        return this.currentTile;
    }
}
