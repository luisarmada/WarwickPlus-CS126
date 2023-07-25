package screen;

import java.awt.*;

import javax.swing.*;

import stores.*;
import utils.*;

public class ConstructionScreen {
    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Genres");
        panel.setVisible(false);
        panel.removeAll();

        JPanel constructionOuter = new JPanel();
        constructionOuter.setBounds(5,5, (int) (panel.getWidth()*0.98), 30);
        constructionOuter.setForeground(Constants.fontColor);
        constructionOuter.setBackground(Constants.highlight);
        

        JLabel myText = new JLabel("Web page under construction...");
        myText.setForeground(Constants.fontColor);
        Dimension dim = myText.getPreferredSize();
        myText.setBounds(20, 20, dim.width, dim.height);
        myText.setHorizontalAlignment(JLabel.CENTER);
        myText.setVerticalAlignment(JLabel.CENTER);
        constructionOuter.add(myText);

        JPanel iconOuter = new JPanel();
        iconOuter.setBounds(5, (int) (panel.getHeight()*0.05), (int) (panel.getWidth()*0.98), (int) (panel.getHeight()*0.90));
        iconOuter.setForeground(Constants.fontColor);
        iconOuter.setBackground(Constants.highlight);

        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon("src/main/resources/img/construction.jpg"));
        icon.setHorizontalAlignment(JLabel.CENTER);
        icon.setVerticalAlignment(JLabel.TOP);
        iconOuter.add(icon);

        panel.add(constructionOuter);
        panel.add(iconOuter);
        panel.setVisible(true);
    }
}
