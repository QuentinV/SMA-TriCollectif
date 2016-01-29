package agents;


public class Memoire {
    public static double TAUX_ERREUR = 2;
    public static String EMPTY = "0";

    private String value;
    private int size;

    public Memoire(int size)
    {
        this.size = size;
        this.value = new String();
    }

    public void add(String m)
    {
        if (value.length() >= size)
            value = value.substring(1);

        value += m;
    }

    public double proportion(String type)
    {
        // Proportion avec erreur : nb type + TAUX_ERREUR * autre type (sauf 0) / total
        int nbType = value.split(type).length-1;
        int nbEmpty = value.split(EMPTY).length-1;

        return (nbType + TAUX_ERREUR * (size - nbType - nbEmpty)) / size;
    }

    public String getValue()
    {
        return value;
    }
}
