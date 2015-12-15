package agents;


public class Memoire {
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
        return (value.split(type).length-1) / size;
    }

    public String getValue()
    {
        return value;
    }
}
