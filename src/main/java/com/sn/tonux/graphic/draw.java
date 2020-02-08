package com.sn.tonux.graphic;

import com.sn.tonux.MainFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Tonux
 */
public class draw extends JPanel implements ActionListener {

    private MainFrame mainFrame;
    private Move[] moves;
    private Position[] positions;
    private Timer timer;
    private boolean moveDone;
    private int pas;
    private int count;
    private int x, y;
    private int pile;
    private int nm;
    private int numberDisc;
    private int[] tours;
    private int discMoved;
    private Image[] disc;
    private static final int LIMIT_DISQUE = 8;
    private static final int NUMBER_TOUR = 3;

    public draw(int numberDisc, int speed, MainFrame mainFrame) {
        this.numberDisc = numberDisc;
        this.mainFrame = mainFrame;
        configurePanel();
        initializeComponents();
        initializeAnimationComponents();
        timer = new Timer(speed, this);
    }

    private void configurePanel() {
        setDoubleBuffered(true);
        setBackground(Color.darkGray);
    }

    private void initializeComponents() {
        disc = new Image[LIMIT_DISQUE + 1];
        for (int i = 1; i <= LIMIT_DISQUE; i++) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("/com/sn/tonux/resources/" + i + ".png"));
            disc[i] = ii.getImage();
        }
    }

    private void initializeAnimationComponents() {
        nm = 0;
        tours = new int[NUMBER_TOUR + 1];
        tours[1] = numberDisc;
        tours[2] = 0;
        tours[3] = 0;
        pile = 1;
        moves = new Move[(int) Math.pow(2, numberDisc)];
        moveDisc(numberDisc, 1, 2, 3); // faire le mouvement
        positions = new Position[9];
        for (int i = 1; i <= numberDisc; i++) {
            int w = numberDisc - i + 1;
            positions[i] = new Position(PileXPosition(i, 1), PileYPosition(w));
        }
        x = positions[1].getX();
        y = positions[1].getY();
        discMoved = 1;
        moveDone = false;
        pas = 1;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        for (int i = numberDisc; i >= 1; i--) {
            g2.drawImage(disc[i], positions[i].getX(), positions[i].getY(), this);
        }

        drawTour(g2, 0);
        drawTour(g2, 200);
        drawTour(g2, 400);

        g2.drawString("I", 134, 320);
        g2.drawString("II", 332, 320);
        g2.drawString("III", 531, 320);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    private void drawTour(Graphics2D g2, int x) {
        int y1 = 60;
        int y2 = 298;
        g2.drawLine(133+x, y1, 133+x, y2);
        g2.drawLine(134+x, y1, 134+x, y2);
        g2.drawLine(135+x, y1, 135+x, y2);
        g2.drawLine(136+x, y1, 136+x, y2);
        g2.drawLine(137+x, y1, 137+x, y2);          
        g2.drawLine(138+x, y1, 138+x, y2);
        g2.drawLine(139+x, y1, 139+x, y2);
    }

    public void moveDisc(int n, int origin, int temporal, int destination) {
        if (n == 0) {
            return;
        }
        moveDisc(n - 1, origin, destination, temporal);
        nm++;
        moves[nm] = new Move(n, origin, destination);
        moveDisc(n - 1, temporal, origin, destination);
        count ++;
    }

    public void actionPerformed(ActionEvent e) {
        switch (pas) {
            case 1:
                if (y > 30) {
                    y--;
                    positions[pile].setY(y);
                } else {
                    if (moves[discMoved].getOrigin() < moves[discMoved].getDestination()) {
                        pas = 2;
                    } else {
                        pas = 3;
                    }
                }
                break;
            case 2:
                if (x < PileXPosition(pile, moves[discMoved].getDestination())) {
                    x++;
                    positions[pile].setX(x);
                } else {
                    pas = 4;
                }
                break;
            case 3:
                if (x > PileXPosition(pile, moves[discMoved].getDestination())) {
                    x--;
                    positions[pile].setX(x);
                } else {
                    pas = 4;
                }
                break;
            case 4:
                int niveau = tours[moves[discMoved].getDestination()] + 1;
                if (y < PileYPosition(niveau)) {
                    y++;
                    positions[pile].setY(y);
                } else {
                    moveDone = true;
                }
                break;
        }
        if (moveDone) {
            pas = 1;
            tours[moves[discMoved].getDestination()]++;
            tours[moves[discMoved].getOrigin()]--;
            discMoved++;
            if (discMoved == (int) Math.pow(2, numberDisc)) {
                timer.stop();
                mainFrame.resolutionCompleted();
                System.out.println(count);
            } else {
                moveDone = false;
                pile = moves[discMoved].getDisc();
                x = positions[pile].getX();
                y = positions[pile].getY();
            }
        }
        repaint();
    }

    public static int PileXPosition(int disc, int tour) {
        int x = (tour - 1) * 200;
        int l = 40;
        switch (disc) {
            case 1:
                return x + (l+70);
            case 2:
                return x + (l+60);
            case 3:
                return x + (l+50);
            case 4:
                return x + (l+40);
            case 5:
                return x + (l+30);
            case 6:
                return x + (l+20);
            case 7:
                return x + (l+10);
            case 8:
                return  x + l;
        }
        return 0;
    }

    public static int PileYPosition(int disc) {
        int y = 71;
        int h = 27;
        switch (disc) {
            case 1:
                return y+(7*h);
            case 2:
                return y+(6*h);
            case 3:
                return y+(5*h);
            case 4:
                return y+(4*h);
            case 5:
                return y+(3*h);
            case 6:
                return y+(2*h);
            case 7:
                return y+h;
            case 8:
                return y;
        }
        return 0;
    }

    public void animation() {
        timer.restart();
    }

    public void AnimationStop() {
        timer.stop();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
