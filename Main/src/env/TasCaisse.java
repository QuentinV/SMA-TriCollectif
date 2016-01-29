package env;

public class TasCaisse extends Case {
    private int size;
    public TasCaisse(String typeCaisse, int size)
    {
        super(typeCaisse);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Caisse getOneCaisse()
    {
        if (!hasCaisseLeft()) return null;
        size--;
        return new Caisse(label);
    }

    public boolean hasCaisseLeft()
    {
        return size > 0;
    }

    public void addOne()
    {
        size++;
    }
}
