package screen;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputListener;

import stores.*;
import utils.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;

public class CrewScreen {
    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Crew screen");
        panel.setVisible(false);
        panel.removeAll();

        // Add the border for the crew from the 20 top rated movies
        TitledBorder topRatedCrewBorder;
        topRatedCrewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Crew from top rated movies");
        topRatedCrewBorder.setTitleJustification(TitledBorder.CENTER);
        topRatedCrewBorder.setTitleColor(Constants.fontColor);

        JPanel topRatedCrewOuter = new JPanel();
        topRatedCrewOuter.setBounds(5, 200, (int) (panel.getWidth()*0.48)+10, (int) (panel.getHeight()*0.60)+20);
        topRatedCrewOuter.setBorder(topRatedCrewBorder);
        topRatedCrewOuter.setForeground(Constants.fontColor);
        topRatedCrewOuter.setBackground(Constants.highlight);
        topRatedCrewOuter.setLayout(new GridBagLayout());

        JPanel topRatedCrewInner = new JPanel();
        topRatedCrewInner.setBackground(Constants.highlight);
        topRatedCrewInner.setForeground(Constants.fontColor);
        JScrollPane topRatedCrew = new JScrollPane(topRatedCrewInner);
        topRatedCrew.setBounds(10, 205, 510, 400);
        topRatedCrew.setMinimumSize(new Dimension(510, 400));
        topRatedCrew.setPreferredSize(new Dimension(510, 400));
        topRatedCrew.getViewport().setMinimumSize(new Dimension(510, 400));
        topRatedCrew.getViewport().setPreferredSize(new Dimension(510, 400));
        topRatedCrew.setBackground(Constants.highlight);
        topRatedCrew.setForeground(Constants.fontColor);
        topRatedCrewOuter.add(topRatedCrew);

        TopRatedCrewRunnable topRatedCrewRunnable = new TopRatedCrewRunnable(panel, topRatedCrew, topRatedCrewInner, ratings, credits, movies);

        // Add the border for the crew from recent movies
        TitledBorder recentCrewBorder;
        recentCrewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Crew from movies released in the Naughties (2000 to 2010)");
        recentCrewBorder.setTitleJustification(TitledBorder.CENTER);
        recentCrewBorder.setTitleColor(Constants.fontColor);

        JPanel recentCrewOuter = new JPanel();
        recentCrewOuter.setBounds((int) (panel.getWidth()*0.48)+15, 200, (int) (panel.getWidth()*0.48)+10, (int) (panel.getHeight()*0.60)+20);
        recentCrewOuter.setBorder(recentCrewBorder);
        recentCrewOuter.setForeground(Constants.fontColor);
        recentCrewOuter.setBackground(Constants.highlight);
        recentCrewOuter.setLayout(new GridBagLayout());

        JPanel recentCrewInner = new JPanel();
        recentCrewInner.setBackground(Constants.highlight);
        recentCrewInner.setForeground(Constants.fontColor);
        JScrollPane recentCrew = new JScrollPane(recentCrewInner);
        recentCrew.setBounds((int) (panel.getWidth()*0.48)+20, 205, 510, 400);
        recentCrew.setMinimumSize(new Dimension(510, 400));
        recentCrew.setPreferredSize(new Dimension(510, 400));
        recentCrew.getViewport().setMinimumSize(new Dimension(510, 400));
        recentCrew.getViewport().setPreferredSize(new Dimension(510, 400));
        recentCrew.setBackground(Constants.highlight);
        recentCrew.setForeground(Constants.fontColor);
        recentCrewOuter.add(recentCrew);

        RecentMoviesCrewsRunnable recentMoviesCrewsRunnable = new RecentMoviesCrewsRunnable(panel, recentCrew, recentCrewInner, credits, movies);


        // Add the border for Gans movies
        TitledBorder famousCrewBorder;
        famousCrewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Edgar Wright movies");
        famousCrewBorder.setTitleJustification(TitledBorder.CENTER);
        famousCrewBorder.setTitleColor(Constants.fontColor);

        JPanel famousCrewOuter = new JPanel();
        famousCrewOuter.setBounds(5, 5, (int) (panel.getWidth()*0.95)+25, 190);
        famousCrewOuter.setBorder(famousCrewBorder);
        famousCrewOuter.setForeground(Constants.fontColor);
        famousCrewOuter.setBackground(Constants.highlight);
        famousCrewOuter.setLayout(new GridBagLayout());

        JPanel famousCrewInner = new JPanel();
        famousCrewInner.setBackground(Constants.highlight);
        famousCrewInner.setForeground(Constants.fontColor);
        JScrollPane famousCrew = new JScrollPane(famousCrewInner);
        famousCrew.setBounds(10, 10, 1045, 168);
        famousCrew.setMinimumSize(new Dimension(1045, 168));
        famousCrew.setPreferredSize(new Dimension(1045, 168));
        famousCrew.getViewport().setMinimumSize(new Dimension(1045, 168));
        famousCrew.getViewport().setPreferredSize(new Dimension(1045, 168));
        famousCrew.setBackground(Constants.highlight);
        famousCrew.setForeground(Constants.fontColor);
        famousCrewOuter.add(famousCrew);

        WrightMovies wrightMovies = new WrightMovies(famousCrew, famousCrewInner, credits, movies);

        panel.add(topRatedCrewOuter);
        panel.add(recentCrewOuter);
        panel.add(famousCrewOuter);

        panel.setVisible(true);

        SwingUtilities.invokeLater(topRatedCrewRunnable);
        SwingUtilities.invokeLater(recentMoviesCrewsRunnable);
        SwingUtilities.invokeLater(wrightMovies);
    }
}

class TopRatedCrewRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Ratings ratings;
    private Movies movies;
    private JLabel loadingText;
    private int[] topMovies; // store IDs of 20 top movies

    public TopRatedCrewRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Ratings ratings, Credits credits, Movies movies) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.ratings = ratings;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for crew from top rated movies...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        topMovies = ratings.getTopMovies(Constants.topMoviesCount); // ids of top 20 movies
    }

    /***
     * Executed when a button is clicked
     * @param e an action
     * @param currentTitle the label of the button that was clicked. Button labels are movie titles
     */
    private void process(ActionEvent e, String currentTitle) {

        int movieID = convertTitleToId(currentTitle);
        FilmScreen.createPanel(masterPanel, movieID, movies, credits);
    }

    /***
     *
     * @param title The title of the movie that its corresponding button was clicked
     * @return the ID of the movie with the specified title
     */
    private int convertTitleToId(String title) {

        for (int i = 0; i < topMovies.length; i ++) {
            if (title.equals(movies.getTitle(topMovies[i]))) {
                return topMovies[i];
            }
        }
        System.out.println("No ID for the particular movie found!");
        return 0;
    }

    @Override
    public void run() {
        // MyArrayList<Crew[]> topCrew = getCrewFromTopMovies();

        scrollPane.setVisible(false);

        if (topMovies == null || topMovies.length == 0) {
            loadingText.setText("No crew that featured in top movies were found!");
            System.out.println("\tNo crew that featured in top movies were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing " + topMovies.length + " crews that featured in top movies...");
            System.out.println("\t" + topMovies.length + " of the top crews found (max: " + Constants.topMoviesCount + ")");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth() - 40, topMovies.length * (itemHeight + ((new JSeparator()).getHeight())));
    
        for (int i = 0; i < topMovies.length; i ++) {
            JPanel resultItem = new JPanel();
            resultItem.setSize(scrollPane.getWidth() - 40, itemHeight);

            String resultString = "";
            String currentTitle = movies.getTitle(topMovies[i]); // title of the current movie
            Crew[] crew = credits.getCrew(topMovies[i]); // current cast

            int movieID = topMovies[i];
            // create a clickable button
            JPanel titlePanel = new JPanel();
            JLabel titleLabel = new JLabel(currentTitle);
            titlePanel.setBackground(Constants.background);
            titleLabel.setForeground(Constants.fontColor);
            titlePanel.add(titleLabel);

            titlePanel.addMouseListener(new MouseInputListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            FilmScreen.createPanel(masterPanel, movieID, movies, credits);
                        }
                    });
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });

            resultsPanel.add(titlePanel);
            for (int j = 0; j < crew.length; j ++) {
                resultString += crew[j].getName();
                if (j < crew.length-1) { // add '|' only if not last cast member reached
                    resultString += " | ";
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

            if (i < topMovies.length-1) {
                JSeparator sep = new JSeparator();
                sep.setBackground(Constants.highlight);
                sep.setForeground(Constants.fontColor);
                resultsPanel.add(sep);
            }
        }
        scrollPane.setVisible(true);
    }
}

class RecentMoviesCrewsRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Movies movies;
    private JLabel loadingText;
    private Calendar cal = Calendar.getInstance(); // store the date to be compared
    private Calendar dawnOfTime = Calendar.getInstance(); // store the date to be compared
    int[] newMovies; // IDs of movies released in the naughties

    public RecentMoviesCrewsRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Movies movies) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for crew in Movies released in the Naughties (2000 to 2010)...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        cal.set(2011, 1, 1, 0, 0, 0); // date is 1/1/2011 00:00:00
        dawnOfTime.set(2000, 1, 1, 0, 0, 0); // date is EpOCH 0, 01/11/2000 00:00:00

        newMovies = movies.getAllIDsReleasedInRange(dawnOfTime, cal); // IDs of movies released in November 2017
    }

    /***
     * Executed when a button is clicked
     * @param e the action
     * @param currentTitle title of the movie that its corresponding button was clicked
     */
    private void process(ActionEvent e, String currentTitle) {

        int movieID = convertTitleToId(currentTitle);
        FilmScreen.createPanel(masterPanel, movieID, movies, credits);
    }

    /***
     * Get the ID of the movie that its corresponding button was clicked
     * @param title title of the movie that its corresponding button was clicked
     * @return the ID of the movie that its title was passed in as a parameter to the function
     */
    private int convertTitleToId(String title) {

        for (int i = 0; i < newMovies.length; i ++) {
            if (title.equals(movies.getTitle(newMovies[i]))) {
                return newMovies[i];
            }
        }
        System.out.println("No ID for the particular movie found!");
        return 0;
    }

    @Override
    public void run() {
        // MyArrayList<Crew[]> newCrew = getNewCrew();

        scrollPane.setVisible(false);

        if (newMovies == null || newMovies.length == 0) {
            loadingText.setText("No crew that played in movies released in the Naughties (2000 to 2010) were found!");
            System.out.println("\tNo crew that played in movies released in the Naughties (2000 to 2010) were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing crews featured in movies released in Naughties (2000 to 2010).");
            System.out.println("\t" + newMovies.length + " crews were found");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth() - 60, newMovies.length * (itemHeight + ((new JSeparator()).getHeight())));
   
        for (int i = 0; i < newMovies.length; i ++) {
            JPanel resultItem = new JPanel();
            resultItem.setSize(scrollPane.getWidth() - 60, itemHeight);

            String resultString = "";
            String currentTitle = movies.getTitle(newMovies[i]);
            Crew[] crew = credits.getCrew(newMovies[i]); // current cast

            int movieID = newMovies[i];
            // create a clickable button
            JPanel titlePanel = new JPanel();
            JLabel titleLabel = new JLabel(currentTitle);
            titlePanel.setBackground(Constants.background);
            titleLabel.setForeground(Constants.fontColor);
            titlePanel.add(titleLabel);

            titlePanel.addMouseListener(new MouseInputListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            FilmScreen.createPanel(masterPanel, movieID, movies, credits);
                        }
                    });
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });

            resultsPanel.add(titlePanel);

            for (int j = 0; j < crew.length; j ++) {
                resultString += crew[j].getName();
                if (j < crew.length-1) { // add '|' only if not last cast member reached
                    resultString += " | ";
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

            if (i < newMovies.length-1) {
                JSeparator sep = new JSeparator();
                sep.setBackground(Constants.highlight);
                sep.setForeground(Constants.fontColor);
                resultsPanel.add(sep);
            }
        }
        scrollPane.setVisible(true);
    }
}

class WrightMovies implements Runnable {
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Movies movies;
    private JLabel loadingText;

    public WrightMovies(JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Movies movies) {
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for Edgar Wright Movies...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);
    }

    static public Image getPosterImage(String posterEndURL) {
        try{
            Image unknown = ImageIO.read(new File("src/main/resources/img/Movie-Unknown.png"));
            if (posterEndURL == null || posterEndURL.equals("")){
                return unknown;
            }

            String completeURL = "https://image.tmdb.org/t/p/w500" + posterEndURL;
            Image ret;
            try{
                ret = ImageIO.read(new URL(completeURL));
            }
            catch (IOException e){
                ret = unknown;
            }
            if (ret == null){ // File not found at URL
                ret = unknown;
            }

            return ret;

        }
        catch (IOException e){
            return null;

        }
    }

    @Override
    public void run() {
        scrollPane.setVisible(false);

        Crew[] listOfCrewMatched = credits.findCrew("Edgar Wright");

        if (listOfCrewMatched == null || listOfCrewMatched.length == 0) {
            loadingText.setText("No crew member with \"Edgar Wright\" in the name was found!");
            System.out.println("\tNo crew member with \"Edgar Wright\" in the name was found!");
            scrollPane.setVisible(true);
            return;
        }

        int[] directorMovies = credits.getFilmIDsFromCrewID(listOfCrewMatched[0].getID());

        if (directorMovies == null || directorMovies.length == 0) {
            loadingText.setText("No Edgar Wright movies found!");
            System.out.println("\tNo Edgar Wright movies found");
            scrollPane.setVisible(true);
            return;
        } else {
            loadingText.setText("Processing " + directorMovies.length + " Edgar Wright movies...");
            System.out.println("\t" + directorMovies.length + " movies featuring Edgar Wright were found!");
        }

        final int itemWidth = 225;
        final int itemHeight = scrollPane.getHeight() - 20;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new GridLayout(1, BoxLayout.X_AXIS, 10, 0));
        resultsPanel.setSize(itemWidth * directorMovies.length, itemHeight);

        for (int i = 0; i < directorMovies.length; i++) {
            String filmNameString = movies.getTitle(directorMovies[i]);
            if (filmNameString == null) {
                filmNameString = "Unknown Film";
            }
            JLabel textLabel = new JLabel(filmNameString);

            // REFERENCE: https://stackoverflow.com/questions/21587309/positioning-jlabel-in-jpanel-below-the-image
            Image poster = getPosterImage(movies.getPoster(directorMovies[i]));
            if (poster == null) {
                System.err.println("poster is null");
            }
            textLabel.setIcon(new ImageIcon(poster.getScaledInstance(itemWidth, itemHeight - 20, Image.SCALE_DEFAULT)));
            textLabel.setHorizontalTextPosition(JLabel.CENTER);
            textLabel.setVerticalTextPosition(JLabel.BOTTOM);
            textLabel.setSize(itemWidth, itemHeight);

            resultsPanel.add(textLabel);
        }
        scrollPane.setVisible(true);
    }
}
