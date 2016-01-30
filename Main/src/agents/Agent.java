package agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import env.*;

public class Agent extends Case implements Runnable
{
    public static int REFRESH_TIME = 10;
    private final Grille grille;

	private Memoire memoire;
	private double kPrise, kDepot;

	private Caisse maCaisse; // Caisse transporter par l'agent
    public Caisse getMaCaisse() {
        return maCaisse;
    }

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

        int nbMove = rand.nextInt(grille.getI())+1; //Nombre de case à bouger en fonction du param I de la grille

        Point nextPos = posMe;
        for (int i = 0; i < nbMove; ++i)
        {
            Voisinage v = grille.getVoisinage(nextPos);

            if (v.getVoisins().size() > 0)
            {
                List<Point> voisins = new ArrayList<>();
                for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                {
                    // Ne prendre que les vides
                    if (e.getValue().getC() == null)
                        voisins.add(e.getValue().getP());
                }

                // aller sur une case voisine vide aléatoire
                if (voisins.size() > 0)
                    nextPos = voisins.get(rand.nextInt(voisins.size()));
            } else
                break; // aucune case libre
        }

        return nextPos;
    }

	public float calculPPrise(Case c)
	{
		float res = 0;

		if (c != null)
		{
			double fp = memoire.proportion(c.getLabel());

            res = (float) (this.kPrise / (this.kPrise + fp));
		}

		return res;
	}

	public double calculPDepot(Voisinage voisinage, String typeCaisse)
    {
        double fd = 0;
        double res = 0;

        if(voisinage != null)
    	{
    		for(Map.Entry<Direction, Voisinage.Voisin> e : voisinage.getVoisins().entrySet())
    		{
                Case c = e.getValue().getC();
    			if(c != null && c.getLabel().contentEquals(typeCaisse))
                {
                    if (c instanceof Caisse)
                        fd++;
                    else if (c instanceof TasCaisse)
                        fd += ((TasCaisse)c).getSize(); // Plusieurs Caisse ..
                }
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
            for (;;)
            {
                Point nextPos = this.nextPos();
                Case nextCase = grille.getCaseAt(nextPos);

                // Bouger
                boolean hasMoved = false;
                if (nextCase == null)
                    hasMoved = grille.move(this, nextPos);

                if (hasMoved)
                {
                    // Observer le voisinage pour prendre ou déposer une caisse
                    Voisinage v = grille.getVoisinage(nextPos);

                    if (maCaisse == null)
                    {
                        for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                        {
                            // Trouver une caisse à prendre
                            if (e.getValue().getC() != null
                                    && (e.getValue().getC() instanceof Caisse || e.getValue().getC() instanceof TasCaisse)) {
                                int pPrise = (int) (calculPPrise(e.getValue().getC()) * 100);
                                if (rand.nextInt(100) <= pPrise) { // Youpi je la prends
                                    if (e.getValue().getC() instanceof Caisse) { // La caisse est toute seule
                                        maCaisse = (Caisse) e.getValue().getC();
                                        grille.cleanCaseAt(e.getValue().getP());
                                    } else { // elle fait partie d'un tas : on retire un element
                                        TasCaisse tc = (TasCaisse) e.getValue().getC();
                                        maCaisse = tc.getOneCaisse();
                                        if (tc.getSize() < 2) // Quand moins de 2 elements supprimer tas
                                        {
                                            grille.cleanCaseAt(e.getValue().getP());
                                            grille.addCaseAtPos(new Caisse(tc.getLabel()), e.getValue().getP());
                                        }
                                    }

                                    break;
                                }
                            }
                        }

                        memoire.add(maCaisse != null ? maCaisse.getLabel() : Memoire.EMPTY);
                    } else
                    { // action depot caisse
                        if (hasMoved)
                            memoire.add(Memoire.EMPTY);

                        int pDepot = (int) (calculPDepot(v, maCaisse.getLabel()) * 100);
                        if (rand.nextInt(100) < pDepot)
                        { // Depot de la caisse
                            for (Map.Entry<Direction, Voisinage.Voisin> e : v.getVoisins().entrySet())
                            {
                                if (e.getValue().getC() != null && e.getValue().getC().getLabel().contentEquals(maCaisse.getLabel()))
                                { // Empiler sur une caisse du même type
                                    if (e.getValue().getC() instanceof TasCaisse)
                                        ((TasCaisse) e.getValue().getC()).addOne();
                                    else // Creer un tas quand ajouté à une caisse
                                        grille.addCaseAtPos(new TasCaisse(maCaisse.getLabel(), 2), e.getValue().getP());

                                    maCaisse = null;
                                    break;
                                }
                            }
                        }
                    }
                }

                this.setChanged();
                this.notifyObservers();

                Thread.sleep(REFRESH_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
