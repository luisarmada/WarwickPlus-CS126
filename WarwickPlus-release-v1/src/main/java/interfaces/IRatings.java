package interfaces;

import java.util.Calendar;
public interface IRatings {
    public boolean add(int userID, int movieID, float rating, Calendar timestamp);

    public boolean remove(int userID, int movieID);

    public boolean set(int userID, int movieID, float rating, Calendar timestamp);

    public float[] getRatingsBetween(Calendar start, Calendar end);
    public float[] getMovieRatingsBetween(int movieID, Calendar start, Calendar end);
    public float[] getUserRatingsBetween(int userID, Calendar start, Calendar end);

    public float[] getMovieRatings(int movieID);
    public float[] getUserRatings(int userID);
    public float getMovieAverageRatings(int movieID);
    public float getUserAverageRatings(int userID);

    public int[] getTopMovies(int num);
    public int[] getMostRatedUsers(int num);

    public String toString();
    public int size();
}
