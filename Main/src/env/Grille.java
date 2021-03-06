package env;

import java.awt.Point;
import java.util.Random;

import agents.Agent;

public class Grille {
    private Case[][] matrice; //Agent, Caisse ,Liste de caisse ou null

    private final int N;
    private final int M;

    private final int i; //d�placement al�atoire de 1 � i

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
    			if(matrice[i][j] != null && matrice[i][j].equals(c))
    			{
    				return new Point(i,j);
    			}
    		}
    	}
    	return null;
    }

    public Case getCaseAt(Point p)
    {
    	return matrice[p.x][p.y];
    }

    public synchronized void cleanCaseAt(Point p)
    {
        if (p == null) return;
        matrice[p.x][p.y] = null;
    }

    public int getN()
	{
		return N;
	}

	public int getM()
	{
		return M;
	}

    public int getI() {
        return i;
    }

    public Voisinage getVoisinage(Point position)
    {
        if (position == null)
            return null;

        Voisinage voisinage = new Voisinage();

        if(position.x+1 <= this.getN()-1)
        {
            Point p = new Point(position.x + 1, position.y);
            voisinage.addVoisin(Direction.Nord, this.getCaseAt(p), p);
        }

        if(position.x-1 >= 0)
        {
            Point p = new Point(position.x - 1, position.y);
            voisinage.addVoisin(Direction.Sud, this.getCaseAt(p), p);
        }

        if(position.y+1 <= this.getM()-1)
        {
            Point p = new Point(position.x, position.y + 1);
            voisinage.addVoisin(Direction.Est, this.getCaseAt(p), p);
        }

        if(position.y-1 >= 0)
        {
            Point p = new Point(position.x, position.y - 1);
            voisinage.addVoisin(Direction.Ouest, this.getCaseAt(p), p);
        }

        return voisinage;
    }

    public Voisinage getVoisinage(Case c)
    {
        return getVoisinage(getPosition(c));
    }

	public synchronized boolean move(Agent a, Point nextPosition)
    {
		if(nextPosition.x > N-1 || nextPosition.y > M-1 || nextPosition.x<0 || nextPosition.y<0)
			return false;

        if(matrice[nextPosition.x][nextPosition.y] == null)
        {
            Point p = this.getPosition(a);

            matrice[p.x][p.y] = null;
            matrice[nextPosition.x][nextPosition.y] = a;

            return true;
        }

        return false;
    }

}
