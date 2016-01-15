package env;

import agents.Agent;

import java.awt.*;
import java.util.Random;

public class Grille {
    private Case[][] matrice; //Agent, Caisse ou null

    private final int N;
    private final int M;

    private final int i; //déplacement aléatoire de 1 à i

    public Grille(int n, int m, int i)
    {
        matrice = new Case[n][m];
        N = n;
        M = m;
        this.i = i;
    }

    public void addCaseAtPos(Case c, Point p)
    {
        matrice[p.x][p.y] = c;
    }

    public Point getPosition(Case c)
    {
    	for(int i=0;i < N;i++)
    	{
    		for(int j=0; j < M;j++)
    		{
    			if(matrice[i][j].equals(c))
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
                case Nord:
                    return new Point(nbcase,0);
                case Sud:
                    return new Point(-nbcase,0);
                case Est:
                    return new Point(0,nbcase);
                case Ouest:
                    return new Point(0,-nbcase);
			}
    	}
    	return null;
	}

    public int getN()
	{
		return N;
	}

	public int getM()
	{
		return M;
	}

	public synchronized boolean move(Agent a, Point nextPosition)
    {
		if(nextPosition.x > N-1 || nextPosition.y > M-1)
		{
			System.out.println("En dehors des limites du tableau je bouges pas");
			return false;
		}
		else
		{
			if(matrice[nextPosition.x][nextPosition.y] == null)
			{
				System.out.println("je bouge");

                Point p = this.getPosition(a);

				matrice[p.x][p.y] = null;
				matrice[nextPosition.x][nextPosition.y] = a;
			}
			else
			{
				System.out.println("il y a une autre entité devant je peux pas me déplacer");
                return false;
			}
		}

        return false;
    }
}
