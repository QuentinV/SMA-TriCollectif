package env;

import java.awt.Point;
import java.util.Random;

import agents.Agent;

public class Grille {
    private Case[][] matrice; //Agent, Caisse ,Liste de caisse ou null

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

	public synchronized boolean move(Agent a,Point nextPositionAgent,Point nextPositionCaisse,Caisse maCaisse)
	{
		if(nextPositionCaisse.x > N-1 || nextPositionCaisse.y > M-1 || nextPositionCaisse.x<0 || nextPositionCaisse.y<0)
		{
			System.out.println("En dehors des limites du tableau je bouges pas");
			return false;
		}
		if(nextPositionAgent.x > N-1 || nextPositionAgent.y > M-1 || nextPositionAgent.x<0 || nextPositionAgent.y<0)
		{
			System.out.println("En dehors des limites du tableau je bouges pas");
			return false;
		}
		else
		{
			if(matrice[nextPositionCaisse.x][nextPositionCaisse.y] == null)
			{
				System.out.println("je bouge avec ma caisse");

                Point p = this.getPosition(a);
				matrice[p.x][p.y] = null;
				matrice[nextPositionAgent.x][nextPositionAgent.y] = a;
				Point pc=this.getPosition(maCaisse);
				matrice[pc.x][pc.y]=null;
				matrice[nextPositionCaisse.x][nextPositionCaisse.y]=maCaisse;
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
