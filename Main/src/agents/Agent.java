package agents;

import java.awt.Point;
import java.util.Random;

import env.Caisse;
import env.Case;
import env.Direction;
import env.Grille;

public class Agent implements Runnable, Case {
    private Memoire memoire;
    private Point nextPosition;
    private double kPrise, kDepot;
    private Grille grille;
    public Agent(int sizeMem, double kPrise, double kDepot,Grille grille)
    {
    	this.grille=grille;
        this.kPrise = kPrise;
        this.kDepot = kDepot;
        this.memoire = new Memoire(sizeMem);
    }
    public Point nextPosition()
    {
    	//prend position courante+random nord sud est ouest avec deplacement en fonction de i de grille
    	Random rand=new Random();
    	int orientation=rand.nextInt(4);
    	switch(orientation)
    	{
    	case 0:this.nextPosition= new Point(getPosition(grille).x+grille.getNumberInDirection(Direction.Nord).x,getPosition(grille).y+grille.getNumberInDirection(Direction.Nord).y);
    		return nextPosition;
    	case 1:this.nextPosition=new Point(getPosition(grille).x+grille.getNumberInDirection(Direction.Sud).x,getPosition(grille).y+grille.getNumberInDirection(Direction.Sud).y);
    		return nextPosition;
    	case 2:this.nextPosition=new Point(getPosition(grille).x+grille.getNumberInDirection(Direction.Est).x,getPosition(grille).y+grille.getNumberInDirection(Direction.Est).y);
    		return nextPosition;
    	case 3:this.nextPosition=new Point(getPosition(grille).x+grille.getNumberInDirection(Direction.Ouest).x,getPosition(grille).y+grille.getNumberInDirection(Direction.Ouest).y);
    		return nextPosition;
    	}
    	return null;
    }
    public float calculPprise()
    {
    	Case nextCase=grille.checkCaisseNextPoint(nextPosition);
    	if(nextCase instanceof Caisse)
    	{
    	double fp=memoire.proportion(((Caisse)nextCase).getLabel());
    	float res=(float) (this.kPrise/(this.kPrise+fp));
    	return res;
    	}
    }
    
    @Override
    public void run()
    {
        
    }
	@Override
	public Point getPosition(Grille grille)
	{
		return grille.getPosition(this);
	}
}
