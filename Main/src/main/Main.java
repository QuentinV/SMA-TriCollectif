package main;

import agents.Agent;
import env.Grille;
import ui.Console;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        float kprise = (float) 0.1;
        float kdepot = (float) 0.3;
        int nbAgents = 1, nbObjects = 30, mem = 5; //20 , 200, 10

        Grille g = new Grille(20, 20, 1); //50, 50

        Factory f = new Factory(g, nbAgents, nbObjects, mem, kprise, kdepot);
        List<Thread> tAgents = new ArrayList<>();
        List<Agent> agents = f.creationAgents();

        Console console = new Console(g);
        console.println();

        //Demarrer tous les agents
        for (Agent a : agents) {
            Thread t = new Thread(a);
            tAgents.add(t);
            t.start();
        }

        for (;;)
        {
            console.println();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
