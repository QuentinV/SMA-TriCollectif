package main;

import env.Grille;
import ui.Controller;
import ui.MainWindow;

public class Main {
    public static void main(String[] args)
    {
        float kprise = (float) 0.1;
        float kdepot = (float) 0.3;

        MainWindow window = new MainWindow(new Grille(50, 50, 1));
        Controller controller = new Controller(window, 10, kprise, kdepot, 200);

        window.addController(controller);

        window.setVisible(true);
    }
}
