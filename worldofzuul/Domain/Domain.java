package worldofzuul.Domain;

import worldofzuul.Domain.DomainInterfaces.BoatActionsInterface;
import worldofzuul.Domain.DomainInterfaces.CommandHandlingInterface;
import worldofzuul.Domain.DomainInterfaces.GeneralGameInfoInterface;
import worldofzuul.Errors.OutOfBoundsError;
import worldofzuul.Errors.TileProtectedFromFishingError;
import worldofzuul.Errors.TooManyHoursToFishError;

import java.util.HashMap;
import java.util.Map;

public class Domain implements BoatActionsInterface, CommandHandlingInterface , GeneralGameInfoInterface {
    private Game game;

    public Domain() {
        this.game = new Game();
    }

    @Override
    public double returnGoldForSoldFish() {
        return game.boat.sellFish();
    }

    @Override
    public double getBoatGold() {
        return game.boat.getGoldStorage();
    }

    @Override
    public Map<String, Integer> getBoatCaughtFish() {
        return convertFishIntegerToStringIntegerMap(game.boat.getCatchAmount());
    }

    @Override
    public int getBoatHoursToFish() {
        return game.boat.getHoursToFish();
    }

    @Override
    public Map<String, Integer> fishCurrentTile(String hoursToFish) throws TileProtectedFromFishingError, TooManyHoursToFishError {
        return convertFishIntegerToStringIntegerMap(game.fish(hoursToFish));
    }

    @Override
    public String goInDirection(String direction) throws OutOfBoundsError {
        return game.goTile(direction);
    }

    @Override
    public String getCurrentTileDescription() {
        return game.getCurrentTile().getLongDescription();
    }


    private Map<String, Integer> convertFishIntegerToStringIntegerMap(Map<Fish, Integer> fishIntegerMap) {
        Map<String, Integer> out = new HashMap<>();
        for (Fish fish : fishIntegerMap.keySet()) {
            out.put(fish.toString(), fishIntegerMap.get(fish));
        }
        return out;
    }
}
