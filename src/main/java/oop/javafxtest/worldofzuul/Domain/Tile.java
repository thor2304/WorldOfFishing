package oop.javafxtest.worldofzuul.Domain;

import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class Tile
{
    private String description;
    private HashMap<String, Tile> exits;
    private Map<Fish, Integer> numberOfFish;
    private Map<Fish, Integer> numberOfMigratedFish;
    private double habitatQuality;
    private boolean isProtectedFromFishing;
    private int x;
    private int y;


    //Tile constructors:
    /**Fish should be able to contain a specific fish in the fish class. 
     * Maybe look at how it would be possible to implement a method to make more than one fish in each tile.
     * 
     * <a href="#{@link}">{@link Fish}</a>
     * @param description
     */
    public Tile(String description, int x, int y)
    {
        this(description, DomainSettings.DEFAULTHABITATQUALITY, new HashMap<Fish, Integer>(), x, y);
        for(Fish fish : Fish.values()) {
            this.numberOfFish.put(fish, DomainSettings.DEFAULTNUMBEROFFISH);
        }
    }

    /**Add these params to be initialized:
     * fishInThisTile
     * habitatQuality
     * numberOfFish
     *
     * @param description The text shown to the user when entering the Tile
     * @param habitatQuality The starting quality of the habitat
     * @param numberOfFish The starting number of fish in this tile
     */
    public Tile(String description, double habitatQuality, Map numberOfFish, int x, int y){
        this.description = description;
        this.exits = new HashMap<String, Tile>();
        this.numberOfFish = numberOfFish;
        this.habitatQuality = habitatQuality;
        this.x = x;
        this.y = y;
    }

    //Methods from world of zuul
    public void setExit(String direction, Tile neighbor)
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You have sailed to this " + description + ".\n" + getExitString();
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Tile getExit(String direction)
    {
        return exits.get(direction);
    }

    //Methods from our implementation
    private int increaseNumberOfFish(Fish fish, int numberOfNewFish){
        int currentFish = this.numberOfFish.get(fish);
        this.numberOfFish.put(fish,numberOfNewFish + currentFish);
        return this.numberOfFish.get(fish);
    }

    /**Always check if the return of this method is negative, if so too many fish were removed
     *
     * @param numberOfFishToRemove The amount of fish to reduce the population by
     * @return the initial result of the removal of fish, will be negative if more fish were removed than there lived in the habitat
     */
    private int decreaseNumberOfFish(Fish fish, int numberOfFishToRemove){
        int currentFish = this.numberOfFish.get(fish);
        int out = currentFish - numberOfFishToRemove;
        currentFish = out;
        if(out < 0){
            currentFish = 0;
        }
        this.numberOfFish.put(fish, currentFish);
        return out;
    }


    /**<p>{@link #increaseNumberOfFish(Fish, int)} works by a formula that multiplies habitatQuality, getReproductionRate and numberOfFish
     *We cast increaseNumberOfFish to int and round it up.<p/>
     *<p>{@link #decreaseNumberOfFish(Fish, int)} works by a formula that multiplies habitatQuality, getDeathRate and numberOfFish.
     *We cast decreaseNumberOfFish to an int type. We round up decreaseNumberOfFish.<p/>
     */
    public void updateFishNumbers(){
        for(Fish fish : Fish.values()) {
            increaseNumberOfFish( fish, (int) Math.round( this.habitatQuality * fish.getReproductionRate() * this.numberOfFish.get(fish) ) );
            decreaseNumberOfFish( fish, (int) Math.round( this.habitatQuality * fish.getDeathRate() * this.numberOfFish.get(fish) ) );
         }
    }

    /**Is called directly by Game
     */
    public void migrateFishPopulation(){
        //do all the complex migrate math
        //get migration rate from fishInThisTile.getMigrationRate
        //Compare quality of current tile vs tile to migrate to
        //check fish numbers of current tile vs tile to migrate to
        //watch out for overfilling a tile beyond its capacity
        //Migrate based on position (perhaps with amount of circle area overlap

        //use the exits Hashmap to get the neighbours of the tile
        //check all neighbours before deciding where to migrate to

        //remember to use the methods in this class, increaseFishPopulation and decreaseFishPopulation
        //remember that this method may have to be reworked to be a public method called from Game

        //make a decision of what the inputs should be
        //and what the return type should be

        //watch out for current/future problems (sending fish to a tile, that hasnt migrated yet)
        //we will avoid this by sending migrated fish to the variable numberOfMigratedFish


        //think about different fish species and checking them (maybe important, maybe not to be decided)
    }



    /**Is also called directly by Game
     * sets {@link #numberOfFish} += {@link #numberOfMigratedFish}
     *<p></p>
     * <p>
     * It is intentional that we do not check if the amount of Fish is above the Tiles capacity.
     * By doing it this way, we allow a tile to potentially be overfilled,
     * but the fish would die by way of decrease numbers in the next round.
     * </p>
     * By this method we also depend on migrate to ensure that fish does not really want to migrate from an
     * undercrowded tile to a more overcrowded tile
     *
     */
    public void completeMigration(){
        for(Fish fish : Fish.values()) {
            int currentFish = this.numberOfFish.get(fish);
            currentFish += this.numberOfMigratedFish.get(fish);
            this.numberOfFish.put(fish, currentFish);
            this.numberOfMigratedFish.put(fish,0);
        }
    }


/**Will add the input to {@link #habitatQuality}
     * @param amount the amount to change quality by
     */
    public void updateQuality(double amount){
        this.habitatQuality += amount;
    }

    /** Protects the tile, so fishing can no longer be done
     *<p>Done by setting {@link #isProtectedFromFishing} to true</p>
     * @return false if the tile is already protected, true if the tile was not protected
     */
    public boolean protectTile(){
        if(this.isProtectedFromFishing) {
            return false;
        } else {
            return this.isProtectedFromFishing = true; //assignment operator returns the value assigned, thus it returns true
        }
    }


    /** <p>This method returns the number of fish, that have been fished up</p>
     *      * <p>Uses the hoursToFish, quality, and a random number to determine the amount of fish caught (and in future implements, netType)</p>
     *      * <p>The method decreases the quality after fishing have been completed</p>
     *      * <p>The methods decreases the number of fish in the tile, corresponding to the amount that have been fished up</p>
     *
     * For future implementations, remember to add param, netType, and let this have an effect on how much quality is decreased and how many fish are caught
     * Please use the netDestruction variable so the above will be easier to implement
     *
     * @param hoursToFish The number of hours to fish, in the initial implementation
     * @return the amount of fish, that have been caught, -1 if tile is protected
     */
    public Map<Fish, Integer> fishTile(int hoursToFish) throws TileProtectedFromFishingError{
        double netDestruction = DomainSettings.DEFAULTNETDESTRUCTION; //arbitrary number, perhaps the range 0 to 1 would be good
        double catchRate = DomainSettings.DEFAULTCATCHRATE; //depends on the nettype, and thus should be updated when different nets are implemented


        return fishTile(hoursToFish, netDestruction, catchRate);
    }


    /**
     * @TODO Check if the random generater works
     * @param hoursToFish
     * @param netDestruction
     * @param catchRate
     * @return
     * @throws TileProtectedFromFishingError
     */
    public Map<Fish, Integer> fishTile(int hoursToFish, double netDestruction, double catchRate) throws TileProtectedFromFishingError{
        if(!isProtectedFromFishing){
            Map<Fish, Integer> out = new HashMap<Fish, Integer>(); //the number of fish caught in this tile
            int min = 0;
            int max = 0;
            double diff = DomainSettings.VARIANCE; // the amount of variance in the caught fish

            for (Fish fish : Fish.values()){
                double fishCaughtAverage = this.habitatQuality * this.numberOfFish.get(fish) * catchRate; //maybe remove habitat quality from this line?
                fishCaughtAverage = (fishCaughtAverage <0) ? 0: fishCaughtAverage;
                min = (int) Math.round(fishCaughtAverage * (1 -diff));
                max = (int) Math.round(fishCaughtAverage * (1 + diff)); //maybe percentages dont work this way? we dont care
                out.put(fish, 0);

                //It is intentional that we do not update min and max along the way
                //by doing it this way, we provide an opportunity for the player to overfish, and an incentive to fish for long periods of time
                for (int i = 0; i < hoursToFish; i++) {
                    int randomNum = 0;
                    if(min != max){
                         randomNum = ThreadLocalRandom.current().nextInt(min, max + 1); //taken from: https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-ja
                    }
                    int temp = out.get(fish) + randomNum; //hack, should be correctly rounded
                    out.put(fish, temp);
                }

                int possibleFishNumber = this.numberOfFish.get(fish);
                if (this.decreaseNumberOfFish(fish, out.get(fish)) < 0){
                    out.put(fish, possibleFishNumber);
                }
            }

            this.updateQuality(-netDestruction);

            return out;
        }else{
            throw new TileProtectedFromFishingError();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

