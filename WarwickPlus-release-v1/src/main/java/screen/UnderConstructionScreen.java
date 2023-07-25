package screen;

import java.io.IOException;

import javax.swing.*;

import utils.Constants;
import utils.DisplayImage;

public class UnderConstructionScreen {
    public static void createPanel(JPanel panel) {
        System.out.println("Under construction page");
        panel.setVisible(false);
        panel.removeAll();

        try {
            DisplayImage underConstructionImage = new DisplayImage("src/main/resources/img/Unbuilt-Screen.png");
            underConstructionImage.setBounds(0, (int) (panel.getHeight() * 0.25), panel.getWidth(), (int) (panel.getHeight() * 0.25));
            panel.add(underConstructionImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel noticeLabel = new JLabel("This page is under construction!");
        noticeLabel.setBounds(0, (int) (panel.getHeight() * 0.5), panel.getWidth(), (int) (panel.getHeight() * 0.25));
        noticeLabel.setForeground(Constants.fontColor);
        noticeLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(noticeLabel);

        panel.setVisible(true);
    }
}
