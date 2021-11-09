package worldofzuul;

public class Game 
{
    private Parser parser;
    private Tile currentTile;
    private Tile[][] tiles;
    public DisplayToPlayer display;
    public Boat boat;
        

    //constructors

    /**No args constructor,
     * Currently the only constructor available.
     * A new game should not take any arguments.
     * <p>It creates an instance of  {@link Parser} and {@link DisplayToPlayer}, to use for itself.</p>
     * It also creates a net of {@link Tile Tiles} which are technically stored only because they refer to each other
     */
    public Game() 
    {
        createTiles();
        this.parser = new Parser();
        this.display = new DisplayToPlayer(parser.getCommandWords());
    }


    public void setBoat(){
        this.boat = new Boat(4);
    }

    //world of zuul methods:

    private void createTiles()
    {
        tiles = new Tile[Settings.BOARDSIZE][Settings.BOARDSIZE];
        for (int i = 0; i < tiles.length ; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile("coordinate: "+ i + "," + j);

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

        this.currentTile = tiles[1][1];
    }

    public void play() 
    {            
        //printWelcome();
        display.displayWelcome(this.currentTile);

                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //finished was true and therefore the game has ended
        display.displayGoodbye();
    }

    /**@deprecated This entire functionality is getting replaced by {@link DisplayToPlayer#displayWelcome(Tile)}
     * <p><b>
     * This method will be deleted shortly
     */
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to World of Fishing!");
        System.out.println("World of fishing is a game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help, but i guess you're fucked then.");
        System.out.println();
        System.out.println(currentTile.getLongDescription());
    }

    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            //System.out.println("I don't know what you mean...");
            display.unknownCommand("");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            //printHelp();
            display.displayHelpText();
        }
        else if (commandWord == CommandWord.GO) {
            goTile(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }else if(commandWord == CommandWord.FISHTILE){
            fish(command);
        }else if(commandWord == CommandWord.SHOWFISH){
            boat.showFish();
        }else if(commandWord == CommandWord.SHOWGOLD){
            boat.showGold();
        }else if(commandWord == CommandWord.SELL){
            boat.sellFish();
        }else if(commandWord == CommandWord.TIME){
            timeInfo(command);
        }
        return wantToQuit;
    }

    /**@deprecated This entire functionality is getting replaced by {@link DisplayToPlayer#displayHelpText()}
     * <p><b>
     * This method will be deleted shortly
     */
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
            //System.out.println("Go where?"); //has been replaced by below
            display.unknownCommand("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Tile nextRoom = currentTile.getExit(direction);

        if (nextRoom == null) {
            display.outOfBoundsText();
        }
        else {
            currentTile = nextRoom;
            //System.out.println(currentTile.getLongDescription());
            display.displayTileDescription(currentTile.getLongDescription());
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()) {
            //System.out.println("Quit what?");
            display.unknownCommand("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    // our own methods:

    public void fish(Command command){
        if(!command.hasSecondWord()) {
            boat.fishTile();
            updateAllTiles();
        }else {
            String newTimeString = command.getSecondWord();
            //convert the string to a number, remember that the user may not have typed a number, so watch out for errors

            try{
                int newTimeInt = Integer.parseInt(newTimeString); //replace here
                if (newTimeInt > 12){
                    display.DisplayTooManyHours();
                } else {
                    boat.fishTile(newTimeInt);
                }

            }catch (Exception e){
                display.displaySimpleInfo(e.getMessage());
            }
        }
    }

    public void timeInfo(Command command){
        if(!command.hasSecondWord()) {
            display.displaySimpleInfo("" + boat.getHoursToFish());
        }else {
            String secondWord = command.getSecondWord();
            if(secondWord == "set"){
                //maybe do something with this?
            }
        }
    }

    /**<p>updateAllTiles starts by using a nested for loop to update all the tiles with the method updateFishNumbers</p>
     * <p>After all the tiles has the correct fish number of fish, it uses a nested for loop to migrate the fish which need to migrate and saves the number of the migrated fish r</p>
     * <p>The last nested for loop takes the migrated fish number and adds it to the current fish number </p>
     */
    public void updateAllTiles(){
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
                //tiles[x][y].completeMigration(); //this adds the migrated fish to the amount of fish in the tile
            }
        }
    }


    /** Returns the reference to {@link #currentTile}, <b>not</b> a copy of the {@link Tile}
     * <p>
     * If the returned {@link Tile} is manipulated it is the actual {@link #currentTile} stored in Game that is manipulated
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
