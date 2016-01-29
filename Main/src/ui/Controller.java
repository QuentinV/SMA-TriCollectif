package ui;

import agents.Agent;
import env.Grille;
import main.Factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener {
    private final MainWindow view;

    private int nbAgents;
    private int sizeMem;
    private int kPrise;
    private int kDepot;
    private int nbCaisse;

    public Controller(MainWindow view, int nbAgent, float kPrise, float kDepot, int nbCaisse, int sizeMem) {
        this.view = view;
        this.nbAgents = nbAgent;
        this.sizeMem = sizeMem;
        this.nbCaisse = nbCaisse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getbSolve().setEnabled(false);
        Grille g = view.getModGrille();

        //creation des agents
        Factory fact = new Factory(view.getModGrille(), nbAgents, nbCaisse, sizeMem, kPrise, kDepot);
        List<Agent> agents = fact.creationAgents();
        List<Thread> tAgents = new ArrayList<>();

        //Demarrer tous les agents
        for (Agent a : agents) {
            a.addObserver(view);
            Thread t = new Thread(a);
            tAgents.add(t);
            t.start();
        }

        view.update();

        //Attendre la fin des agents
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Thread t : tAgents)
                    try {
                        t.join();
                    } catch (InterruptedException e1) {
                    }
            }
        }).start();
    }
}
