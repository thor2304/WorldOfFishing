package worldofzuul.Errors;

public class TileProtectedFromFishingError extends Exception {
    public TileProtectedFromFishingError() {
        super("Cannot fish in this tile because the tile is protected");
    }
}
