package worldofzuul.DisplayInterfaces;

import worldofzuul.Fish;

import java.util.Map;

public interface DisplayFishingResult {
    void displayFishingResult (double catchRate, int hoursFished, Fish fishType);
    void displayCurrentFish(Map<Fish, Integer> fish);
}
