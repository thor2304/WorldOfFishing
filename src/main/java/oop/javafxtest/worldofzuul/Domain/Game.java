package oop.javafxtest.worldofzuul.Domain;

import oop.javafxtest.worldofzuul.Errors.OutOfBoundsError;
import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;
import oop.javafxtest.worldofzuul.Errors.TooManyHoursToFishError;

import java.util.Map;
import java.util.Objects;

class Game {
    public Boat boat;
    private Tile currentTile;
    private Tile[][] tiles;


    //constructors

    /**
     * No args constructor,
     * Currently the only constructor available.
     * A new game should not take any arguments.
     * It also creates a net of {@link Tile Tiles} which are technically stored only because they refer to each other
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


    public String goTile(String direction) throws OutOfBoundsError {
        Tile nextRoom = currentTile.getExit(direction);

        if (nextRoom == null) {
            throw new OutOfBoundsError();
        } else {
            currentTile = nextRoom;
            return currentTile.getLongDescription();
        }
    }


    // our own methods:

    /**
     * @param hoursToFish a string containing only the number of hours to fish
     */
    public Map<Fish, Integer> fish(String hoursToFish) throws NumberFormatException, TooManyHoursToFishError, TileProtectedFromFishingError {
        if (Objects.isNull(hoursToFish)) {
            Map<Fish, Integer> newlyCaughtFish = boat.fishTile(currentTile);
            updateAllTiles();
            return newlyCaughtFish;
        } else {
            int newTimeInt = Integer.parseInt(hoursToFish);
            if (newTimeInt > 12) {
                throw new TooManyHoursToFishError(hoursToFish);
            } else {
                Map<Fish, Integer> newlyCaughtFish = boat.fishTile(newTimeInt, currentTile);
                updateAllTiles();
                return newlyCaughtFish;
            }
        }
    }


    /**
     * @param hoursToFish a string containing only the number of hours to fish
     */
    public Map<Fish, Integer> fish() throws TileProtectedFromFishingError {
        Map<Fish, Integer> newlyCaughtFish = boat.fishTile(currentTile);
        updateAllTiles();
        return newlyCaughtFish;
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
