package agents;

import env.Case;

public class Agent implements Runnable, Case {
    private Memoire memoire;

    private double kPrise, kDepot;

    public Agent(int sizeMem, double kPrise, double kDepot)
    {
        this.kPrise = kPrise;
        this.kDepot = kDepot;
        this.memoire = new Memoire(sizeMem);
    }

    @Override
    public void run()
    {
        
    }
}
