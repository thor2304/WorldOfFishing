package oop.javafxtest.worldofzuul.Domain;

/**
 * this class defines default settings to make it more manageable to tweak game settings.
 */
abstract class DomainSettings {
    /** the default habitat quality in {@link Tile#Tile(String, int, int)}
     * click link to read what the method does.
     */
    public static final double DEFAULTHABITATQUALITY = 1;

    public static final double MAXIMUMHABITATQUALITY  = 1.5;

    public static final double PROTECTIONBONUS = 1.5;

    /** the default number of fish in {@link Tile#Tile(String, int, int) Tile}
     * click link to read what the method does.
     */
    public static final int DEFAULTNUMBEROFFISH = 100;

    public static final int DEFAULTCARRYINGCAPACITYOFTILE = 1000;

    /** the default number of the net destruction.{@link Tile#fishTile(int)}
     * click link to read what the method does.
     */
    public static final double DEFAULTNETDESTRUCTION = 0.5;

    /** the default catch rate.{@link Tile#fishTile(int)}
     * click link to read what the method does.
     */
    public static final double DEFAULTCATCHRATE = 0.13;

    /** the board size of the tile.{@link Game}
     * the board size value can be found in the createTiles method
     */
    public static final int BOARDSIZE = 3;

    /**
     * Default time for fishing
     */
    public static final int DEFAULTHOURSTOFISH = 12;

    /** the amount of variance of the caught fish. {@link Tile#fishTile(int, double, double)}
     * click link to read what the method does.
     */
    public static final double VARIANCE = 0.4;

    /** The way DAILYEXPENSES is used is that it is multiplied by EXPENSESCLAING.
     *
     */
    public static final double DAILYEXPENSES = 1000;

    public static final double EXPENSESCALING = 1.05;
    public static final double REQUIREDGOLDTOWIN = 100000;
    public static final double PROTECTIONCOST = 15000;
}

