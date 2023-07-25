package screen;

import java.awt.*;
import java.awt.event.*;

import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;

import stores.*;
import utils.*;

public class MainScreen {

    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Main page");
        panel.setVisible(false);
        panel.removeAll();

        // Add search panel
        TitledBorder searchPanelBorder;
        searchPanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Search box");
        searchPanelBorder.setTitleJustification(TitledBorder.RIGHT);
        searchPanelBorder.setTitleColor(Constants.fontColor);

        JPanel searchPanelOuter = new JPanel();
        searchPanelOuter.setBounds(5,5, (int) (panel.getWidth()*0.98), (int) (panel.getHeight()*0.12));
        searchPanelOuter.setBorder(searchPanelBorder);
        searchPanelOuter.setForeground(Constants.fontColor);
        searchPanelOuter.setBackground(Constants.highlight);
        searchPanelOuter.setLayout(new GridBagLayout());

        // Add movie panel
        TitledBorder moviePanelBorder;
        moviePanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Movies");
        moviePanelBorder.setTitleJustification(TitledBorder.LEFT);
        moviePanelBorder.setTitleColor(Constants.fontColor);

        JPanel moviePanelOuter = new JPanel();
        moviePanelOuter.setBounds(5, (int) (panel.getHeight()*0.135), (int) (panel.getWidth()*0.75), (int) (panel.getHeight()*0.43));
        moviePanelOuter.setBorder(moviePanelBorder);
        moviePanelOuter.setForeground(Constants.fontColor);
        moviePanelOuter.setBackground(Constants.highlight);
        moviePanelOuter.setLayout(new GridBagLayout());

        // Add movie panel inner
        JPanel moviePanelInner = new JPanel();
        moviePanelInner.setBackground(Constants.highlight);
        moviePanelInner.setForeground(Constants.fontColor);
        JScrollPane moviePanel = new JScrollPane(moviePanelInner);
        moviePanel.setBounds(10, (int) (panel.getHeight()*0.15), (int) (panel.getWidth()*0.75)-10, 260);
        moviePanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.75)-10, 260));
        moviePanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.75)-10, 260));
        moviePanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.75)-10, 260));
        moviePanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.75)-10, 260));
        moviePanel.setBackground(Constants.highlight);
        moviePanel.setForeground(Constants.fontColor);
        moviePanelOuter.add(moviePanel);

        // Add ratings panel
        TitledBorder ratingPanelBorder;
        ratingPanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Movie ratings");
        ratingPanelBorder.setTitleJustification(TitledBorder.CENTER);
        ratingPanelBorder.setTitleColor(Constants.fontColor);

        JPanel ratingPanelOuter = new JPanel();
        ratingPanelOuter.setBounds((int) (panel.getWidth()*0.75), (int) (panel.getHeight()*0.135), (int) (panel.getWidth()*0.23)+5, (int) (panel.getHeight()*0.43));
        ratingPanelOuter.setBorder(ratingPanelBorder);
        ratingPanelOuter.setForeground(Constants.fontColor);
        ratingPanelOuter.setBackground(Constants.highlight);
        ratingPanelOuter.setLayout(new GridBagLayout());

        // Add ratings panel inner
        JPanel ratingPanelInner = new JPanel();
        ratingPanelInner.setBackground(Constants.highlight);
        ratingPanelInner.setForeground(Constants.fontColor);
        JScrollPane ratingPanel = new JScrollPane(ratingPanelInner);
        ratingPanel.setBounds((int) (panel.getWidth()*0.75)+5, (int) (panel.getHeight()*0.15), (int) (panel.getWidth()*0.23)-5, 260);
        ratingPanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.23)-5, 260));
        ratingPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.23)-5, 260));
        ratingPanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.23)-5, 260));
        ratingPanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.23)-5, 260));
        ratingPanel.setBackground(Constants.highlight);
        ratingPanel.setForeground(Constants.fontColor);
        ratingPanelOuter.add(ratingPanel);

        // Add cast panel
        TitledBorder castPanelBorder;
        castPanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Star Cast");
        castPanelBorder.setTitleJustification(TitledBorder.CENTER);
        castPanelBorder.setTitleColor(Constants.fontColor);

        JPanel castPanelOuter = new JPanel();
        castPanelOuter.setBounds(5, (int) (panel.getHeight()*0.57), (int) (panel.getWidth()*0.35), (int) (panel.getHeight()*0.37));
        castPanelOuter.setBorder(castPanelBorder);
        castPanelOuter.setForeground(Constants.fontColor);
        castPanelOuter.setBackground(Constants.highlight);
        castPanelOuter.setLayout(new GridBagLayout());

        // Add cast panel inner
        JPanel castPanelInner = new JPanel();
        castPanelInner.setBackground(Constants.highlight);
        castPanelInner.setForeground(Constants.fontColor);
        JScrollPane castPanel = new JScrollPane(castPanelInner);
        castPanel.setBounds(10, (int) (panel.getHeight()*0.57)+17, (int) (panel.getWidth()*0.35)-10, 228);
        castPanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.35)-10, 228));
        castPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.35)-10, 228));
        castPanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.35)-10, 228));
        castPanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.35)-10, 228));
        castPanel.setBackground(Constants.highlight);
        castPanel.setForeground(Constants.fontColor);
        castPanelOuter.add(castPanel);

        // Add superstar panel
        TitledBorder superstarPanelBorder;
        superstarPanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Super-star Cast");
        superstarPanelBorder.setTitleJustification(TitledBorder.CENTER);
        superstarPanelBorder.setTitleColor(Constants.fontColor);

        JPanel superstarPanelOuter = new JPanel();
        superstarPanelOuter.setBounds((int) (panel.getWidth()*0.37), (int) (panel.getHeight()*0.57), (int) (panel.getWidth()*0.61), (int) (panel.getHeight()*0.37));
        superstarPanelOuter.setBorder(superstarPanelBorder);
        superstarPanelOuter.setForeground(Constants.fontColor);
        superstarPanelOuter.setBackground(Constants.highlight);
        superstarPanelOuter.setLayout(new GridBagLayout());

        // Add superstar panel inner
        JPanel superstarPanelInner = new JPanel();
        superstarPanelInner.setBackground(Constants.highlight);
        superstarPanelInner.setForeground(Constants.fontColor);
        JScrollPane superstarPanel = new JScrollPane(superstarPanelInner);
        superstarPanel.setBounds((int) (panel.getWidth()*0.37)+5, (int) (panel.getHeight()*0.57)+17, (int) (panel.getWidth()*0.61)-10, 228);
        superstarPanel.setMinimumSize(new Dimension((int) (panel.getWidth()*0.61)-10, 228));
        superstarPanel.setPreferredSize(new Dimension((int) (panel.getWidth()*0.61)-10, 228));
        superstarPanel.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.61)-10, 228));
        superstarPanel.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.61)-10, 228));
        superstarPanel.setBackground(Constants.highlight);
        superstarPanel.setForeground(Constants.fontColor);
        superstarPanelOuter.add(superstarPanel);

        int firstYear = 1900;
        String[] years = new String[123];
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        for (int i = 0; i < years.length; i ++) {
            years[i] = String.valueOf(firstYear);
            firstYear ++;
        }

        JComboBox<String> yearFrom =new JComboBox<>(years);   
        yearFrom.setBounds(50, 20,90,20);   

        JComboBox<String> monthFrom =new JComboBox<>(month);
        monthFrom.setBounds(160, 20, 90, 20);

        JLabel text = new JLabel();
        text.setText("to");
        text.setBounds(270, 15, 40, 20);

        JComboBox<String> yearTo =new JComboBox<>(years);   
        yearTo.setBounds(310, 20,90,20);   

        JComboBox<String> monthTo =new JComboBox<>(month);
        monthTo.setBounds(420, 20, 90, 20);

        JTextField budgetFrom,budgetTo;

        budgetFrom = new JTextField("000000");
        budgetTo = new JTextField("000000");

        budgetFrom.setBounds(160, 50, 90, 20);
        budgetTo.setBounds(310, 50, 90, 20);

        JLabel textBelow = new JLabel();
        textBelow.setText("to");
        textBelow.setBounds(270, 50, 40, 20);

        JCheckBox budget = new JCheckBox();
        budget.setBounds(20, 50, 25, 25);
        budget.setBackground(Constants.highlight);

        JButton b =new JButton("Submit");  
        b.setBounds(540, 25, 100, 30);

        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                int startYear = Integer.valueOf((String) (yearFrom.getSelectedItem()));
                int endYear = Integer.valueOf((String) (yearTo.getSelectedItem()));

                int startMonth = Integer.valueOf((String) (monthFrom.getSelectedItem()));
                int endMonth = Integer.valueOf((String) (monthTo.getSelectedItem()));

                startMonth --;
                endMonth --;

                start.set(startYear, startMonth, 1, 0, 0, 0);
                end.set(endYear, endMonth, 1, 0, 0, 0);

                long lowBudget = Long.valueOf(budgetFrom.getText());
                long highBudget = Long.valueOf(budgetTo.getText());

                Boolean ticked = budget.isSelected();

                int[] myMovies = produceResults(panel, moviePanel, moviePanelInner, movies, start, end, lowBudget, highBudget, ticked);
            
                produceRatings(panel, ratingPanel, ratingPanelInner, movies, start, end, ratings, myMovies);
            
                
            }
        });

        CastRunnable castRunnable = new CastRunnable(panel, castPanel, castPanelInner, credits, ratings);
        SupercastRunnable supercastRunnable = new SupercastRunnable(panel, superstarPanel, superstarPanelInner, credits, ratings);

        panel.add(yearFrom);
        panel.add(monthFrom);
        panel.add(text);
        panel.add(yearTo);
        panel.add(monthTo);
        panel.add(budgetFrom);
        panel.add(budgetTo);
        panel.add(textBelow);
        panel.add(budget);
        panel.add(b);
        panel.add(searchPanelOuter);
        panel.add(moviePanelOuter);
        panel.add(ratingPanelOuter);
        panel.add(castPanelOuter);
        panel.add(superstarPanelOuter);

        panel.setVisible(true);

        SwingUtilities.invokeLater(castRunnable);
        SwingUtilities.invokeLater(supercastRunnable);
    } 

    private static int[] produceResults(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Movies movies, Calendar start, Calendar end, long lowBudget, long highBudget, Boolean ticked) {
        int[] myMovies;
        JLabel loadingText;
        
        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for movies...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        if (ticked) {
            myMovies =  movies.getAllIDsReleasedInRangeAndBudget(start, end, lowBudget, highBudget);
        }
        else {
            myMovies =  movies.getAllIDsReleasedInRange(start, end);
        }

        scrollPane.setVisible(false);
        if (myMovies == null || myMovies.length == 0) {
            loadingText.setText("No movies were found!");
            System.out.println("\tNo movies were found");
            scrollPane.setVisible(true);
            return myMovies;
        }
        else {
            loadingText.setText("Processing " + myMovies.length + " movies...");
            System.out.println("\t" + myMovies.length + " found!");
        }

        int currentWidth = 0;
        final int gapSize = 5;
        int currentHeight = gapSize;

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < myMovies.length; i ++) {
            String movieID = movies.getTitle(myMovies[i]);

            JLabel textLabel = new JLabel(movieID);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(movieID)+10;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);

            //Decides if the movie should be on the current line, or a new line should be started
            if (currentWidth + (4*gapSize) + stringWidth > resultsPanel.getWidth()) {
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
        return myMovies;
    }

    private static void produceRatings(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Movies movies, Calendar start, Calendar end, Ratings ratings, int[] myMovies) {
        JLabel loadingText;
        
        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for movie ratings...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        scrollPane.setVisible(false);
        if (myMovies == null || myMovies.length == 0) {
            loadingText.setText("No ratings were found!");
            System.out.println("\tNo ratings were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing movie ratings!");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth()-60, myMovies.length * (itemHeight + ((new JSeparator()).getHeight())));
    
        for (int i = 0; i < myMovies.length; i ++) {
            JPanel resultItem = new JPanel();
            resultItem.setSize(scrollPane.getWidth()-60, itemHeight);

            String resultString = "";
            float[] currentRatings = ratings.getMovieRatingsBetween(myMovies[i], start, end);
            if (currentRatings.length == 0) {
                resultString += "No ratings for the movie '" + movies.getTitle(myMovies[i]) + "' were found!";
            }
            else {
                resultString += "Movie '" + movies.getTitle(myMovies[i]) + "'\n";
                for (int j = 0; j < currentRatings.length; j ++) {
                    resultString += String.valueOf(currentRatings[j]);
                    if (j < currentRatings.length-1) {
                        resultString += " | ";
                    }
                }
            }

            JTextArea title = new JTextArea(resultString);
            title.setBounds(0, (itemHeight * i), resultsPanel.getWidth(), itemHeight);
            title.setForeground(Constants.fontColor);
            title.setBackground(Constants.highlight);
            title.setEditable(false);
            title.setLineWrap(true);
            title.setWrapStyleWord(true);

            resultItem.add(title);
            resultItem.setBackground(Constants.highlight);
            resultItem.setForeground(Constants.fontColor);

            resultsPanel.add(resultItem);

            if (i < myMovies.length-1) {
                JSeparator sep = new JSeparator();
                sep.setBackground(Constants.highlight);
                sep.setForeground(Constants.fontColor);
                resultsPanel.add(sep);
            }
        }
        scrollPane.setVisible(true);
    } 
}

class CastRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Ratings ratings;
    private JLabel loadingText;
    int[] starCast;

    public CastRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Ratings ratings) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.ratings = ratings;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for star cast...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);        
    }

    @Override
    public void run() {
        starCast = credits.findStarCastID(ratings);
        scrollPane.setVisible(false);

        if (starCast == null || starCast.length == 0) {
            loadingText.setText("No star cast was found!");
            System.out.println("\tNo star cast was found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing star cast....");
            System.out.println("\t" + starCast.length + " star casts were found");
        }

        int currentWidth = 0;
        final int gapSize = 5;
        int currentHeight = gapSize;
        
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        String currentActor = "";
        for (int i = 0; i < starCast.length; i ++) {
            currentActor = credits.getCastName(starCast[i]);

            JLabel textLabel = new JLabel(currentActor);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(currentActor)+10;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);

            //Decides if the movie should be on the current line, or a new line should be started
            if (currentWidth + (2*gapSize) + stringWidth > resultsPanel.getWidth()) {
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

class SupercastRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Ratings ratings;
    private JLabel loadingText;
    int[] superstarCast;

    public SupercastRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Ratings ratings) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.ratings = ratings;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for super-star cast...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);        
    }

    @Override
    public void run() {
        superstarCast = credits.findSuperStarCastID(ratings);
        scrollPane.setVisible(false);

        if (superstarCast == null || superstarCast.length == 0) {
            loadingText.setText("No super-star cast was found!");
            System.out.println("\tNo super-star cast was found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing super-star cast....");
            System.out.println("\t" + superstarCast.length + " super-star casts were found");
        }

        int currentWidth = 0;
        final int gapSize = 5;
        int currentHeight = gapSize;
        
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        String currentActor = "";
        for (int i = 0; i < superstarCast.length; i ++) {
            currentActor = credits.getCastName(superstarCast[i]);
            
            //Create film label
            JLabel textLabel = new JLabel(currentActor);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(currentActor)+10;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);

            //Decides if the movie should be on the current line, or a new line should be started
            if (currentWidth + (2*gapSize) + stringWidth > resultsPanel.getWidth()) {
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

