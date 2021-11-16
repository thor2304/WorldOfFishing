package worldofzuul.Display.DisplayInterfaces;

import worldofzuul.Domain.Fish;

import java.util.Map;

public interface DisplayFishingResult {
    void displayFishingResult (double catchRate, int hoursFished, Fish fishType);
    void displayCurrentFish(Map<Fish, Integer> fish);
}
