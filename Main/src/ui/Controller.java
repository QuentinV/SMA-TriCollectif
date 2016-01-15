package ui;

import agents.Agent;
import env.Caisse;
import env.Case;
import env.Grille;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Controller implements ActionListener {
    private final MainWindow view;

    private List<Agent> agents;
    private List<Thread> tAgents;

    private int nbAgents;
    private int sizeMem;
    private int kPrise;
    private int kDepot;
    private int nbCaisse;

    public Controller(MainWindow view, int nbAgent, float kPrise, float kDepot, int nbCaisse) {
        this.view = view;
        agents = new ArrayList<>();
        tAgents = new ArrayList<>();
        this.nbAgents = nbAgent;
        this.sizeMem = sizeMem;
        this.nbCaisse = nbCaisse;
    }



    protected Agent createAgent()
    {
        Agent a = new Agent(String.valueOf(agents.size()), sizeMem, kPrise, kDepot, view.getModGrille());
        agents.add(a);

        return a;
    }

    public void creationAgents()
    {
        LinkedList<Point> points = new LinkedList<>();

        for (int x = 0; x < view.getModGrille().getN(); x++)
            for (int y = 0; y < view.getModGrille().getM(); y++)
                points.add(new Point(x, y));

        Random r = new Random();

        for (int i = 0; i < nbAgents; ++i)
        {
            int rand = r.nextInt(points.size());
            Point p = points.get(rand);
            points.remove(rand);

            Agent a = this.createAgent();

            view.getModGrille().addCaseAtPos(a, p);
        }

        for (int i = 0; i < nbCaisse; ++i)
        {
            int rand = r.nextInt(points.size());
            Point p = points.get(rand);
            points.remove(rand);

            int randLabel = r.nextInt(2);
            String label = (randLabel == 0) ? "A" : "B";

            view.getModGrille().addCaseAtPos(new Caisse(label), p);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long timeStart = System.currentTimeMillis();

        view.getbSolve().setEnabled(false);
        Grille g = view.getModGrille();

        //creation des agents
        this.creationAgents();

        //Demarrer tous les agents
        for (Agent a : agents) {
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
