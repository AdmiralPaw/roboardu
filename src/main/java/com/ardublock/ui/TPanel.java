package com.ardublock.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
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
    private int height = 300;

    public TPanel(TutorialPane tutorialPane) {
        this.setLayout(null);
        this.setOpaque(false);
        //-------------------------------------------
        tutorialText = new JTextArea();
        tutorialText.setOpaque(false);
        tutorialText.setFont(new Font("Impact", Font.PLAIN, 14));
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
        URL iconURL = TutorialPane.class.getClassLoader().getResource("com/ardublock/Images/tutorPrev.png");
        ImageIcon image = new ImageIcon(iconURL);
        Image imageRaw = image.getImage().getScaledInstance(
                width / 2 - 10, 30, java.awt.Image.SCALE_SMOOTH);
        prevButton = new JButton(new ImageIcon(imageRaw)/*"< НАЗАД"*/);
//        prevButton.setFont(new Font("Impact", Font.PLAIN, 14));
        prevButton.setMargin(new Insets(0, 0, 0, 0));
        prevButton.setIconTextGap(0);
        prevButton.setBorderPainted(false);
        prevButton.setBorder(null);
        prevButton.setFocusable(false);
        prevButton.setBackground(Color.white);
        prevButton.setSize(new Dimension(width / 2 - 10, 30));
        prevButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        this.add(prevButton);
        prevButton.setLocation(0, this.tutorialText.getHeight()/*height - 40*/);
        //---------------------------------------------
        iconURL = TutorialPane.class.getClassLoader().getResource("com/ardublock/Images/tutorNext.png");
        image = new ImageIcon(iconURL);
        imageRaw = image.getImage().getScaledInstance(
                width / 2 - 10, 30, java.awt.Image.SCALE_SMOOTH);
        nextButton = new JButton(new ImageIcon(imageRaw)/*"ДАЛЕЕ >"*/);
//        nextButton.setFont(new Font("Impact", Font.PLAIN, 14));
        nextButton.setMargin(new Insets(0, 0, 0, 0));
        nextButton.setIconTextGap(0);
        nextButton.setBorderPainted(false);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);
        nextButton.setBackground(Color.white);
        nextButton.setSize(new Dimension(width / 2 - 10, 30));
        nextButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        this.add(nextButton);
        nextButton.setLocation(width / 2, this.tutorialText.getHeight()/*height - 40*/);
        //---------------------------------------------
        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(300, 250));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tutorialPane.iter < tutorialPane.activeAnimPanels.size()) {
                    if (tutorialPane.activeAnimPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                        if (!tutorialPane.tutorIllumPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                            for (IllumPanel pane : tutorialPane.tutorIllumPanels.get(tutorialPane.iter)) {
                                pane.stopAnimation();
                            }
                        }
                        tutorialPane.lastIter = tutorialPane.iter;
                        tutorialPane.iter++;
                        tutorialPane.nextTutor();
                    }
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tutorialPane.iter > 0) {
                    if (tutorialPane.activeAnimPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                        if (!tutorialPane.tutorIllumPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
                            for (IllumPanel pane : tutorialPane.tutorIllumPanels.get(tutorialPane.iter)) {
                                pane.stopAnimation();
                            }
                        }
                        tutorialPane.lastIter = tutorialPane.iter;
                        tutorialPane.iter--;
                        tutorialPane.nextTutor();
                    }
                }
            }
        });
    }

    public void setText(String newText) {
        this.tutorialText.setText(newText);
    }

    public void changeLocation(Point p) {
        this.setLocation(p.x, p.y);
        this.repaint();
    }

    public void changeDimension(Dimension d) {
        this.width = d.width;
        this.height = d.height;
        
        this.setSize(new Dimension(width, height));
        this.repaint();
        
        tutorialText.setSize(new Dimension(width, height - 40));
        tutorialText.setPreferredSize(new Dimension(width, height - 40));
        tutorialText.repaint();
        
        prevButton.setSize(new Dimension(width / 2 - 10, 30));
        prevButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        prevButton.setLocation(0, this.tutorialText.getHeight()/*height - 40*/);
        prevButton.repaint();
        
        nextButton.setSize(new Dimension(width / 2 - 10, 30));
        nextButton.setPreferredSize(new Dimension(width / 2 - 10, 30));
        nextButton.setLocation(width / 2, this.tutorialText.getHeight()/*height - 40*/);
        nextButton.repaint();
        
        this.updateUI();
    }

    public void setTextColor(Color newColor) {
        this.tutorialText.setForeground(newColor);
    }

    private void startAnimation() {
        ((DarkPanel) this.getParent()).startAnimation();
    }

}
