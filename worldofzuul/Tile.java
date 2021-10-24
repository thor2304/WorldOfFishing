package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Tile
{
    private String description;
    private HashMap<String, Tile> exits;
    private int numberOfFish;
    private double habitatQuality;
    private boolean isProtectedFromFishing;
    private Fish fishInThisTile; //perhaps make this a list in the future for multiple different fish species in one tile
    //if we want to have more than one type of fish, look into hashmaps of fish and their number

    //constructor(s):
    public Tile(String description)
    {
        this.description = description;
        exits = new HashMap<String, Tile>();
    }

    /**Add these params to be initialized:
     * fishInThisTile
     * habitatQuality
     * numberOfFish
     *
     * @param description The text shown to the user when entering the Tile
     * @param habitatQuality The starting quality of the habitat
     * @param fishInThisTile The type of fish in this tile, a Fish object
     * @param numberOfFish The starting number of fish in this tile
     */
    public Tile(String description, double habitatQuality, Fish fishInThisTile, int numberOfFish){
        this(description);

        //implement declaration
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
        return "You are " + description + ".\n" + getExitString();
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
    private int increaseNumberOfFish(int numberOfNewFish){
        this.numberOfFish += numberOfNewFish;
        return this.numberOfFish;
    }

    /**Always check if the return of this method is negative, if so too many fish were removed
     *
     * @param numberOfFishToRemove The amount of fish to reduce the population by
     * @return the initial result of the removal of fish
     */
    private int decreaseNumberOfFish(int numberOfFishToRemove){
        this.numberOfFish -= numberOfFishToRemove;
        int out = this.numberOfFish;
        if(this.numberOfFish < 0){
            this.numberOfFish = 0;
        }

        return out;
    }

    /**Lots of things to do, refer to the comments in the body
     *
     */
    private void migrateFishPopulation(){
        //do all the complex migrate math
        //get migration rate from fishInThisTile.getMigrationRate
        //Compare quality of current tile vs tile to migrate to
        //check fish numbers of current tile vs tile to migrate to
        //watch out for overfilling a tile beyond its capacity
        //Migrate based on position (perhaps with amount of circle area overlap

        //remember to use the methods in this class, increaseFishPopulation and decreaseFishPopulation
        //remember that this method may have to be reworked to be a public method called from Game

        //make a decision of what the inputs should be
        //and what the return type should be

        //watch out for current/future problems (sending fish to a tile, that hasnt migrated yet)


        //think about different fish species and checking them (maybe important, maybe not to be decided)
    }

    /** implement the logic of how to grow or decrease the population
     * use this.habitatQuality to determine how much the population should increase or decrease
     * remember to get reproductionRate and deathRate from fishInThisTile.**
     *
     */
    public void updateFishNumbers(){

        increaseNumberOfFish(0);
        decreaseNumberOfFish(0);
        migrateFishPopulation();
        //maybe more?
    }

    /**Will add the input to habitatQuality
     *
     * @param amount the amount to change quality by
     */
    public void updateQuality(double amount){
        this.habitatQuality += amount;
    }

    /** Protects the tile, so fishing can no longer be done
     *
     * @return false if the tile is already protected, true if the tile was not protected
     */
    public boolean protectTile(){
        if(isProtectedFromFishing) {
            return false;
        } else {
            isProtectedFromFishing = true;
            return true;
        }

    }


    /**This method should return the number of fish, that have been fished up
     * Remember to use the hoursToFish, quality, and a random number to determine the amount of fish caught (and in future implements, netType)
     * remember to decrease quality after fishing have been completed
     * remember to  decrease the number of fish in the tile, corresponding to the amount that have been fished up
     *
     * For future implementations, remember to add param, netType, and let this have an effect on how much quality is decreased and how many fish are caught
     * Please use the netDestruction variable so the above will be easier to implement
     *
     * @param hoursToFish The number of hours to fish, in the initial implementation
     * @return the amount of fish, that have been caught, -1 if tile is protected
     */
    public int fishTile(int hoursToFish){
        double netDestruction = 0.1; //arbitrary number, perhaps the range 0 to 1 would be good
        double catchRate = 0.06; //depends on the nettype, and thus should be updated when different nets are implemented

        return fishTile(hoursToFish, netDestruction, catchRate);
    }

    public int fishTile(int hoursToFish, double netDestruction, double catchRate){
        if(!isProtectedFromFishing){
            int out = 0; //the number of fish caught in this tile
            int min = 0;
            int max = 0;
            double diff = 0.2; // the amount of variance in the caught fish

            double fishCaughtAverage = this.habitatQuality * this.numberOfFish * catchRate; //maybe remove habitat quality from this line?
            min = (int) Math.round(fishCaughtAverage * (1 -diff));
            max = (int) Math.round(fishCaughtAverage * (1 + diff)); //maybe percentages dont work this way? we dont care

            //It is intentional that we do not update min and max along the way
            //by doing it this way, we provide an opportunity for the player to overfish, and an incentive to fish for long periods of time
            for (int i = 0; i < hoursToFish; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1); //taken from: https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
                out += randomNum;
            }

            int possibleFishNumber = this.numberOfFish;
            if (this.decreaseNumberOfFish(out) < 0){
                out = possibleFishNumber;
            }

            this.updateQuality(-netDestruction);

            return out;
        }else{
            return -1;
        }
    }






}

