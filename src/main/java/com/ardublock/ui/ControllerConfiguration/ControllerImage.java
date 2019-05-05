package com.ardublock.ui.ControllerConfiguration;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import com.ardublock.ui.ControllerConfiguration.InvisibleButton;

public class ControllerImage extends JPanel{

    private BufferedImage image;
    private BufferedImage usingImage;
    private ArrayList<BufferedImage> moduleImages = new ArrayList<BufferedImage>();

    public ControllerImage() {
//        try {
//            image = ImageIO.read(new File("image name and path"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            throw new NullPointerException("Отсутствует изображение контроллера");
//        }
    }

    public void addPicture(int numberOfModule){
        usingImage = moduleImages.get(numberOfModule);
        JLabel picLabel = new JLabel(new ImageIcon(usingImage));
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
