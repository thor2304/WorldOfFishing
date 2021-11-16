package worldofzuul.Domain;

class Game {
    public Boat boat;
    private Tile currentTile;
    private Tile[][] tiles;


    //constructors

    /**
     * No args constructor,
     * Currently the only constructor available.
     * A new game should not take any arguments.
     */
    public Game() {
        createTiles();
        this.boat = new Boat(4);
    }


    //world of zuul methods:

    private void createTiles() {
        tiles = new Tile[DomainSettings.BOARDSIZE][DomainSettings.BOARDSIZE];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile("coordinate: " + i + "," + j);

            }

        }
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (tiles.length > x + 1) {
                    tiles[x][y].setExit("east", tiles[x + 1][y]);
                }
                if (tiles[x].length > y + 1) {
                    tiles[x][y].setExit("south", tiles[x][y + 1]);
                }
                if (x - 1 >= 0) {
                    tiles[x][y].setExit("west", tiles[x - 1][y]);
                }
                if (y - 1 >= 0) {
                    tiles[x][y].setExit("north", tiles[x][y - 1]);
                }

            }

        }

        this.currentTile = tiles[1][1];
    }


    private void goTile(String direction) {
//        if (!command.hasSecondWord()) {
//            //System.out.println("Go where?"); //has been replaced by below
//            display.unknownCommand("Go where?");
//            return;
//        }
        // the above should be implemented in GameLoop

        Tile nextRoom = currentTile.getExit(direction);

        if (nextRoom == null) {
            display.outOfBoundsText();
        } else {
            currentTile = nextRoom;
            //System.out.println(currentTile.getLongDescription());
            display.displayTileDescription(currentTile.getLongDescription());
        }
    }


    // our own methods:

    public void fish(Command command) {
        if (!command.hasSecondWord()) {
            boat.fishTile();
            updateAllTiles();
        } else {
            String newTimeString = command.getSecondWord();
            //convert the string to a number, remember that the user may not have typed a number, so watch out for errors

            try {
                int newTimeInt = Integer.parseInt(newTimeString); //replace here
                if (newTimeInt > 12) {
                    display.DisplayTooManyHours();
                } else {
                    boat.fishTile(newTimeInt);
                }

            } catch (NumberFormatException e) {
                display.displaySimpleInfo(e.getMessage());
            }
        }
    }


    /**
     * <p>updateAllTiles starts by using a nested for loop to update all the tiles with the method updateFishNumbers</p>
     * <p>After all the tiles has the correct fish number of fish, it uses a nested for loop to migrate the fish which need to migrate and saves the number of the migrated fish r</p>
     * <p>The last nested for loop takes the migrated fish number and adds it to the current fish number </p>
     */
    public void updateAllTiles() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y].updateFishNumbers(); //this updates the number of fish in Tile x,y
            }
        }

        //migrate fish
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y].migrateFishPopulation(); //this migrates fish from Tile x,y to the neighbouring tiles
                //the fish are stored in a variable in each tile called migrated fish, and thus the migration is not yet complete
            }
        }

        //Complete migration
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                //tiles[x][y].completeMigration(); //this adds the migrated fish to the amount of fish in the tile
            }
        }
    }


    /**
     * Returns the reference to {@link #currentTile}, <b>not</b> a copy of the {@link Tile}
     * <p>
     * If the returned {@link Tile} is manipulated it is the actual {@link #currentTile} stored in Game that is manipulated
     * </p>
     *
     * @return a reference to the currentTile
     */
    public Tile getCurrentTile() {
        // normally we would use copy() or a self implemented safe copy, to ensure, that we dont mess with the actual current room
        // but in this case, we DO want to mess with the actual current room and data stored within
        return this.currentTile;
    }
}
