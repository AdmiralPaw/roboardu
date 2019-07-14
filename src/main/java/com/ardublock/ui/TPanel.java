package com.ardublock.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author User
 */
public class TPanel extends JPanel {

    private JTextArea tutorialText;
    private JButton prevButton;
    private JButton nextButton;
    private int width = 300;
    private int height = 500;

    public TPanel(TutorialPane tutorialPane) {
        this.setLayout(null);
        this.setOpaque(false);
        //-------------------------------------------
        tutorialText = new JTextArea();
        tutorialText.setOpaque(false);
        tutorialText.setFont(new Font("Impact", Font.PLAIN, 18));
        tutorialText.setForeground(Color.white);
        tutorialText.setWrapStyleWord(true);
        tutorialText.setLineWrap(true);
        tutorialText.setEditable(false);
        tutorialText.setSize(new Dimension(width, height - 40));
        tutorialText.setPreferredSize(new Dimension(width, height - 40));
        tutorialText.setText("Немного инфы про эту хрень поможет вам ей пользоваться"
                + " (зачем? займитесь лучше чем-нибудь полезным)");
        this.add(tutorialText);
        tutorialText.setLocation(0, 0);
        //--------------------------------------------
        prevButton = new JButton("НАЗАД");
        prevButton.setFont(new Font("Impact", Font.PLAIN, 14));
//        prevButton.setMargin(new Insets(0, 0, 0, 0));
//        prevButton.setIconTextGap(0);
        prevButton.setBorderPainted(false);
        prevButton.setBorder(null);
        prevButton.setFocusable(false);
        prevButton.setBackground(Color.white);
        prevButton.setSize(new Dimension(width / 2 - 10, 30));
        prevButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        this.add(prevButton);
        prevButton.setLocation(0, height - 40);
        //---------------------------------------------
        nextButton = new JButton("ПОНЯТНО");
        nextButton.setFont(new Font("Impact", Font.PLAIN, 14));
        nextButton.setBorderPainted(false);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);
        nextButton.setBackground(Color.white);
        nextButton.setSize(new Dimension(width / 2 - 10, 30));
        nextButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        this.add(nextButton);
        nextButton.setLocation(width / 2, height - 40);
        //---------------------------------------------
        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(300, 250));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tutorialPane.activeAnimPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                    if (tutorialPane.iter < tutorialPane.activeAnimPanels.size()) {
                        tutorialPane.lastIter = tutorialPane.iter; 
                        tutorialPane.iter++;
                        
                    }
                    tutorialPane.nextTutor();
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tutorialPane.activeAnimPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                    if (tutorialPane.iter != 0) {
                        tutorialPane.lastIter = tutorialPane.iter; 
                        tutorialPane.iter--;
                    }
                    tutorialPane.nextTutor();
                }
            }
        });
    }

    public void setText(String newText) {
        this.tutorialText.setText(newText);
    }

    public void changeLocation(int x, int y) {
        this.setLocation(x, y);
        this.getParent().repaint();
    }

    public void changeDimension(int width, int height) {
        this.setSize(width, height);
        this.getParent().repaint();
    }

    private void startAnimation() {
        ((AnimPanel) this.getParent()).startAnimation();
    }

}
