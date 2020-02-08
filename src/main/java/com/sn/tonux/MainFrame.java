package com.sn.tonux;

import com.sn.tonux.graphic.draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author tonux
 */
public class MainFrame extends JFrame implements ActionListener, ChangeListener {

    private JSpinner choiceSpeed;
    private JButton buttonGame;
    private JButton buttonReset;
    private JLabel titlePanel;
    private JLabel textFinish;
    private JSpinner choiceNumberDisc;

    private com.sn.tonux.graphic.draw draw;

    public MainFrame() {
        super("Tour de Hanoi");
        configureWindow();
        initComponents();
        this.setVisible(true);
    }

    private void configureWindow() {
        this.setSize(720, 440);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {

        JPanel panel = new JPanel();

        titlePanel = new JLabel("Disques");
        panel.add(titlePanel);

        choiceNumberDisc = new JSpinner(new SpinnerNumberModel(8, 1, 8, 1));
        choiceNumberDisc.addChangeListener(this);
        panel.add(choiceNumberDisc);

        titlePanel = new JLabel("Vitesse");
        panel.add(titlePanel);
        choiceSpeed = new JSpinner(new SpinnerNumberModel(1, -5, 5, 1));
        choiceSpeed.addChangeListener(this);
        panel.add(choiceSpeed);

        buttonGame = new JButton("Commencer");
        buttonGame.addActionListener(this);
        panel.add(buttonGame);

        textFinish = new JLabel("FINISH!");
        textFinish.setForeground(Color.black);
        textFinish.setVisible(false);
        panel.add(textFinish);

        add(panel, BorderLayout.SOUTH);
        draw = new draw(8, 1, this);
        add(draw, BorderLayout.CENTER);


        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw.AnimationStop();
                buttonGame.setText("Recommencer");
                buttonReset.setVisible(false);
            }
        });
        buttonReset.setVisible(false);
        panel.add(buttonReset);


    }


    public void actionPerformed(ActionEvent e) {
        switch(buttonGame.getText()) {
            case "Pause":
                draw.AnimationStop();
                buttonGame.setText("Continue");
                buttonReset.setVisible(true);
                break;
            case "Recommencer":
                draw = new draw(Integer.parseInt(choiceNumberDisc.getValue().toString()), Integer.parseInt(choiceSpeed.getValue().toString()), this);
                add(draw, BorderLayout.CENTER);
                buttonGame.setText("Commencer");
                textFinish.setVisible(false);
                buttonReset.setVisible(true);
                this.setVisible(true);
                break;
            case "Continue":
            case "Commencer":
                draw.animation();
                buttonGame.setText("Pause");
                buttonReset.setVisible(true);
                break;
            default:

        }

    }

    public void stateChanged(ChangeEvent e) {
        draw.AnimationStop();
        buttonGame.setText("Commencer");
        textFinish.setVisible(false);
        draw = new draw(Integer.parseInt(choiceNumberDisc.getValue().toString()), Integer.parseInt(choiceSpeed.getValue().toString()), this);
        add(draw, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void resolutionCompleted() {
        buttonGame.setText("Recommencer");
        textFinish.setVisible(true);
    }
}
