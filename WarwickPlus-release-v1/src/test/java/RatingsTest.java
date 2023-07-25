import stores.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingsTest {
    
    private Ratings ratings = new Ratings();
    private int fakeUserID = 110;
    private int fakeMovieID = 210;

    @BeforeAll
    void setUp(){

        Calendar timestamp;
        
        // ratings for uid 101
        timestamp = calendarYear(1989);
        ratings.add(101, 201, 0.1f, timestamp);

        timestamp = calendarYear(1991);
        ratings.add(101, 202, 1.1f, timestamp);

        timestamp = calendarYear(1993);
        ratings.add(101, 203, 2.1f, timestamp);

        timestamp = calendarYear(1995);
        ratings.add(101, 204, 3.1f, timestamp);

        timestamp = calendarYear(1997);
        ratings.add(101, 205, 4.1f, timestamp);

        // ratings for uid 102 
        
        timestamp = calendarYear(2001);
        ratings.add(102, 201, 2.2f, timestamp);

        timestamp = calendarYear(2005);
        ratings.add(102, 202, 3.2f, timestamp);

        timestamp = calendarYear(2009);
        ratings.add(102, 203, 4.2f, timestamp);

        // ratings for uid 103 
        timestamp = calendarYear(2003);
        ratings.add(103, 201, 1.3f, timestamp);

        timestamp = calendarYear(2007);
        ratings.add(103, 202, 2.3f, timestamp);

        timestamp = calendarYear(2013);
        ratings.add(103, 203, 3.3f, timestamp);

        timestamp = calendarYear(2013);
        ratings.add(103, 204, 4.3f, timestamp);

        // ratings for uid 104
        timestamp = calendarYear(2013);
        ratings.add(104, 201, 4.4f, timestamp);

        // ratings for uid 105
        timestamp = calendarYear(2013);
        ratings.add(105, 201, 3.5f, timestamp);

        timestamp = calendarYear(2013);
        ratings.add(105, 202,4.5f, timestamp);

    }

    Calendar calendarYear(int year){
        Calendar timestamp = Calendar.getInstance();
        timestamp.set(Calendar.YEAR, year);
        timestamp.set(Calendar.WEEK_OF_YEAR, 1);
        timestamp.set(Calendar.DAY_OF_YEAR, 1);
        timestamp.set(Calendar.MONTH, 1);
        timestamp.set(Calendar.DAY_OF_MONTH, 1);
        timestamp.set(Calendar.WEEK_OF_MONTH, 1);
        timestamp.set(Calendar.AM_PM, 1);
        timestamp.set(Calendar.HOUR_OF_DAY, 0);
        timestamp.set(Calendar.HOUR, 0);
        timestamp.set(Calendar.HOUR_OF_DAY, 0);
        timestamp.set(Calendar.MINUTE, 0);
        timestamp.set(Calendar.SECOND, 0);
        timestamp.set(Calendar.MILLISECOND, 0);

        return timestamp;
    }


    
    /**
     * Correct values for movie 201 are 0.1f, 2.2f, 1.3f, 4.4f, 3.5f.
     */
    @Test void testGetMovieRatingsPos(){

        System.out.println("\nStarting testGetMovieRatingsPos...");

        float[] tmpRatings = {0.1f, 2.2f, 1.3f, 4.4f, 3.5f};

        assertArrayEquals(tmpRatings , ratings.getMovieRatings(201), "Not returning correct ratings for movie.");
    }

    /**
     * Fake ID should return empty array.
     */
    @Test void testGetMovieRatingsNeg(){

        System.out.println("\nStarting testGetMovieRatingsNeg...");

        assertArrayEquals(new float[0], ratings.getMovieRatings(fakeMovieID), "Non existent ID should return empty array.");
    }

    /**
     * Correct values for user 101 are 0.1f, 1.1f, 2.1f, 3.1f, 4.1f.
     */
    @Test void testGetUserRatingsPos(){

        System.out.println("\nStarting testGetUserRatingsPos...");

        float[] tmpRatings = {0.1f, 1.1f, 2.1f, 3.1f, 4.1f};

        assertArrayEquals(tmpRatings , ratings.getUserRatings(101), "Not returning correct ratings for user.");
    }

    /**
     * Fake ID should return empty array.
     */
    @Test void testGetUserRatingsNeg(){

        System.out.println("\nStarting testGetUserRatingsNeg...");

        assertArrayEquals(new float[0], ratings.getUserRatings(fakeUserID), "Non existent ID should return empty array.");
    }

    /**
     * Top 3 users are 101, 103, 102 in order with 5,4,3 respectively.
     */
    @Test void testGetMostRatedUsersPos (){
        System.out.println("\nStarting testGetMostRatedUsersPos...");
        int num = 3;
        int[] tmpMostRatedUsers = {101, 103, 102};

        assertArrayEquals(tmpMostRatedUsers, ratings.getMostRatedUsers(num), "Incorrect values returned.");

    }

    /**
     * If asked for none there should be an empty array.
     */
    @Test void testGetMostRatedUsersNeg (){
        
        System.out.println("\nStarting testGetMostRatedUsers ...");
        int num = 0;

        assertArrayEquals(new int[0], ratings.getMostRatedUsers(num), "Empty array should be returned when most rated parameter is 0.");

    }


    /**
     * Top 3 movies are 201, 202, 203 in order with 5,4,3 respectively.
     */
    @Test void testGetTopMoviesPos (){
        System.out.println("\nStarting testGetTopMoviesPos...");
        int num = 3;
        int[] tmpMostRatedUsers = {201, 202, 203};

        assertArrayEquals(tmpMostRatedUsers, ratings.getTopMovies(num), "Incorrect values returned.");

    }

    /**
     * If asked for none there should be an empty array.
     */
    @Test void testGetTopMoviesNeg (){
        
        System.out.println("\nStartingGetTopMoviesUsers ...");
        int num = 0;

        assertArrayEquals(new int[0], ratings.getTopMovies(num), "Empty array should be returned when top parameter is 0.");

    }


    

    @Test void testGetUserAverageRatingsPos (){
        
        System.out.println("\nStarting testGetUserAverageRatings Pos ...");

        assertEquals(2.1f, ratings.getUserAverageRatings(101), "Incorrect average given.");

    }

    /**
     * Average for fake ID should be 0.0f as there will be no results.
     */
    @Test void testGetUserAverageRatingsNeg (){
        
        System.out.println("\nStarting testGetUserAverageRatingsNeg...");

        assertEquals(0.0f, ratings.getUserAverageRatings(fakeUserID), "Value should be 0.0 when no ratings present.");

    }

    /**
     * Average is 2.3f floats cause approximation, so rounding applied.
     */
    @Test void testGetMovieAverageRatingsPos (){
        
        System.out.println("\nStarting testGetMovieAverageRatings Pos ...");

        float number = BigDecimal.valueOf(ratings.getMovieAverageRatings(201))
            .setScale(1, RoundingMode.HALF_EVEN)
            .floatValue();

        assertEquals(2.3f, number, "Incorrect average given.");

    }

    /**
     * Average for fake ID should be 0.0f as there will be no results.
     */
    @Test void testGetMovieAverageRatingsNeg (){
        System.out.println("\nStarting testGetMovieAverageRatingsNeg...");

        assertEquals(0.0f, ratings.getMovieAverageRatings(fakeMovieID), "Value should be 0.0 when no ratings present.");

    }

    /**
     * One rating between 2000 and 2002, this is 2.2.
     */

    @Test void testGetRatingsBetweenPos(){
        Calendar after = calendarYear(2000);
        Calendar before = calendarYear(2002);

        float[] tmpRatingsBetween = {2.2f};

        assertArrayEquals(tmpRatingsBetween, ratings.getRatingsBetween(after, before), "Incorrect values returned.");
    }

    /**
     * One rating between 2002 and 2006 for 202, this is 3.2.
     */

    @Test void testGetRatingsBetweenNeg(){

        Calendar after = calendarYear(2000);

        assertArrayEquals(new float[0], ratings.getRatingsBetween(after, after), "Returning values for date range of 0.");

    }

    /**
     * One rating between 2002 and 2006 for 202, this is 3.2.
     */
    @Test void testGetMovieRatingsBetweenPos(){
        Calendar after = calendarYear(2002);
        Calendar before = calendarYear(2006);

        float[] tmpRatingsBetween = {3.2f};

        assertArrayEquals(tmpRatingsBetween, ratings.getMovieRatingsBetween(202, after, before), "Incorrect values returned.");
    }

    /**
     * As the 2 dates are the same there should be no results, this means that there will be no results so compare to empty float array.
     */
    @Test void testGetMovieRatingsBetweenNeg(){
        Calendar after = calendarYear(2000);

        assertArrayEquals(new float[0], ratings.getMovieRatingsBetween(202, after, after), "Returning values for date range of 0.");
    }



    /**
     * One rating between 2000 and 2004 for 103, this is 1.3.
     */
    @Test void testGetUserRatingsBetweenPos(){
        Calendar after = calendarYear(2000);
        Calendar before = calendarYear(2004);

        float[] tmpRatingsBetween = {1.3f};

        assertArrayEquals(tmpRatingsBetween, ratings.getUserRatingsBetween(103, after, before), "Incorrect values returned.");
    }

    /**
     * As the 2 dates are the same there should be no results, this means that there will be no results so compare to empty float array.
     */
    @Test void testGetUserRatingsBetweenNeg(){
        Calendar after = calendarYear(2000);

        assertArrayEquals(new float[0], ratings.getUserRatingsBetween(103, after, after), "Returning values for date range of 0.");
    }

    /**
     * Expects 15 as that is the number of ratings added.
     */
    @Test void testSize(){
        assertEquals(15, ratings.size(), "Incorrect size.");
    }
    
    
}
