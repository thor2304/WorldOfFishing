package oop.javafxtest.worldofzuul.Domain;

import oop.javafxtest.worldofzuul.Domain.DomainInterfaces.BoatActionsInterface;
import oop.javafxtest.worldofzuul.Domain.DomainInterfaces.CommandHandlingInterface;
import oop.javafxtest.worldofzuul.Domain.DomainInterfaces.GeneralGameInfoInterface;
import oop.javafxtest.worldofzuul.Errors.OutOfBoundsError;
import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;

import java.util.HashMap;
import java.util.Map;

public class Domain implements BoatActionsInterface, CommandHandlingInterface , GeneralGameInfoInterface {

    private Game game;

    public Domain() {
        this.game = new Game();
    }

    public static int getSettingsBoardSize(){
        return DomainSettings.BOARDSIZE;
    }

    public boolean protectTile() {
        return game.protectTile();
    }

    public int getCurrentXCoordinate () {
        return game.currentXCoordinate();
    }

    public int getCurrentYCoordinate () {
        return game.currentYCoordinate();
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
    public void setBoatHoursToFish(int hoursToFish) {
        game.boat.setHoursToFish(hoursToFish);
    }

    @Override
    public Map<String, Integer> fishCurrentTile() throws TileProtectedFromFishingError {
        return convertFishIntegerToStringIntegerMap(game.fish());
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

    @Override
    public double getDailyExpenses(){
        return game.boat.getDailyExpenses();
    }


    @Override
    public boolean checkWinCondition(){
        return game.checkWinCondition();
    }

    public double getGoldMissingToWin(){
        return game.getGoldMissingToWin();
    }
}

