package screen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputListener;

import stores.*;
import utils.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;

public class RatingsScreen {
    public static void createPanel(JPanel panel, Movies movies, Ratings ratings, Credits credits) {
        System.out.println("Ratings screen");
        panel.setVisible(false);
        panel.removeAll();

        // Get the top 100 users with the most ratings & display their UID, average rating and number of ratings. Ordered by number of reviews, descending.
        TitledBorder mostRatingsUserBorder;
        mostRatingsUserBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "The " + Constants.mostUserRatingCount + " Users Who Rated the Most");
        mostRatingsUserBorder.setTitleJustification(TitledBorder.CENTER);
        mostRatingsUserBorder.setTitleColor(Constants.fontColor);

        JPanel mostRatingsUserOuter = new JPanel();
        mostRatingsUserOuter.setBounds((int)(panel.getWidth()*0.75)+5, 5, (int) (panel.getWidth() * 0.25) - 10, panel.getHeight()-35);
        mostRatingsUserOuter.setBorder(mostRatingsUserBorder);
        mostRatingsUserOuter.setForeground(Constants.fontColor);
        mostRatingsUserOuter.setBackground(Constants.highlight);
        mostRatingsUserOuter.setLayout(new GridBagLayout());

        JPanel mostRatingsUserInner = new JPanel();
        mostRatingsUserInner.setBackground(Constants.highlight);
        mostRatingsUserInner.setForeground(Constants.fontColor);
        JScrollPane mostRatingsUser = new JScrollPane(mostRatingsUserInner);
        mostRatingsUser.setBounds(10, 25, (int) (panel.getWidth() * 0.25) - 30, panel.getHeight() - 70);
        mostRatingsUser.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.25) - 30, panel.getHeight() - 70));
        mostRatingsUser.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.25) - 30, panel.getHeight() - 70));
        mostRatingsUser.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth() * 0.25) - 30, panel.getHeight() - 70));
        mostRatingsUser.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth() * 0.25) - 30, panel.getHeight() - 70));
        mostRatingsUser.setBackground(Constants.highlight);
        mostRatingsUser.setForeground(Constants.fontColor);
        mostRatingsUserOuter.add(mostRatingsUser);

        MostRatedUsersRunnable mostRatedUsersRunnable = new MostRatedUsersRunnable(panel, mostRatingsUser, mostRatingsUserInner, ratings);



        // Get top movies with the best rating
        TitledBorder topMovieRatingBorder;
        topMovieRatingBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Top " + Constants.topMoviesCount + " Most Rated Movies");
        topMovieRatingBorder.setTitleJustification(TitledBorder.LEFT);
        topMovieRatingBorder.setTitleColor(Constants.fontColor);
        JPanel topMovieRatingOuter = new JPanel();
        topMovieRatingOuter.setBounds(5, (int) (panel.getHeight()*0.35)+2, (int) (panel.getWidth()*0.75)-10, (int) (panel.getHeight() * 0.65)-30);
        topMovieRatingOuter.setBorder(topMovieRatingBorder);
        topMovieRatingOuter.setBackground(Constants.highlight);
        topMovieRatingOuter.setForeground(Constants.fontColor);
        topMovieRatingOuter.setLayout(new GridBagLayout());

        JPanel topMovieRatingInner = new JPanel();
        topMovieRatingInner.setBackground(Constants.highlight);
        topMovieRatingInner.setForeground(Constants.fontColor);
        JScrollPane topMovieRating = new JScrollPane(topMovieRatingInner);
        topMovieRating.setBounds(10, 25, (int) (panel.getWidth() * 0.75) - 30, (int) (panel.getHeight() * 0.65) - 65);
        topMovieRating.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.75) - 30, (int) (panel.getHeight() * 0.65) - 65));
        topMovieRating.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.75) - 30, (int) (panel.getHeight() * 0.65) - 65));
        topMovieRating.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth() * 0.75) - 30, (int) (panel.getHeight() * 0.65) - 65));
        topMovieRating.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth() * 0.75) - 30, (int) (panel.getHeight() * 0.65) - 65));
        topMovieRating.setBackground(Constants.highlight);
        topMovieRating.setForeground(Constants.fontColor);
        topMovieRatingOuter.add(topMovieRating);

        TopRatedMoviesRunnable topRatedMoviesRunnable = new TopRatedMoviesRunnable(panel, topMovieRating, topMovieRatingInner, movies, ratings);

        // Display movies released after 2015
        TitledBorder recentMoviesBorder;
        recentMoviesBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Movies Released Before 2000");
        recentMoviesBorder.setTitleJustification(TitledBorder.LEFT);
        recentMoviesBorder.setTitleColor(Constants.fontColor);
        JPanel recentMoviesOuter = new JPanel();
        recentMoviesOuter.setBounds(5, 5, (int) (panel.getWidth()*0.75)-10, (int) (panel.getHeight()*0.35)-5);
        recentMoviesOuter.setBorder(recentMoviesBorder);
        recentMoviesOuter.setBackground(Constants.highlight);
        recentMoviesOuter.setForeground(Constants.fontColor);
        recentMoviesOuter.setLayout(new GridBagLayout());

        JPanel recentMoviesInner = new JPanel();
        recentMoviesInner.setBackground(Constants.highlight);
        recentMoviesInner.setForeground(Constants.fontColor);
        JScrollPane recentMovies = new JScrollPane(recentMoviesInner);
        recentMovies.setBounds(20, 20, (int) (panel.getWidth()*0.75)-30, (int) (panel.getHeight()*0.35)-30);
        recentMovies.setMinimumSize(new Dimension((int) (panel.getWidth()*0.75)-30, (int) (panel.getHeight()*0.35)-30));
        recentMovies.setPreferredSize(new Dimension((int) (panel.getWidth()*0.75)-30, (int) (panel.getHeight()*0.35)-30));
        recentMovies.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth()*0.75)-30, (int) (panel.getHeight()*0.35)-30));
        recentMovies.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth()*0.75)-30, (int) (panel.getHeight()*0.35)-30));
        recentMovies.setBackground(Constants.highlight);
        recentMovies.setForeground(Constants.fontColor);
        recentMoviesOuter.add(recentMovies);

        RecentMoviesRunnable recentMoviesRunnable = new RecentMoviesRunnable(panel, recentMovies, recentMoviesInner, movies, ratings, credits);

        panel.add(mostRatingsUserOuter);
        panel.add(topMovieRatingOuter);
        panel.add(recentMoviesOuter);

        panel.setVisible(true);

        SwingUtilities.invokeLater(mostRatedUsersRunnable);
        SwingUtilities.invokeLater(topRatedMoviesRunnable);
        SwingUtilities.invokeLater(recentMoviesRunnable);
    }
}

class RecentMoviesRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Movies movies;
    private Ratings ratings;
    private Credits credits;
    private JLabel loadingText;

    public RecentMoviesRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Movies movies, Ratings ratings, Credits credits) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.movies = movies;
        this.ratings = ratings;
        this.credits = credits;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching Movies Released Before 2000...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);
    }

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 1, 0, 0, 0); // date is set to 01/01/2000 00:00:00
        Calendar dawnOfTime = Calendar.getInstance();
        dawnOfTime.set(1970, 1, 1, 0, 0, 0); // date is set to EPOCH 0, 01/01/1970 00:00:00
        System.out.println("\tGetting film ID's between " + dawnOfTime.getTime().toString() + " and " + cal.getTime().toString());
        int[] results = movies.getAllIDsReleasedInRange(dawnOfTime, cal);

        scrollPane.setVisible(false);

        if (results == null || results.length == 0) {
            loadingText.setText("No movies that were released after " + cal.getTime() + " were found!");
            System.out.println("\t... No movies that were released after " + cal.getTime() + " were found");
            scrollPane.setVisible(true);
            return;
        }
        else {
            loadingText.setText("Processing movies released after " + cal.getTime());
            System.out.println("\t... " + results.length + " movies were found");
        }

        int currentWidth = 0;
        final int gapSize = 5;
        int currentHeight = gapSize;
        
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setBackground(Constants.highlight);

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < results.length; i ++) {
            String filmNameString = movies.getTitle(results[i]);
            int movieID = results[i];
            if (filmNameString == null){
                filmNameString = "Unknown Film";
            } else {
                float[] movieRatings = ratings.getMovieRatings(results[i]);
                if (movieRatings == null || movieRatings.length == 0) {
                    filmNameString = filmNameString + " (No ratings)";
                } else {
                    filmNameString = filmNameString + " (" + new DecimalFormat("0.00").format(ratings.getMovieAverageRatings(results[i])) + "★)";
                }
            }

            //Create film label
            JLabel textLabel = new JLabel(filmNameString);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            int stringWidth = masterPanel.getGraphics().getFontMetrics().stringWidth(filmNameString)+10;
            textLabel.setForeground(Constants.fontColor);
            tmpPanel.add(textLabel);
            tmpPanel.addMouseListener(new MouseInputListener() {
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

            //Decides if the movie should be on the current line, or a new line should be started
            if (currentWidth + (4 * gapSize) + stringWidth > resultsPanel.getWidth()) {
                currentHeight += 30 + gapSize;
                tmpPanel.setBounds(gapSize, currentHeight, stringWidth, 30);
                currentWidth = gapSize + stringWidth;
                resultsPanel.setSize(resultsPanel.getWidth(), currentHeight + gapSize + 30);
                resultsPanel.add(horizontalPanel);
                horizontalPanel = new JPanel();
                horizontalPanel.setBackground(Constants.highlight);
                horizontalPanel.add(tmpPanel);
            } else {
                tmpPanel.setBounds(currentWidth + gapSize, currentHeight, stringWidth, 30);
                currentWidth += gapSize+ stringWidth;
                horizontalPanel.add(tmpPanel);
            }

            resultsPanel.add(horizontalPanel);
        }
        scrollPane.setVisible(true);
    }
}

class MostRatedUsersRunnable implements Runnable {

    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Ratings ratings;
    private JLabel loadingText;

    public MostRatedUsersRunnable(JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Ratings ratings) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.ratings = ratings;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching Top Users...");
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setVerticalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        resultsPanel.add(loadingText);

        scrollPane.setVisible(true);
    }

    @Override
    public void run() {
        int[] userResults = ratings.getMostRatedUsers(Constants.mostUserRatingCount);

        scrollPane.setVisible(false);

        if (userResults == null || userResults.length == 0) {
            loadingText.setText("No user ratings found!");
            System.out.println("\tNo user ratings found");
            scrollPane.setVisible(true);
            return;
        } else {
            loadingText.setText("Processing " + userResults.length + " Users...");
            System.out.println("\t" + userResults.length + " Users found (max: " + Constants.mostUserRatingCount + ")");
        }

        final int itemHeight = 50;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setSize(scrollPane.getWidth() - 40, userResults.length * (itemHeight + ((new JSeparator()).getHeight())));

        for (int i = 0; i < userResults.length; i++) {
            JPanel resultItem = new JPanel();
            resultItem.setSize(scrollPane.getWidth() - 40, itemHeight);

            String resultString = "UID: " + userResults[i] + "\t " + new DecimalFormat("0.00").format(ratings.getUserAverageRatings(userResults[i])) + "★ (" + ratings.getUserRatings(userResults[i]).length + ")";

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

            if (i < userResults.length - 1) {
                JSeparator sep = new JSeparator();
                sep.setBackground(Constants.highlight);
                sep.setForeground(Constants.fontColor);
                resultsPanel.add(sep);
            }
        }
        scrollPane.setVisible(true);
    }
}

class TopRatedMoviesRunnable implements Runnable {
    private JPanel masterPanel;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private Movies movies;
    private Ratings ratings;
    private JLabel loadingText;

    public TopRatedMoviesRunnable (JPanel masterPanel, JScrollPane scrollPane, JPanel resultsPanel, Movies movies, Ratings ratings) {
        this.masterPanel = masterPanel;
        this.scrollPane = scrollPane;
        this.resultsPanel = resultsPanel;
        this.movies = movies;
        this.ratings = ratings;

        scrollPane.setVisible(false);

        loadingText = new JLabel("Searching Top Rated Movies...");
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
        int[] movieResults = ratings.getTopMovies(Constants.topMoviesCount);
        scrollPane.setVisible(false);

        if (movieResults == null || movieResults.length == 0) {
            loadingText.setText("No top movie ratings found!");
            System.out.println("\tNo top movie ratings found");
            scrollPane.setVisible(true);
            return;
        } else {
            loadingText.setText("Processing " + movieResults.length + " of the top movies...");
            System.out.println("\t" + movieResults.length + " of the top movies found (max: " + Constants.topMoviesCount + ")");
        }

        final int itemWidth = 225;
        final int itemHeight = scrollPane.getHeight() - 20;

        resultsPanel.removeAll();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.X_AXIS));
        resultsPanel.setSize(itemWidth * movieResults.length, itemHeight);

        for (int i = 0; i < movieResults.length; i++) {
            String filmNameString = movies.getTitle(movieResults[i]);
            if (filmNameString == null){
                filmNameString = "Unknown Film";
            } else {
                filmNameString = "  " + filmNameString + " (" + ratings.getMovieRatings(movieResults[i]).length + " ratings)  ";
            }
            JLabel textLabel = new JLabel(filmNameString);

            // REFERENCE: https://stackoverflow.com/questions/21587309/positioning-jlabel-in-jpanel-below-the-image
            Image poster = getPosterImage(movies.getPoster(movieResults[i]));
            if (poster == null){
                System.err.println("poster is null");
            }
            textLabel.setIcon(new ImageIcon(poster.getScaledInstance(itemWidth, itemHeight - 20, Image.SCALE_DEFAULT)));
            textLabel.setHorizontalTextPosition(JLabel.CENTER);
            textLabel.setVerticalTextPosition(JLabel.BOTTOM);
            textLabel.setSize(itemWidth, itemHeight);

            resultsPanel.add(textLabel);

            if (true) continue;

            JPanel resultItem = new JPanel();
            resultItem.setBackground(Constants.background);
            resultItem.setForeground(Constants.fontColor);
            resultItem.setSize(new Dimension(itemWidth-10, scrollPane.getHeight()));

            DisplayImage filmPoster = null;
            if (filmNameString == null) {
                filmNameString = "Unknown film";
                try {
                    filmPoster = new DisplayImage("src/main/resources/img/Movie-Unknown.png", 10, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.err.println("Unknown film with ID: " + movieResults[i]);
            } else {
                String filmPosterURL = movies.getPoster(movieResults[i]);
                if (filmPosterURL == null || filmPosterURL == "") {
                    filmPosterURL = "src/main/resources/img/Movie-Unknown.png";
                } else {
                    filmPosterURL = "https://image.tmdb.org/t/p/original" + filmPosterURL;
                }
                try {
                    if (filmPosterURL == "src/main/resources/img/Movie-Unknown.png") {
                        filmPoster = new DisplayImage(filmPosterURL);
                    } else {
                        filmPoster = new DisplayImage(filmPosterURL);
                    }
                } catch (IOException e) {
                    System.err.print("\t...No file found");
                    if (filmPosterURL != "src/main/resources/img/Movie-Unknown.png") {
                        try {
                            System.err.print(", so putting in the default image\n");
                            filmPosterURL = "src/main/resources/img/Movie-Unknown.png";
                            filmPoster = new DisplayImage(filmPosterURL);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            // if (filmPoster != null) {
            //     filmPoster.setBounds((int) (panel.getWidth() * 0.8), 5, (int) (panel.getWidth() * 0.2)-5, (int) (panel.getHeight() * 0.5)-4);
            // }

            filmPoster.setSize(100,200);

            resultItem.add(filmPoster);


            // JTextArea filmName = new JTextArea(filmNameString);
            // filmName.setBackground(Constants.background);
            // filmName.setForeground(Constants.fontColor);
            // resultItem.add(filmName);

            // JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("src/main/resources/img/Movie-Unknown.png").getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT)));
            // resultItem.add(imageLabel);

            resultsPanel.add(resultItem);
        }
        scrollPane.setVisible(true);
    }
}