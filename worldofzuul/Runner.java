package worldofzuul;

public class Runner {
    public static Game game;
    public static void main(String[] args){
        game = new Game();
        game.setBoat();
//        System.out.println("test");
//        System.out.println("live test");
//        System.out.println();
//        System.out.println("Thor er sej");
//        System.out.println("Daniella test");
        game.play();

    }
}
