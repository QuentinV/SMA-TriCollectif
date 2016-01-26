package agents;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import env.Caisse;
import env.Case;
import env.Direction;
import env.Grille;
import env.Voisinage;
public class Agent extends Case implements Runnable
{
	private Memoire memoire;
	private double kPrise, kDepot;
	private Grille grille;
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

	public Point nextPosition()
	{
		Point point = null;
		do
		{
			// prend position courante+random nord sud est ouest avec
			// deplacement en fonction de i de grille
			Random rand = new Random();
			orientation = rand.nextInt(4);
			int offset=0;
			if(maCaisse!=null)
			{
				offset=1;
			}
			switch (orientation)
			{

                case 0:
                    if (getPosition(grille).x < grille.getN()-1-offset)
                    {
                        do
                        {
                            point = new Point(
                                    getPosition(grille).x+ grille.getNumberInDirection(Direction.Nord).x,
                                    getPosition(grille).y+ grille.getNumberInDirection(Direction.Nord).y
                            );
                        }while(point.x>grille.getN()-1-offset);
                    }
                    break;
                case 1:
                    if (getPosition(grille).x > 0+offset)
                    {
                        do
                        {
                            point = new Point(
                                    getPosition(grille).x+ grille.getNumberInDirection(Direction.Sud).x,
                                    getPosition(grille).y+ grille.getNumberInDirection(Direction.Sud).y
                            );
                        }while(point.x < 0+offset);
                    }
                    break;
                case 2:
                    if (getPosition(grille).y < grille.getM()-1-offset)
                    {
                        do
                        {
                            point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Est).x,
                                    getPosition(grille).y+ grille.getNumberInDirection(Direction.Est).y);
                        }while(point.y>grille.getM()-1-offset);
                    }
                    break;
                case 3:
                    if (getPosition(grille).y > 0+offset)
                    {
                        do
                        {
                            point = new Point(getPosition(grille).x+ grille.getNumberInDirection(Direction.Ouest).x,
                                    getPosition(grille).y+ grille.getNumberInDirection(Direction.Ouest).y);
                        }while(point.y<0+offset);
                    }
                    break;
                default:
                    break;
            }
		} while (point == null);

		return point;
	}
	public Point nextPosition(Caisse caisse,int orientation,Point nextPosition)
	{
		switch(orientation)
		{
			case 0:
			if(this.grille.checkCaisseNextPoint(new Point(nextPosition.x+1,nextPosition.y))==null)
				{return new Point(nextPosition.x+1,nextPosition.y);}
			break;
			case 1:
			if(this.grille.checkCaisseNextPoint(new Point(nextPosition.x-1,nextPosition.y))==null)
				{return new Point(nextPosition.x-1,nextPosition.y);}
			break;
			case 2:
			if(this.grille.checkCaisseNextPoint(new Point(nextPosition.x,nextPosition.y+1))==null)
				{return new Point(nextPosition.x,nextPosition.y+1);}
			break;
			case 3:
			if(this.grille.checkCaisseNextPoint(new Point(nextPosition.x,nextPosition.y-1))==null)
				{return new Point(nextPosition.x,nextPosition.y-1);}
		}
		return null;
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

	public double calculPdepot(Voisinage voisinage,String typeCaisse)
    {
        double fd=0;
        double res=0;

        if(voisinage!=null && voisinage instanceof Voisinage)
    	{
    		Map<Direction,Case> voisins=voisinage.getVoisins();
    		for(Map.Entry<Direction, Case> e : voisins.entrySet())
    		{
                Case c = e.getValue();
    			if(c instanceof Caisse &&
    					((Caisse)c).getLabel().contentEquals(typeCaisse))
    			{
    				fd++;
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
		// a mettre avant d'effectuer le traitement sur les
		//Case nextCase = grille.checkCaisseNextPoint(nextPosition());

        try {
        	Point point=this.nextPosition();
        	Case caseCheck=this.grille.checkCaisseNextPoint(point);
        	//si j'ai pas de caisse
        	if(maCaisse==null)
        	{
	        	//si la case est vide je bouge
	        	if(caseCheck==null)
	        	{
	        		this.grille.move(this, point);
	        		this.memoire.add("N");//pour null
	        	}
	        	//si c'est une caisse je la prend en fonction de pprise(à finir)
	        	else if(caseCheck instanceof Caisse && this.calculPprise(caseCheck)>0.5)
	        	{
	        		this.maCaisse=(Caisse)caseCheck;
	        	}
        	}
        	//si j'ai une caisse
        	else
        	{
        		Point nextPosCaisse=this.nextPosition(maCaisse, orientation,point);
        		if(nextPosCaisse!=null)
        		{
        			this.grille.move(this, point, nextPosCaisse, maCaisse);
        		}
        		else
        			// j'avance et dépose dans une liste de caisse en fonction de pdepot et si le label correspond(à faire)
        		{
        			this.maCaisse=null;
        		}
        	}
        	//sinon je dors tout de suite
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public Point getPosition(Grille grille)
	{
		return grille.getPosition(this);
	}
}
