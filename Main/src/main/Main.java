package main;

import agents.Agent;
import env.Grille;
import ui.Console;
import ui.Controller;
import ui.MainWindow;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        float kprise = (float) 0.1;
        float kdepot = (float) 0.3;
        int nbAgents = 20, nbObjects = 200, mem = 10;

        Grille g = new Grille(50, 50, 1);

        Agent.REFRESH_TIME = 50; //ms

        MainWindow mw = new MainWindow(g);
        mw.addController(new Controller(mw, nbAgents, kprise, kdepot, nbObjects, mem));

        mw.setVisible(true);
    }
}
