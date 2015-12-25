package agents;

import java.awt.Point;
import java.util.Random;

import env.Caisse;
import env.Case;
import env.Direction;
import env.Grille;
import env.Voisinage;

public class Agent implements Runnable, Case
{
	private Memoire memoire;
	private Point nextPosition;
	private double kPrise, kDepot;
	private Grille grille;

	public Agent(int sizeMem, double kPrise, double kDepot, Grille grille)
	{
		this.grille = grille;
		this.kPrise = kPrise;
		this.kDepot = kDepot;
		this.memoire = new Memoire(sizeMem);
	}

	public Point nextPosition()
	{
		Point point = null;
		do
		{
			// prend position courante+random nord sud est ouest avec
			// deplacement en fonction de i de grille
			Random rand = new Random();
			int orientation = rand.nextInt(4);
			switch (orientation)
			{

			case 0:
				if (getPosition(grille).x < grille.getN()-1)
				{
					do
					{
					point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Nord).x,
						getPosition(grille).y+ grille.getNumberInDirection(Direction.Nord).y);
					}while(point.x>grille.getN()-1);
				}
			case 1:
				if (getPosition(grille).x > 0)
				{
					do
					{
						point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Sud).x,
								getPosition(grille).y+ grille.getNumberInDirection(Direction.Sud).y);
					}while(point.x < 0);
				}
			case 2:
				if (getPosition(grille).y < grille.getM()-1)
				{
					do
					{
						point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Est).x,
								getPosition(grille).y+ grille.getNumberInDirection(Direction.Est).y);
					}while(point.y>grille.getM()-1);
				}
			case 3:
				if (getPosition(grille).y > 0)
				{
					do
					{
						point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Ouest).x,
								getPosition(grille).y+ grille.getNumberInDirection(Direction.Ouest).y);
					}while(point.y<0);
				}
			}
		} while (point == null);
		this.nextPosition=point;
		return nextPosition;
	}

	public float calculPprise(Case nextCase)
	{
		float res = 0;

		if (nextCase != null && nextCase instanceof Caisse)
		{
			double fp = memoire.proportion(((Caisse) nextCase).getLabel());
			res = (float) (this.kPrise / (this.kPrise + fp));
		}
		return res;
	}

	public float calculPdepot(Voisinage voisinage)
    {
    	float res=0;
    	if(voisinage!=null && voisinage instanceof Voisinage)
    	{
    	
    }

	@Override
	public void run()
	{
		// a mettre avant d'effectuer le traitement sur les
		Case nextCase = grille.checkCaisseNextPoint(nextPosition);
	}

	@Override
	public Point getPosition(Grille grille)
	{
		return grille.getPosition(this);
	}
}
