package ui;

import env.Case;
import env.Grille;

import java.awt.*;

public class Console {
    private final Grille modGrille;

    public Console(Grille modGrille) {
        this.modGrille = modGrille;
    }

    public void println()
    {
        for (int i = 0; i < 10; ++i)
            System.out.println();

        String top = "";
        String content = "";
        String bottom = "";

        for (int i = 0; i <  modGrille.getN(); ++i)
        {
            top += "---";
            bottom += "---";
            content += "| ";
            for (int j = 0; j < modGrille.getM(); ++j)
            {
                Case ca = modGrille.getCaseAt(new Point(i, j));

                content += (ca == null) ? "   " : ca.toString();
            }
            content += " |";
            content += "\n";
        }

        top = "/-"+top+"-\\";
        bottom = "\\-"+bottom+"-/";

        System.out.println (top);
        System.out.print(content);
        System.out.println(bottom);
    }
}
