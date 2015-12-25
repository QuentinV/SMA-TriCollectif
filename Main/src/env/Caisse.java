package env;

import java.awt.Point;

public class Caisse implements Case {
    private String label;
    
    public Caisse(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }

	public Point getPosition(Grille grille)
	{
		return grille.getPosition(this);
	}
}
