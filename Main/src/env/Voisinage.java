package env;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Voisinage {
	Map<Direction,Case> voisins=new HashMap<>();
	public Voisinage(Grille grille,Case case_position)
	{
		if(case_position.getPosition(grille).x+1<=grille.getN()-1)
		{
			Case caseCheck=grille.checkCaisseNextPoint(new Point(case_position.getPosition(grille).x+1,
					case_position.getPosition(grille).y));
			voisins.put(Direction.Nord, caseCheck);
		}
		if(case_position.getPosition(grille).x-1>=0)
		{
			Case caseCheck=grille.checkCaisseNextPoint(new Point(case_position.getPosition(grille).x-1,
					case_position.getPosition(grille).y));
			voisins.put(Direction.Sud, caseCheck);
		}
		if(case_position.getPosition(grille).y+1<=grille.getM()-1)
		{
			Case caseCheck=grille.checkCaisseNextPoint(new Point(case_position.getPosition(grille).x,
					case_position.getPosition(grille).y+1));
			voisins.put(Direction.Est, caseCheck);
		}
		if(case_position.getPosition(grille).y-1>=0)
		{
			Case caseCheck=grille.checkCaisseNextPoint(new Point(case_position.getPosition(grille).x,
					case_position.getPosition(grille).y-1));
			voisins.put(Direction.Ouest, caseCheck);
		}
	}
	public Map<Direction, Case> getVoisins()
	{
		return voisins;
	}

	public void setVoisins(Map<Direction, Case> voisins)
	{
		this.voisins = voisins;
	}
}