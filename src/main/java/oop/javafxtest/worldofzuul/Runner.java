package oop.javafxtest.worldofzuul;

import oop.javafxtest.worldofzuul.Display.GameLoop;

public class Runner {
    public static GameLoop gameLoop;
    public static void main(String[] args){
        run();
    }

    public static String run(){
        gameLoop = new GameLoop();

        return gameLoop.showWelcome();
    }
}
