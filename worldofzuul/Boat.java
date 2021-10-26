package worldofzuul;

/**Boat Class
 * Make commands for viewing the info (e.g. make a command to call getCatchAmount and show the player this info)
 * Make commands to let the player change the settings of hoursToFish
 *
 * Make a Command to let the player fish with selected settings, 2 different commands, see below:
 * > fish
 * Will prompt the player to confirm fishing with current setting of hoursToFish
 *
 * > fish x
 * will start fishing immediately with hoursToFish set to x
 *
 *Add later, nettype, and commands to change this
 */
public class Boat {
    private double catchAmount; //possibly rework this to use a HashMap<Fish, int> instead of a double, to allow for storage of different
    private int hoursToFish;
    private double goldStorage;
    private Game game;


    /**init local attributes
     * set game to be Runner.game
     *
     * maybe more things to do!
     */
    public Boat(){
        //construct it
    }


    /**Make this method print info to the player about how the fishing went, how much fish were caught and how long we fished for
     *
     * !!update all tiles after calling this method!!
     * Use the method Game.updateAllTiles()
     */
    public void fishTile(){
        // do some fishing
        this.catchAmount += game.getCurrentTile().fishTile(this.hoursToFish);
    }

    public void sellFish(){
        //convert fish to gold
        //use the values from Fish
    }

    public double getCatchAmount() {
        return catchAmount;
    }

    public void setCatchAmount(double catchAmount) {
        this.catchAmount = catchAmount;
    }

    public int getHoursToFish() {
        return hoursToFish;
    }

    public void setHoursToFish(int hoursToFish) {
        this.hoursToFish = hoursToFish;
    }

    public double getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(double goldStorage) {
        this.goldStorage = goldStorage;
    }
}
