package env;

import agents.Agent;

public class Grille {
    private Case[][] matrice; //Agent, Caisse ou null

    private final int N;
    private final int M;

    private final int i;

    public Grille(int n, int m, int i)
    {
        N = n;
        M = m;
        this.i = i;
    }

    public void move(Agent a, Direction d)
    {

    }
}
