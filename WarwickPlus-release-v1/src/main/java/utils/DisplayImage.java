package utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DisplayImage extends JPanel{
    private BufferedImage rawImage;
    private Image image;
    private int boundary = 0;

    public DisplayImage(String fileName) throws IOException {
        rawImage = ImageIO.read(new File(fileName));
    }

    public DisplayImage(String fileName, int boundary, boolean URL) throws IOException {
        if (URL) {
            rawImage = ImageIO.read(new URL(fileName));
        } else {
            rawImage = ImageIO.read(new File(fileName));
        }
        this.boundary = boundary;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.getWidth() > this.getHeight()) {
            image = rawImage.getScaledInstance(-1, this.getHeight(), Image.SCALE_SMOOTH);
        } else {
            image = rawImage.getScaledInstance(this.getWidth(), -1, Image.SCALE_SMOOTH);
        }

        if (image.getWidth(this.getParent()) > this.getWidth()) {
            image = rawImage.getScaledInstance(this.getWidth(), -1, Image.SCALE_SMOOTH);
        }

        if (image.getHeight(this.getParent()) > this.getHeight()) {
            image = rawImage.getScaledInstance(-1, this.getHeight(), Image.SCALE_SMOOTH);
        }

        image = rawImage.getScaledInstance(image.getWidth(this.getParent())-boundary, image.getHeight(this.getParent())-boundary, Image.SCALE_SMOOTH);

        int xPos = (this.getWidth() / 2) - (image.getWidth(this.getParent()) / 2);
        int yPos = (this.getHeight() / 2) - (image.getHeight(this.getParent()) / 2);
        g.drawImage(image, xPos, yPos, this);
    }
}
