package worldofzuul;

import worldofzuul.DisplayInterfaces.DisplayFishingResult;

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
public class Boat{
    private double catchAmount; //possibly rework this to use a HashMap<Fish, int> instead of a double, to allow for storage of different
    private int hoursToFish;
    private double goldStorage;
    private Game game;
    private DisplayToPlayer display;


    /** <p>initializes local attributes </p>
     *<p>sets this.game to be Runner.game</p>
     * maybe more things to do!
     */
    public Boat(double catchAmount, int hoursToFish, double goldStorage){
        //construct it
        this.catchAmount = catchAmount;
        this.hoursToFish = hoursToFish;
        this.goldStorage = goldStorage;

        this.game = Runner.game;
        this.display = game.display;

    }


    /**Reduced constructor, taking only one param, and setting the rest to 0
     * <p>CatchAmount is set to 0</p>
     * <p>goldStorage is set to 0</p>
     * is chained using {@link #Boat(double, int, double)}
     * @param hoursToFish The default amount of hours to Fish
     */
    public Boat (int hoursToFish) {
        this(0, hoursToFish,0);
    }


    /**<p>Calls {@link #fishTile(int)} with the value of {@link #hoursToFish} saved in {@link Boat}</p>
     * <b>!!update all tiles after calling this method!!</b>
     * Using the method Game.updateAllTiles()
     */
    public void fishTile(){
        fishTile(this.hoursToFish);
    }

    /**Calling this method does not change {@link #hoursToFish} in {@link Boat}
     *
     * @param hoursToFish the amount of hours to fish
     */
    public void fishTile(int hoursToFish){
        // do some fishing
        this.catchAmount += game.getCurrentTile().fishTile(hoursToFish);
        display.displayFishingResult(this.getCatchAmount(), hoursToFish);
    }

    public void sellFish(){
        //convert fish to gold
        //use the values from Fish
    }

    public void showGold(){
        this.display.displayGold(this.getGoldStorage());
    }

    public void showFish(){
        this.display.displayCurrentFish(catchAmount);
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
