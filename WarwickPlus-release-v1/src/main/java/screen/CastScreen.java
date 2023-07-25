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

public class CastScreen {
    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Cast screen");
        panel.setVisible(false);
        panel.removeAll();

        // Add the border for the cast from the 100 top rated movies
        TitledBorder topRatedCastBorder;
        topRatedCastBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast from top rated movies");
        topRatedCastBorder.setTitleJustification(TitledBorder.CENTER);
        topRatedCastBorder.setTitleColor(Constants.fontColor);

        JPanel topRatedCastOuter = new JPanel();
        topRatedCastOuter.setBounds(5, 5, (int) (panel.getWidth()*0.48)+10, (int) (panel.getHeight()*0.60)+20);
        topRatedCastOuter.setBorder(topRatedCastBorder);
        topRatedCastOuter.setForeground(Constants.fontColor);
        topRatedCastOuter.setBackground(Constants.highlight);
        topRatedCastOuter.setLayout(new GridBagLayout());

        JPanel topRatedCastInner = new JPanel();
        topRatedCastInner.setBackground(Constants.highlight);
        topRatedCastInner.setForeground(Constants.fontColor);
        topRatedCastInner.setSize(new Dimension((int) 470, 400));
        JScrollPane topRatedCast = new JScrollPane(topRatedCastInner);
        topRatedCast.setBounds(10, 10, 510, 400);
        topRatedCast.setMinimumSize(new Dimension(510, 400));
        topRatedCast.setPreferredSize(new Dimension(510, 400));
        topRatedCast.getViewport().setMinimumSize(new Dimension(510, 400));
        topRatedCast.getViewport().setPreferredSize(new Dimension(510, 400));
        topRatedCast.setBackground(Constants.highlight);
        topRatedCast.setForeground(Constants.fontColor);
        topRatedCastOuter.add(topRatedCast);

        TopRatedCastRunnable topRatedCastRunnable = new TopRatedCastRunnable(panel, topRatedCast, topRatedCastInner, ratings, credits, movies);

        // Add the border for the cast from movies released in August 1999
        TitledBorder oldMoviesCastBorder;
        oldMoviesCastBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast from movies in the 90's");
        oldMoviesCastBorder.setTitleJustification(TitledBorder.CENTER);
        oldMoviesCastBorder.setTitleColor(Constants.fontColor);

        JPanel oldMoviesCastOuter = new JPanel();
        oldMoviesCastOuter.setBounds((int) (panel.getWidth()*0.48)+15, 5, (int) (panel.getWidth()*0.48)+10, (int) (panel.getHeight()*0.60)+20);
        oldMoviesCastOuter.setBorder(oldMoviesCastBorder);
        oldMoviesCastOuter.setForeground(Constants.fontColor);
        oldMoviesCastOuter.setBackground(Constants.highlight);
        oldMoviesCastOuter.setLayout(new GridBagLayout());

        JPanel oldMoviesCastInner = new JPanel();
        oldMoviesCastInner.setBackground(Constants.highlight);
        oldMoviesCastInner.setForeground(Constants.fontColor);
        JScrollPane oldMoviesCast = new JScrollPane(oldMoviesCastInner);
        oldMoviesCast.setBounds((int) (panel.getWidth()*0.48)+20, 10, 510, 400);
        oldMoviesCast.setMinimumSize(new Dimension(510, 400));
        oldMoviesCast.setPreferredSize(new Dimension(510, 400));
        oldMoviesCast.getViewport().setMinimumSize(new Dimension(510, 400));
        oldMoviesCast.getViewport().setPreferredSize(new Dimension(510, 400));
        oldMoviesCast.setBackground(Constants.highlight);
        oldMoviesCast.setForeground(Constants.fontColor);
        oldMoviesCastOuter.add(oldMoviesCast);

        OldMoviesCastRunnable oldMoviesCastRunnable = new OldMoviesCastRunnable(panel, oldMoviesCast, oldMoviesCastInner, credits, movies);

        // Add the border for the movies where Tom Hanks featured
        TitledBorder actorMoviesBorder;
        actorMoviesBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Tom Hanks Movies");
        actorMoviesBorder.setTitleJustification(TitledBorder.LEFT);
        actorMoviesBorder.setTitleColor(Constants.fontColor);

        JPanel actorMoviesOuter = new JPanel();
        actorMoviesOuter.setBounds(5, 425, 1065, 208);
        actorMoviesOuter.setBorder(actorMoviesBorder);
        actorMoviesOuter.setForeground(Constants.fontColor);
        actorMoviesOuter.setBackground(Constants.highlight);
        actorMoviesOuter.setLayout(new GridBagLayout());

        JPanel actorMoviesInner = new JPanel();
        actorMoviesInner.setBackground(Constants.highlight);
        actorMoviesInner.setForeground(Constants.fontColor);
        JScrollPane actorMovies = new JScrollPane(actorMoviesInner);
        actorMovies.setBounds(10, 435, 1050, 187);
        actorMovies.setMinimumSize(new Dimension(1050, 187));
        actorMovies.setPreferredSize(new Dimension(1050, 187));
        actorMovies.getViewport().setMinimumSize(new Dimension(1050, 187));
        actorMovies.getViewport().setPreferredSize(new Dimension(1050, 187));
        actorMovies.setBackground(Constants.highlight);
        actorMovies.setForeground(Constants.fontColor);
        actorMoviesOuter.add(actorMovies);

        LeonardoDiCaprioMovies leonardoDiCaprioMovies = new LeonardoDiCaprioMovies(actorMovies, actorMoviesInner, credits, movies);

        panel.add(topRatedCastOuter);
        panel.add(oldMoviesCastOuter);
        panel.add(actorMoviesOuter);

        panel.setVisible(true);

        SwingUtilities.invokeLater(topRatedCastRunnable);
        SwingUtilities.invokeLater(oldMoviesCastRunnable);
        SwingUtilities.invokeLater(leonardoDiCaprioMovies);
    }
}

class TopRatedCastRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Ratings ratings;
    private Credits credits;
    private Movies movies;
    private JLabel loadingText;
    private int[] topMovies; // store IDs of 20 top movies

    public TopRatedCastRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Ratings ratings, Credits credits, Movies movies) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.ratings = ratings;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for cast from top rated movies...");
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

        // MyArrayList<Cast[]> topCast = getCastFromTopMovies();

        scrollPane.setVisible(false);

        if (topMovies == null || topMovies.length == 0) {
            loadingText.setText("No casts that featured in top movies were found!");
            System.out.println("\tNo casts that featured in top movies were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing " + topMovies.length + " casts that featured in top movies...");
            System.out.println("\t" + topMovies.length + " of the top cast found (max: " + Constants.topMoviesCount + ")");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth(), topMovies.length * (itemHeight + ((new JSeparator()).getHeight())));

        for (int i = 0; i < topMovies.length; i ++) {
            JPanel resultItem = new JPanel();
            resultItem.setBackground(Constants.background);
            resultItem.setSize(scrollPane.getWidth(), itemHeight);

            String resultString = "";
            int movieID = topMovies[i];
            String currentTitle = movies.getTitle(topMovies[i]); // title of the current movie
            Cast[] cast = credits.getCast(topMovies[i]); // current cast

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
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}

                @Override
                public void mouseDragged(MouseEvent e) {}

                @Override
                public void mouseMoved(MouseEvent e) {}
            });

            resultsPanel.add(titlePanel);

            for (int j = 0; j < cast.length; j ++) {
                resultString += cast[j].getName();
                if (j < cast.length-1) { // add '|' only if not last cast member reached
                    resultString += " | ";
                }
            }
            JTextArea title = new JTextArea(resultString);
            title.setBounds(0, (itemHeight * i), resultsPanel.getWidth()-40, itemHeight);
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

class OldMoviesCastRunnable implements Runnable {

    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Movies movies;
    private JLabel loadingText;
    private Calendar cal = Calendar.getInstance(); // store the date to be compared
    private Calendar dawnOfTime = Calendar.getInstance(); // store the date to be compared
    int[] oldMovies; // IDs of movies released before a predefined date

    public OldMoviesCastRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Movies movies) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for cast in Movies released in the 90's...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);

        cal.set(2000, 1, 1, 0, 0, 0); // date is 01/01/2000 00:00:00
        dawnOfTime.set(1900, 1, 1, 0, 0, 0); // date is EpOCH 0, 01/01/1990 00:00:00

        oldMovies = movies.getAllIDsReleasedInRange(dawnOfTime, cal); // IDs of movies released before a pre-defined date
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

        for (int i = 0; i < oldMovies.length; i ++) {
            if (title.equals(movies.getTitle(oldMovies[i]))) {
                return oldMovies[i];
            }
        }
        System.out.println("No ID for the particular movie found!");
        return 0;
    }

    @Override
    public void run() {
        // MyArrayList<Cast[]> oldCast = getOldCast();

        scrollPane.setVisible(false);

        if (oldMovies == null || oldMovies.length == 0) {
            loadingText.setText("No casts that played in movies released in the 90's were found!");
            System.out.println("\tNo casts that played in movies released in the 90's were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing casts featured in movies released in the 90's.");
            System.out.println("\t" + oldMovies.length + " casts were found");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth() - 40, oldMovies.length * (itemHeight + ((new JSeparator()).getHeight())));

        for (int i = 0; i < oldMovies.length; i ++) {
            JPanel resultItem = new JPanel();
            resultItem.setSize(scrollPane.getWidth() - 40, itemHeight);

            String resultString = "";
            int movieID = oldMovies[i];
            String currentTitle = movies.getTitle(oldMovies[i]);
            Cast[] cast = credits.getCast(oldMovies[i]); // current cast

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

            for (int j = 0; j < cast.length; j ++) {
                resultString += cast[j].getName();
                if (j < cast.length-1) { // add '|' only if not last cast member reached
                    resultString += " | ";
                }
            }
            JTextArea title = new JTextArea(resultString);
            title.setBounds(0, (itemHeight * i), resultsPanel.getWidth()-40, itemHeight);
            title.setForeground(Constants.fontColor);
            title.setBackground(Constants.highlight);
            title.setEditable(false);
            title.setLineWrap(true);
            title.setWrapStyleWord(true);

            resultItem.add(title);
            resultItem.setBackground(Constants.highlight);
            resultItem.setForeground(Constants.fontColor);

            resultsPanel.add(resultItem);

            if (i < oldMovies.length-1) {
                JSeparator sep = new JSeparator();
                sep.setBackground(Constants.highlight);
                sep.setForeground(Constants.fontColor);
                resultsPanel.add(sep);
            }
        }
        scrollPane.setVisible(true);
    }
}

class LeonardoDiCaprioMovies implements Runnable {
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Credits credits;
    private Movies movies;
    private JLabel loadingText;

    public LeonardoDiCaprioMovies(JScrollPane scrollPane, JPanel resultsPanel, Credits credits, Movies movies) {
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.credits = credits;
        this.movies = movies;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching for Tom Hanks Movies...");
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

        Cast[] listOfCastMatched = credits.findCast("Tom Hanks");

        if (listOfCastMatched == null || listOfCastMatched.length == 0) {
            loadingText.setText("No cast member with \"Tom Hanks\" in the name was found!");
            System.out.println("\tNo cast member with \"Tom Hanks\" in the name was found!");
            scrollPane.setVisible(true);
            return;
        }

        int[] castMemberMovies = credits.getFilmIDsFromCastID(listOfCastMatched[0].getID());

        if (castMemberMovies == null || castMemberMovies.length == 0) {
            loadingText.setText("No Tom Hanks movies found!");
            System.out.println("\tNo Tom Hanks movies found");
            scrollPane.setVisible(true);
            return;
        } else {
            loadingText.setText("Processing " + castMemberMovies.length + " Tom Hanks movies...");
            System.out.println("\t" + castMemberMovies.length + " movies featuring Tom Hanks were found!");
        }

        final int itemWidth = 180;
        final int itemHeight = scrollPane.getHeight() - 20;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new GridLayout(1, BoxLayout.X_AXIS, 10, 10));
        resultsPanel.setSize(itemWidth * castMemberMovies.length, itemHeight);

        for (int i = 0; i < castMemberMovies.length; i++) {
            String filmNameString = movies.getTitle(castMemberMovies[i]);
            if (filmNameString == null) {
                filmNameString = "Unknown Film";
            }
            JLabel textLabel = new JLabel(filmNameString);

            // REFERENCE: https://stackoverflow.com/questions/21587309/positioning-jlabel-in-jpanel-below-the-image
            Image poster = getPosterImage(movies.getPoster(castMemberMovies[i]));
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