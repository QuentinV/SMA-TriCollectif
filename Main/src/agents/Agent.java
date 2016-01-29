package agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import env.Caisse;
import env.Case;
import env.Direction;
import env.Grille;
import env.Voisinage;

public class Agent extends Case implements Runnable
{
    public static int REFRESH_TIME = 1000;
    private final Grille grille;

	private Memoire memoire;
	private double kPrise, kDepot;

	private Caisse maCaisse;
    private int orientation;

	public Agent(String label, int sizeMem, double kPrise, double kDepot, Grille grille)
	{
        super(label);
        this.grille = grille;
		this.kPrise = kPrise;
		this.kDepot = kDepot;
		this.memoire = new Memoire(sizeMem);
	}

    public Point nextPos()
    {
        Random rand = new Random();

        Point posMe = grille.getPosition(this);

        int nbMove = rand.nextInt(grille.getI())+1; //Nombre de case à bouger
        if (maCaisse != null)
            nbMove--;

        Point nextPos = posMe;
        for (int i = 1; i < nbMove; ++i)
        {
            Voisinage v = grille.getVoisinage(nextPos);

            if (v.getVoisins().size() > 0)
            {
                List<Point> voisins = new ArrayList<>();
                for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                    if (e.getValue().getC() != null)
                    {
                        if (maCaisse == null && e.getValue().getC() instanceof Caisse)
                        {
                            int pPrise = (int)(calculPPrise(e.getValue().getC()) * 100);
                            if (rand.nextInt(100) <= pPrise)
                            { // Youpi je le prends
                                voisins.clear();
                                voisins.add(e.getValue().getP());
                                break;
                            }
                        }
                    } else
                        voisins.add(e.getValue().getP());

                // aller sur une case voisine vide aléatoire
                nextPos = voisins.get(rand.nextInt(voisins.size()));
            } else
                break; // aucune case libre
        }

        return nextPos;
    }

	public float calculPPrise(Case nextCase)
	{
		float res = 0;

		if (nextCase != null && nextCase instanceof Caisse)
		{
			double fp = memoire.proportion(nextCase.getLabel());
			res = (float) (this.kPrise / (this.kPrise + fp));
		}

		return res;
	}

	public double calculPDepot(Voisinage voisinage, String typeCaisse)
    {
        double fd = 0;
        double res = 0;

        if(voisinage != null && voisinage instanceof Voisinage)
    	{
    		for(Map.Entry<Direction, Voisinage.Voisin> e : voisinage.getVoisins().entrySet())
    		{
                Case c = e.getValue().getC();
    			if(c instanceof Caisse && c.getLabel().contentEquals(typeCaisse))
    				fd++;
    		}
    	}

    	res = (fd / (this.kDepot + fd));
        res = Math.pow(res, 2);

    	return res;
    }

	@Override
	public void run()
	{
        Random rand = new Random();

        try {
            Point nextPos = this.nextPos();
            Case nextCase = grille.getCaseAt(nextPos);

            if (nextCase != null && nextCase instanceof Caisse)
            { // Prendre la caisse et se déplacer
                maCaisse = (Caisse) nextCase;

                memoire.add(maCaisse.getLabel());

                grille.cleanCaseAt(nextPos);
                grille.move(this, nextPos);
            } else {
                if (maCaisse != null && (nextCase == this || nextCase == null))
                {
                    Voisinage v = grille.getVoisinage(nextCase);
                    int pDepot = (int) (calculPDepot(v, maCaisse.getLabel()) * 100);
                    if (rand.nextInt(100) < pDepot) {
                        for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                            if (e.getValue().getC() == null) {
                                grille.addCaseAtPos(e.getValue().getC(), e.getValue().getP());
                                maCaisse = null;
                                break;
                            }
                        if (grille.move(this, nextPos))
                            memoire.add("0");
                    } else {
                        List<Voisinage.Voisin> voisins = new ArrayList<>();
                        for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                            if (e.getValue().getC() == null)
                                voisins.add(e.getValue());

                        Point next = voisins.get(rand.nextInt(voisins.size())).getP();
                        if (grille.move(this, next))
                            memoire.add("0");
                    }
                }
            }

            Thread.sleep(REFRESH_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString()+(maCaisse != null ? maCaisse : "");
    }
}
