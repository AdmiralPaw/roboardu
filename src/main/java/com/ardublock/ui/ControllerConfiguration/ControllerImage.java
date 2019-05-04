package com.ardublock.ui.ControllerConfiguration;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration.Pin



public class ControllerImage extends JPanel {
    private BufferedImage image;
    private BufferedImage usingImage;
    private ArrayList<BufferedImage> moduleImages = new ArrayList<BufferedImage>();

    public ControllerImage() {
        setLayout(null);
        this.setBounds(0,0,150,150);
        try {
            image = ImageIO.read(new File("com/ardublock/ui/ControllerConfiguration/Images/mainImage.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new NullPointerException("Отсутствует изображение контроллера");
        }
    }

    public void setModuleImages(){
        try{
            moduleImages.add( ImageIO.read(new File("com/ardublock/ui/ControllerConfiguration/Images/1.png")));

        } catch(IOException ex){
            ex.printStackTrace();
            throw new NullPointerException("Не хватает каких то изображений");
        }
    }

    public void addModule(int numberOfModule, Pin pin){
        JLabel picLabel;
        switch (pin){
            case dir04pwm05:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case dir07pwm06:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d2:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d3:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d8:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d10:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d9:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case d11:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case a3:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case a2:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case a1:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case a0:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
            case i2c:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(new ImageIcon(usingImage));
                picLabel.setBounds(10,10,10,10);
                add(picLabel);
                break;
        }
    }

    public void addPicture(int numberOfModule,int x,int y,int width,int height){
        usingImage = moduleImages.get(numberOfModule);
        JLabel picLabel = new JLabel(new ImageIcon(usingImage));
        picLabel.setBounds(x,y,width,height);
        add(picLabel);
    }



    public void deletePicture(int numberOfModule){
        try{
            remove(numberOfModule);
        } catch(NullPointerException nul){
            throw new NullPointerException("Такого дерьма тут нет");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}

