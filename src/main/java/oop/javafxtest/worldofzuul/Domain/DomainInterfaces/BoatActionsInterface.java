package oop.javafxtest.worldofzuul.Domain.DomainInterfaces;

import java.util.Map;

public interface BoatActionsInterface {
    double returnGoldForSoldFish();
    double getBoatGold();
    Map<String, Integer> getBoatCaughtFish();
    int getBoatHoursToFish();
    void setBoatHoursToFish(int hoursToFish);
}
