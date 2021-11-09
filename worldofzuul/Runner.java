package worldofzuul;

public class Runner {
    public static Game game;
    public static void main(String[] args){
        game = new Game();
        game.setBoat(); //Necessary to initialize the boat
        //we would love to have this as part of the Game constructor, but there were certain problems initially
        //Cleaning this up is on the roadmap

        game.play();

    }
}
