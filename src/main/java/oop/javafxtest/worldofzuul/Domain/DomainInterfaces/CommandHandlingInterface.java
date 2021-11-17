package oop.javafxtest.worldofzuul.Domain.DomainInterfaces;

import oop.javafxtest.worldofzuul.Errors.OutOfBoundsError;
import oop.javafxtest.worldofzuul.Errors.TileProtectedFromFishingError;
import oop.javafxtest.worldofzuul.Errors.TooManyHoursToFishError;

import java.util.Map;

public interface CommandHandlingInterface {
    Map<String, Integer> fishCurrentTile() throws TileProtectedFromFishingError;
    String goInDirection(String direction) throws OutOfBoundsError;
}
