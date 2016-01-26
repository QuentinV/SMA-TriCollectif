package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import env.Case;
import env.Grille;

public class MainWindow extends JFrame implements Observer {
    private final Grille modGrille;
    private JPanel p;
    private JButton bStart;

    public MainWindow(Grille modGrille) throws HeadlessException {
        this.modGrille = modGrille;

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
        p.setLayout(new GridBagLayout());

        this.update();

        this.getContentPane().add(p);

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
        p.removeAll();
        p.validate();
        p.repaint();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        for (int x = 0; x < modGrille.getN(); ++x)
            for (int y = 0; y < modGrille.getM(); ++y) {
                c.gridx = y;
                c.gridy = x;

                Case ca = modGrille.checkCaisseNextPoint(new Point(x, y));

                Panel pl = new Panel();

                if (ca != null) {
                    if (ca.getLabel().contentEquals("A"))
                        pl.setBackground(Color.CYAN);
                    else if (ca.getLabel().contentEquals("B"))
                        pl.setBackground(Color.RED);
                    else
                        pl.setBackground(Color.BLACK);
                }

               // pl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                pl.setPreferredSize(new Dimension(12, 12));

                p.add(pl, c);
            }

        p.validate();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.update();
    }
}

