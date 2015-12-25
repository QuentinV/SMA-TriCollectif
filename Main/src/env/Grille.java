package env;

import java.awt.Point;
import java.util.Random;

import agents.Agent;

public class Grille {
    private Case[][] matrice; //Agent, Caisse ou null

    private final int N;
    private final int M;
    //déplacement aléatoire de 1 à i
    private final int i;

    public Grille(int n, int m, int i)
    {
        N = n;
        M = m;
        this.i = i;
    }
    public Point getPosition(Case c)
    {
    	for(int i=0;i<N;i++)
    	{
    		for(int j=0;j<M;j++)
    		{
    			if(matrice[i][j]==c)
    			{
    				return new Point(i,j);
    			}
    		}
    	}
    	return null;
    }
    public Case checkCaisseNextPoint(Point p)
    {
    	return matrice[p.x][p.y];
    }
    public Point getNumberInDirection(Direction d)
	{
    	if(d!=null)
    	{
			Random rand=new Random();
			//on se deplace de 1 à i+1
			int nbcase=rand.nextInt(this.i)+1;
			switch(d)
			{
			case Nord:return new Point(nbcase,0);
			case Sud:return new Point(-nbcase,0);
			case Est:return new Point(0,nbcase);
			case Ouest:return new Point(0,-nbcase);
			}
    	}
    	return null;
	}
    public void move(Agent a, Direction d)
    {

    }
}
