package worldofzuul;

import java.util.Set;
import java.util.HashMap;


public class Tile
{
    private String description;
    private HashMap<String, Tile> exits;

    public Tile(String description)
    {
        this.description = description;
        exits = new HashMap<String, Tile>();
    }

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
}

