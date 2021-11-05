package worldofzuul;
import worldofzuul.Errors.TileProtectedFromFishingError;

import java.util.HashMap;
import java.util.Map;

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
    //private double catchAmount; //possibly rework this to use a HashMap<Fish, int> instead of a double, to allow for storage of different
    private int hoursToFish;
    private double goldStorage;
    private Game game;
    private Map<Fish, Integer> catchAmount;





    /** <p>initializes local attributes </p>
     *<p>sets this.game to be Runner.game</p>
     * maybe more things to do!
     */
    public Boat(Map<Fish, Integer> catchAmount, int hoursToFish, double goldStorage){
        //construct it
        this.catchAmount = catchAmount;
        this.hoursToFish = hoursToFish;
        this.goldStorage = goldStorage;

        this.game = Runner.game;

    }


    /**Reduced constructor, taking only one param, and setting the rest to 0
     * <p>CatchAmount is set to 0</p>
     * <p>goldStorage is set to 0</p>
     * is chained using {@link #Boat(Map, int, double)}
     * @param hoursToFish The default amount of hours to Fish
     */
    public Boat (int hoursToFish) {
        this(new HashMap<Fish, Integer>(), hoursToFish,0);
        for(Fish fi : Fish.values()){
            this.catchAmount.put(fi,0);
        }
    }


    /**Make this method print info to the player about how the fishing went, how much fish were caught and how long we fished for
     *
     * !!update all tiles after calling this method!!
     * Use the method Game.updateAllTiles()
     * @TODO Check if the tile is protected when getting the fishing results, a.k.a handle the TileProtected exception
     */
    public void fishTile(int hoursToFish) {
        // do some fishin
        try{
            Map<Fish, Integer> caughtFish = this.game.getCurrentTile().fishTile(hoursToFish);
            for (Fish fish : Fish.values()) {
                this.catchAmount.put(fish, this.catchAmount.get(fish) + caughtFish.get(fish));
            }
        }catch(TileProtectedFromFishingError T){
            display.displaySimpleInfo(T.getMessage());
        }


    }


    /** sellFish method
     *<p>goldStorage is equal to catchAmount multiplied by the price of the fish.
     *The price is obtained from the Fish Class's getSalesPrice method.</p>
     *
     */
    public void sellFish(){
        goldStorage += (getCatchAmount()*Fish.MAKREL.getSalesPrice()); //convert fish(makrel) to gold
        catchAmount = 0;


    }

    public Map getCatchAmount() {
        return catchAmount;
    }

    public void setCatchAmount(Map catchAmount) {
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
