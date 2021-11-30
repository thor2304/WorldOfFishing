package oop.javafxtest.worldofzuul.Domain;

/**
 * this class defines default settings to make it more manageable.
 */
class DomainSettings {
    /** the default number of the habitat quality in the tile.{@link Tile#Tile(String)}
     * click link to read what the method does.
     */
    public static final double DEFAULTHABITATQUALITY = 1;


    /** the default number of fish.{@link Tile#Tile(String)}
     * click link to read what the method does.
     */
    public static final int DEFAULTNUMBEROFFISH = 1000;


    /** the default number of the net destruction.{@link Tile#fishTile(int)}
     * click link to read what the method does.
     */
    public static final double DEFAULTNETDESTRUCTION = 0.1;



    /** the default catch rate.{@link Tile#fishTile(int)}
     * click link to read what the method does.
     */
    public static final double DEFAULTCATCHRATE = 0.06;


    /** the board size of the tile.{@link Game}
     * the board size value can be found in the createTiles method
     */
    public static final int BOARDSIZE = 3;


    /** the amount of variance of the caught fish.{@link Tile#fishTile(int, double, double)}
     * click link to read what the method does.
     */
    public static final double VARIANCE = 0.4;

    public static final double PROTECTIONCOST = 15000;
}
