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
        if (size <= 0) return null;
        size--;
        return new Caisse(label);
    }

    public void addOne()
    {
        size++;
    }
}
