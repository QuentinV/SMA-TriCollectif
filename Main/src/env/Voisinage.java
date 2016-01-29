package env;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Voisinage {
    public static class Voisin {
        private Case c;
        private Point p;

        public Voisin(Case c, Point p) {
            this.c = c;
            this.p = p;
        }

        public Case getC() {
            return c;
        }

        public Point getP() {
            return p;
        }
    };

	private Map<Direction, Voisin> voisins;

	public Voisinage()
	{
	    voisins = new HashMap<>();
	}

    public void addVoisin(Direction d, Case c, Point p)
    {
        voisins.put(d, new Voisin(c, p));
    }

	public Map<Direction, Voisin> getVoisins()
	{
		return voisins;
	}
}