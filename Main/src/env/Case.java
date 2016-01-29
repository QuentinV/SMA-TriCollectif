package env;

import java.util.Observable;

public abstract class Case extends Observable {
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
