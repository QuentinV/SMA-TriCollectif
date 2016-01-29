package main;

import agents.Agent;
import env.Caisse;
import env.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Factory {
    private List<Agent> agents;
    private Grille grille;
    private int sizeMem, nbAgents, nbCaisse;
    private double kPrise, kDepot;

    public Factory(Grille grille, int nbAgents, int nbCaisse, int sizeMem, double kPrise, double kDepot) {
        this.grille = grille;
        this.sizeMem = sizeMem;
        this.kPrise = kPrise;
        this.kDepot = kDepot;
        this.nbAgents = nbAgents;
        this.nbCaisse = nbCaisse;
        this.agents = new ArrayList<>();
    }

    protected Agent createAgent()
    {
        String name = "R"+String.valueOf(agents.size());
        if (name.length() == 2)
            name += " ";

        Agent a = new Agent(name, sizeMem, kPrise, kDepot, grille);
        agents.add(a);

        return a;
    }

    public List<Agent> creationAgents()
    {
        LinkedList<Point> points = new LinkedList<>();

        for (int x = 0; x < grille.getN(); x++)
            for (int y = 0; y < grille.getM(); y++)
                points.add(new Point(x, y));

        Random r = new Random();

        for (int i = 0; i < nbAgents; ++i)
        {
            int rand = r.nextInt(points.size());
            Point p = points.get(rand);
            points.remove(rand);

            Agent a = this.createAgent();

            grille.addCaseAtPos(a, p);
        }

        for (int i = 0; i < nbCaisse; ++i)
        {
            int rand = r.nextInt(points.size());
            Point p = points.get(rand);
            points.remove(rand);

            int randLabel = r.nextInt(2);
            String label = (randLabel == 0) ? " A " : " B ";

            grille.addCaseAtPos(new Caisse(label), p);
        }

        return agents;
    }

    public List<Agent> getAgents() {
        return agents;
    }
}
