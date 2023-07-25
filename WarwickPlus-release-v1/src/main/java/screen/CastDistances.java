package screen;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputListener;

import stores.*;
import utils.*;

public class CastDistances {
    public static int firstCast = -1;
    public static int secondCast = -2;
    public static JButton b = new JButton("Search");

    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Cast Distances");
        panel.setVisible(false);
        panel.removeAll();

        // Add cast A panel
        TitledBorder castABorder;
        castABorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast A");
        castABorder.setTitleJustification(TitledBorder.LEFT);
        castABorder.setTitleColor(Constants.fontColor);

        JPanel castAOuter = new JPanel();
        castAOuter.setBounds(5,5, (int) (panel.getWidth()*0.38), (int) (panel.getHeight()*0.80));
        castAOuter.setBorder(castABorder);
        castAOuter.setForeground(Constants.fontColor);
        castAOuter.setBackground(Constants.highlight);
        castAOuter.setLayout(new GridBagLayout());

        // Add cast A panel inner
        JPanel castAInner = new JPanel();
        castAInner.setBackground(Constants.highlight);
        castAInner.setForeground(Constants.fontColor);
        JScrollPane castAPanel = new JScrollPane(castAInner);
        castAPanel.setBounds(10, 10, (int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20);
        castAPanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castAPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castAPanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castAPanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castAPanel.setBackground(Constants.highlight);
        castAPanel.setForeground(Constants.fontColor);
        castAOuter.add(castAPanel);

        // Add cast B panel
        TitledBorder castBBorder;
        castBBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast B");
        castBBorder.setTitleJustification(TitledBorder.LEFT);
        castBBorder.setTitleColor(Constants.fontColor);

        JPanel castBOuter = new JPanel();
        castBOuter.setBounds((int) (panel.getWidth()*0.38) + 70,5, (int) (panel.getWidth()*0.38), (int) (panel.getHeight()*0.80));
        castBOuter.setBorder(castBBorder);
        castBOuter.setForeground(Constants.fontColor);
        castBOuter.setBackground(Constants.highlight);
        castBOuter.setLayout(new GridBagLayout());

        // Add cast B panel inner
        JPanel castBInner = new JPanel();
        castBInner.setBackground(Constants.highlight);
        castBInner.setForeground(Constants.fontColor);
        JScrollPane castBPanel = new JScrollPane(castBInner);
        castBPanel.setBounds((int) (panel.getWidth()*0.38) + 75, 10, (int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20);
        castBPanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castBPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castBPanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castBPanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.38)-10, (int) (panel.getHeight()*0.80)-20));
        castBPanel.setBackground(Constants.highlight);
        castBPanel.setForeground(Constants.fontColor);
        castBOuter.add(castBPanel);

        // Add selection A panel
        TitledBorder selectionABorder;
        selectionABorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor));

        JPanel selectionA = new JPanel();
        selectionA.setBounds(7,(int) (panel.getHeight()*0.81), (int) (panel.getWidth()*0.38)-3, 50);
        selectionA.setBorder(selectionABorder);
        selectionA.setForeground(Constants.fontColor);
        selectionA.setBackground(Constants.highlight);
        selectionA.setLayout(new GridBagLayout());

        // Add selection B panel
        TitledBorder selectionBBorder;
        selectionBBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor));

        JPanel selectionB = new JPanel();
        selectionB.setBounds((int) (panel.getWidth()*0.38)+72,(int) (panel.getHeight()*0.81), (int) (panel.getWidth()*0.38)-3, 50);
        selectionB.setBorder(selectionBBorder);
        selectionB.setForeground(Constants.fontColor);
        selectionB.setBackground(Constants.highlight);
        selectionB.setLayout(new GridBagLayout());

        JLabel castA = new JLabel("No cast selected");
        castA.setForeground(Constants.fontColor);
        JLabel castB = new JLabel("No cast selected");
        castB.setForeground(Constants.fontColor);
        selectionA.add(castA);
        selectionB.add(castB);

        // Add search panel
        TitledBorder searchBorder;
        searchBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor));

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds((int) (panel.getWidth()*0.80)+35,14 , 150, 50);
        searchPanel.setBorder(searchBorder);
        searchPanel.setForeground(Constants.fontColor);
        searchPanel.setBackground(Constants.highlight);
        searchPanel.setLayout(new GridBagLayout());

        
        b.setBounds(50,100,95,30);
        searchPanel.add(b);

        
        // Add cast distance panel
        TitledBorder castDistanceBorder;
        castDistanceBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast Distances");
        castDistanceBorder.setTitleJustification(TitledBorder.LEFT);
        castDistanceBorder.setTitleColor(Constants.fontColor);

        JPanel castDistanceOuter = new JPanel();
        castDistanceOuter.setBounds((int) (panel.getWidth()*0.80)+35,160, 150, (int) (panel.getHeight()*0.65));
        castDistanceOuter.setBorder(castDistanceBorder);
        castDistanceOuter.setForeground(Constants.fontColor);
        castDistanceOuter.setBackground(Constants.highlight);
        castDistanceOuter.setLayout(new GridBagLayout());

        // Add cast distance inner
        JPanel castDistanceInner = new JPanel();
        castDistanceInner.setBackground(Constants.highlight);
        castDistanceInner.setForeground(Constants.fontColor);
        JScrollPane castDistancePanel = new JScrollPane(castDistanceInner);
        castDistancePanel.setBounds((int) (panel.getWidth()*0.80)+45, 170, 135, (int) (panel.getHeight()*0.65)-20);
        castDistancePanel.setMinimumSize(new Dimension(135, (int) (panel.getHeight()*0.65)-20));
        castDistancePanel.setPreferredSize(new Dimension(135, (int) (panel.getHeight()*0.65)-20));
        castDistancePanel.getViewport().setMinimumSize(new Dimension(135, (int) (panel.getHeight()*0.65)-20));
        castDistancePanel.getViewport().setPreferredSize(new Dimension(135, (int) (panel.getHeight()*0.65)-20));
        castDistancePanel.setBackground(Constants.highlight);
        castDistancePanel.setForeground(Constants.fontColor);
        castDistanceOuter.add(castDistancePanel);

        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                b.setText("Searching...");

                calculateDistance(panel, castDistancePanel, castDistanceInner, credits, firstCast, secondCast);
            }
        });

        FindCastRunnable findCastARunnable = new FindCastRunnable(panel, castAPanel, castAInner, credits, castA, true);
        FindCastRunnable findCastBRunnable = new FindCastRunnable(panel, castBPanel, castBInner, credits, castB, false);

        panel.add(castAOuter);
        panel.add(castBOuter);
        panel.add(selectionB);
        panel.add(selectionA);
        panel.add(searchPanel);
        panel.add(castDistanceOuter);

        panel.setVisible(true);

        SwingUtilities.invokeLater(findCastARunnable);
        SwingUtilities.invokeLater(findCastBRunnable);
    }

    private static void calculateDistance(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, int firstCast, int secondCast) {
        scrollPane.setVisible(false);
        resultsPanel.removeAll();

        JLabel loadingText = new JLabel("Calculating distance...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.TOP);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        int[] distances = credits.findDistance(firstCast, secondCast);
        scrollPane.setVisible(false);

        if (distances == null || distances.length == 0) {
            loadingText.setText("Distance is 0!");
            System.out.println("\tDistance is 0!");
            scrollPane.setVisible(true);
            b.setText("Search");
            return;
        }
        else {
            loadingText.setText("Displaying distance...");
            System.out.println("\tDistance is: " + distances.length);
        }

        final int gapSize = 5;
        int currentHeight = gapSize;

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        String currentActor = "";
        for (int i = 0; i < distances.length; i ++) {
            currentActor = credits.getCastName(distances[i]);

            JLabel textLabel = new JLabel(currentActor);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(currentActor)+2;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);
            
            tmpPanel.setBounds(gapSize, currentHeight, stringWidth, 10);
            resultsPanel.setSize(resultsPanel.getWidth(), currentHeight + gapSize);
            resultsPanel.add(horizontalPanel);
            horizontalPanel = new JPanel();
            horizontalPanel.setBackground(Constants.highlight);
            horizontalPanel.add(tmpPanel);

            resultsPanel.add(horizontalPanel);

        }
        scrollPane.setVisible(true);
        b.setText("Search");
    }
}

class FindCastRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private JLabel loadingText;
    private JLabel cast;
    private Boolean castA;
    int[] castIDs;

    public FindCastRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, JLabel cast, Boolean castA) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.cast = cast;
        this.castA = castA;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for cast...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);        
    }

    @Override
    public void run() {
        castIDs = credits.getUniqueCastIDs();
        scrollPane.setVisible(false);

        if (castIDs == null || castIDs.length == 0) {
            loadingText.setText("No cast was found!");
            System.out.println("\tNo cast was found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing cast....");
            System.out.println("\t" + castIDs.length + " casts were found");
        }

        int currentWidth = 0;
        final int gapSize = 5;
        int currentHeight = gapSize;

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        String currentActor = "";
        for (int i = 0; i < castIDs.length; i ++) {
            currentActor = credits.getCastName(castIDs[i]);

            final String myActor = currentActor;
            final int index = i;

            JLabel textLabel = new JLabel(currentActor);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(currentActor)+10;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);
            tmpPanel.addMouseListener(new MouseInputListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            cast.setText(myActor);
                            if (castA) {
                                CastDistances.firstCast = castIDs[index];
                            }
                            else {
                                CastDistances.secondCast = castIDs[index];
                            }
                        }
                    });
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    tmpPanel.setBackground(new Color(85, 45, 98));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    tmpPanel.setBackground(Constants.background);
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}

                @Override
                public void mouseDragged(MouseEvent e) {}

                @Override
                public void mouseMoved(MouseEvent e) {}
            });

            //Decides if the movie should be on the current line, or a new line should be started
            if (currentWidth + (2*gapSize) + stringWidth > resultsPanel.getWidth()-50) {
                currentHeight += 30 + gapSize;
                tmpPanel.setBounds(gapSize, currentHeight, stringWidth, 30);
                currentWidth = gapSize + stringWidth;
                resultsPanel.setSize(resultsPanel.getWidth(), currentHeight + gapSize + 30);
                resultsPanel.add(horizontalPanel);
                horizontalPanel = new JPanel();
                horizontalPanel.setBackground(Constants.highlight);
                horizontalPanel.add(tmpPanel);
            } 
            else {
                tmpPanel.setBounds(currentWidth + gapSize, currentHeight, stringWidth, 30);
                currentWidth += gapSize+ stringWidth;
                horizontalPanel.add(tmpPanel);
            }

            resultsPanel.add(horizontalPanel);
        }
        scrollPane.setVisible(true);
    }
}
