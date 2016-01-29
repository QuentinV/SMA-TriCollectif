package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import agents.Agent;
import env.Caisse;
import env.Case;
import env.Grille;
import env.TasCaisse;

public class MainWindow extends JFrame implements Observer {
    private final Grille modGrille;
    private JPanel p;
    private JPanel jpcases;
    private JButton bStart;
    private GridLayout g;
    private Map<Point,JPanel> cases;

    public MainWindow(Grille modGrille) throws HeadlessException {
        this.modGrille = modGrille;
        cases=new HashMap<>();
        setup();
    }

    public Grille getModGrille() {
        return modGrille;
    }

    private void setup()
    {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.PAGE_AXIS));

        JLabel titre = new JLabel("Tri collectif");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("serif", Font.PLAIN, 28));

        pNorth.add(titre);

        this.getContentPane().add(pNorth, BorderLayout.NORTH);

        p = new JPanel();
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.setLayout(new BorderLayout());
        jpcases = new JPanel();
        jpcases.setBackground(new Color(51, 102, 102));
        g = new GridLayout(modGrille.getN(), modGrille.getM());
        for (int x = 0; x < modGrille.getN(); ++x)
        {
            for (int y = 0; y < modGrille.getM(); ++y) {

                JPanel uneCase=new JPanel();
                uneCase.setBackground(Color.BLACK);
                uneCase.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                uneCase.setPreferredSize(new Dimension(15, 15));
                jpcases.add(uneCase);
                cases.put(new Point(x,y),uneCase);
            }
        }
        this.update();
        jpcases.setLayout(g);

        this.getContentPane().add(p);
        p.add(jpcases,BorderLayout.CENTER);

        bStart = new JButton("Start");
        this.getContentPane().add(bStart, BorderLayout.SOUTH);

        this.pack();
    }

    public void addController(Controller s)
    {
        bStart.addActionListener(s);
    }

    public JButton getbSolve() {
        return bStart;
    }

    public void update()
    {
        for (int x = 0; x < modGrille.getN(); ++x)
            for (int y = 0; y < modGrille.getM(); ++y) {

                Point p = new Point(x, y);
                Case ca = modGrille.getCaseAt(p);

                Color c = Color.BLACK;
                if (ca != null) {
                    if (ca.getLabel().contentEquals("A"))
                        c = (ca instanceof TasCaisse) ? new Color(0, 0, 120) : new Color(0, 0, 255);
                    else if (ca.getLabel().contentEquals("B"))
                        c = (ca instanceof TasCaisse) ? new Color(120, 0, 0) : new Color(255, 0, 0);
                    else if (ca instanceof Agent)
                    {
                        Caisse caisse = ((Agent) ca).getMaCaisse();
                        if (caisse != null)
                            if (caisse.getLabel().contentEquals("A"))
                                c = new Color(0, 255, 255);
                            else
                                c = new Color(255, 255, 0);
                        else
                            c = new Color(0, 120, 0);
                    }
                }

                cases.get(p).setBackground(c);
            }

        p.validate();
        p.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.update();
    }
}

