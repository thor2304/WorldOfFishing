package worldofzuul;

import worldofzuul.Display.GameLoop;

public class Runner {
    public static GameLoop gameLoop;
    public static void main(String[] args){
        gameLoop = new GameLoop();
        //we would love to have this as part of the Game constructor, but there were certain problems initially
        //Cleaning this up is on the roadmap

        gameLoop.play();

    }
}
