package oop.javafxtest.worldofzuul.Domain;

import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;

import java.lang.reflect.Array;
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
    /**Basic Constructor which sets habitatQuality and numberOfFish to the defaults set in {@link DomainSettings}
     * @param description the text that the Tile displays when asked for a description
     * @param x The x coordinate that the Tile reports
     * @param y The y coordinate that the Tile
     */
    public Tile(String description, int x, int y)
    {
        this(description, DomainSettings.DEFAULTHABITATQUALITY, new HashMap<>(), x, y);
        for(Fish fish : Fish.values()) {
            this.numberOfFish.put(fish, DomainSettings.DEFAULTNUMBEROFFISH);
        }
    }

    /**Master constructor which takes all params to set
     * @param description The text shown to the user when entering the Tile
     * @param habitatQuality The starting quality of the habitat
     * @param numberOfFish The starting number of fish in this tile
     * @param x The x coordinate that the Tile reports
     * @param y The y coordinate that the Tile
     */
    public Tile(String description, double habitatQuality, Map<Fish, Integer> numberOfFish, int x, int y){
        this.description = description;
        this.exits = new HashMap<>();
        this.numberOfFish = numberOfFish;
        this.habitatQuality = habitatQuality;
        this.x = x;
        this.y = y;

        this.numberOfMigratedFish = new HashMap<>();
        for (Fish fish: Fish.values()) {
            this.numberOfMigratedFish.put(fish, 0);
        }
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
        System.out.println(this.description);
        System.out.println("increasing fish by: " + numberOfNewFish);
        System.out.println("Fish already there: " + currentFish);
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
            System.out.println("oof: " + fish);
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
            increaseNumberOfFish( fish, getExtraFish(fish.getReproductionRate() * this.habitatQuality, this.numberOfFish.get(fish), (int) Math.round(this.habitatQuality * DomainSettings.DEFAULTCARRYINGCAPACITYOFTILE)) );
            decreaseNumberOfFish( fish, (int) Math.round( 1/this.habitatQuality * fish.getDeathRate() * this.numberOfFish.get(fish) ) );
         }
    }

    private int getExtraFish(double growthRate, int currentPopulation, int carryingCap){
        double out;
        if(carryingCap > 0 && currentPopulation < carryingCap){
            out = (growthRate) * (1- currentPopulation/ (double) carryingCap) * currentPopulation;
            if(out <0){
                return 0;
            }
            return (int) out;
        }else{
            return 0;
        }
    }

    /**Is called directly by Game
     */
    public void migrateFishPopulation(){
        for (Fish currentFish: Fish.values()) {
            int numberOfFishToMigrate = (int) Math.round(numberOfFish.get(currentFish) / 10.0);

            Map<String, Double> neighbourMultipliers = new HashMap<>();
            double sum = 0;
            double multiplierValue;

            for(String neighbour: exits.keySet()){
                if(this.exits.get(neighbour).numberOfFish.get(currentFish) > 1){
                    multiplierValue = ( this.numberOfFish.get(currentFish) /
                            (double)this.exits.get(neighbour).numberOfFish.get(currentFish) )
                            * ( this.exits.get(neighbour).habitatQuality / this.habitatQuality);
                    neighbourMultipliers.put(neighbour, multiplierValue );
                }else{
                    multiplierValue = this.numberOfFish.get(currentFish)
                            * ( this.exits.get(neighbour).habitatQuality / this.habitatQuality);
                    neighbourMultipliers.put(neighbour, multiplierValue );
                }

                if(multiplierValue > 1){
                    sum += multiplierValue;
                }
            }

            for(String neighbour: neighbourMultipliers.keySet()){
                multiplierValue = neighbourMultipliers.get(neighbour);
                if(multiplierValue <= 1){
                    continue;
                }
                int prevFishInNeighbour = exits.get(neighbour).numberOfMigratedFish.get(currentFish);
                int fishToSwimOut = (int) Math.floor((multiplierValue / sum) * numberOfFishToMigrate);
                exits.get(neighbour).numberOfMigratedFish.put(currentFish, prevFishInNeighbour + fishToSwimOut);
                int prevFishInThisMigrated = this.numberOfMigratedFish.get(currentFish);
                this.numberOfMigratedFish.put(currentFish, prevFishInThisMigrated - fishToSwimOut);
            }
        }
    }





    /**Is also called directly by Game
     * sets {@link #numberOfFish} += {@link #numberOfMigratedFish}
     * <p>
     * It is intentional that we do not check if the amount of Fish is above the Tiles capacity.
     * By doing it this way, we allow a tile to potentially be overfilled,
     * but the fish would die by way of decrease numbers in the next round.
     * </p>
     * By doing it this way we also depend on migrate to ensure that fish does not really want to migrate from an
     * undercrowded tile to a more overcrowded tile
     *
     */
    public void completeMigration(){
        for(Fish fish : Fish.values()) {
            this.decreaseNumberOfFish(fish, -this.numberOfMigratedFish.get(fish));
            this.numberOfMigratedFish.put(fish,0);
        }
    }


/**Will add the input to {@link #habitatQuality}
     * @param amount the amount to change quality by
     */
    public void updateQuality(double amount){
        if (this.habitatQuality < DomainSettings.MAXIMUMHABITATQUALITY){
            this.habitatQuality += amount;
        }
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
                double fishCaughtAverage = this.numberOfFish.get(fish) * catchRate; 
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

