package worldofzuul.Domain.DomainInterfaces;

import worldofzuul.Errors.OutOfBoundsError;
import worldofzuul.Errors.TileProtectedFromFishingError;
import worldofzuul.Errors.TooManyHoursToFishError;

import java.util.Map;

public interface CommandHandlingInterface {
    Map<String, Integer> fishCurrentTile(String hoursToFish) throws TileProtectedFromFishingError, TooManyHoursToFishError;
    String goInDirection(String direction) throws OutOfBoundsError;
}
