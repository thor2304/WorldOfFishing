package oop.javafxtest.worldofzuul.Domain;
import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;
import oop.javafxtest.worldofzuul.Runner;

import java.util.HashMap;
import java.util.Map;

import static oop.javafxtest.worldofzuul.Domain.DomainSettings.DAILYEXPENSES;
import static oop.javafxtest.worldofzuul.Domain.DomainSettings.EXPENSESCALING;

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
 *@TODO Later, add nettype, and commands to change this
 */


class Boat {
    //private double catchAmount; //possibly rework this to use a HashMap<Fish, int> instead of a double, to allow for storage of different
    private int hoursToFish;
    private double goldStorage;
    private Map<Fish, Integer> catchAmount;
    private double dailyExpenses;
    private int counter = 0;
    private double protectionExpenses;




    /** <p>initializes local attributes </p>
     *<p>sets this.game to be Runner.game</p>
     * maybe more things to do!
     */
    public Boat(Map<Fish, Integer> catchAmount, int hoursToFish, double goldStorage){
        //construct it
        this.catchAmount = catchAmount;
        this.hoursToFish = hoursToFish;
        this.goldStorage = goldStorage;
        this.dailyExpenses = DAILYEXPENSES;
    }

    /**Reduced constructor, taking only one param, and setting the rest to 0
     * <p>CatchAmount is set to 0</p>
     * <p>goldStorage is set to 0</p>
     * is chained using {@link #Boat(Map, int, double)}
     * @param hoursToFish The default amount of hours to Fish
     */
    public Boat (int hoursToFish) {
        this(new HashMap<Fish, Integer>(), hoursToFish,0);
        for(Fish fish : Fish.values()){
            this.catchAmount.put(fish,0);
        }
    }

    /**<p>Calls {@link #fishTile(int, Tile)} with the value of {@link #hoursToFish} saved in {@link Boat}</p>
     * <b>!!update all tiles after calling this method!!</b>
     * Using the method Game.updateAllTiles()
     */
    public Map<Fish, Integer> fishTile(Tile currentTile) throws TileProtectedFromFishingError{
        return fishTile(this.hoursToFish, currentTile);
    }

    /**Calling this method does not change {@link #hoursToFish} in {@link Boat}
     *
     * !!update all tiles after calling this method!!
     * Use the method Game.updateAllTiles()
     * @param hoursToFish the amount of hours to fish
     */
    public Map<Fish, Integer> fishTile(int hoursToFish, Tile currentTile) throws TileProtectedFromFishingError{
        // do some fishing
        Map<Fish, Integer> caughtFish = currentTile.fishTile(hoursToFish);
        for (Fish fish : Fish.values()) {
            this.catchAmount.put(fish, this.catchAmount.get(fish) + caughtFish.get(fish));
        }
        subtractDailyExpenses();
        return caughtFish;
    }

    /** sellFish method
     *<p>goldStorage is equal to catchAmount multiplied by the price of the fish.
     *The price is obtained from the Fish Class's getSalesPrice method.</p>
     *
     */
    public double sellFish() {
        double newGold = 0;
        for (Fish fish : Fish.values()) {
            int fishAmount = catchAmount.get(fish);
            double fishGold = (fishAmount * fish.getSalesPrice());
            goldStorage += fishGold;
            newGold += fishGold;
            this.catchAmount.put(fish, 0);
        }
        return newGold;
    }

    public void protectionCost(){
        if (goldStorage >= DomainSettings.PROTECTIONCOST){
            this.protectionExpenses = DomainSettings.PROTECTIONCOST;
            goldStorage -= this.protectionExpenses;
        }
    }

    /**
     * <p>Daily expenses so you force the player to fish more.</p>
     * @return
     */
    public void subtractDailyExpenses(){
        if (counter >= 7) {
            this.dailyExpenses *= EXPENSESCALING;
            goldStorage -= this.dailyExpenses;
            counter = 0;
        } else{
            counter++;
        }
    }



    public Map<Fish, Integer> getCatchAmount() {
        return catchAmount;
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

    public double getDailyExpenses(){
        return this.dailyExpenses;
    }
}
