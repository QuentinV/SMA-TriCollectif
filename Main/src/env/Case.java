package env;

import java.awt.Point;

public abstract class Case {
	protected String label;

    public Case(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }
}
