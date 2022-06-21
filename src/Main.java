/**
 * @author AndreyPavlov
 * @version 1.0
 * @description File for codeforces coding
 * */
import javax.swing.*;
import java.util.*;

public class Main {
    public static int max (int a, int b) {
        if (a > b)
            return a;
        return b;
    }

    public static void main(String[] args) {
        Ray Window = new Ray();
        Game game = new Game();
        Window.add(game);
    }
}
