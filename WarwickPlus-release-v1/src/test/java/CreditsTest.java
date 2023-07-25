import stores.*;
import java.util.Calendar;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CreditsTest {

    // various credits combinations
    private Credits removedCredits = new Credits();
    private Credits emptyCredits = new Credits();
    private Credits manyCredits = new Credits();
    private Credits oneCredits = new Credits();
    private Credits starCredits = new Credits();

    private Ratings ratings = new Ratings();

    // negative ID
    private int fakeMovieID = 210;

    // cast and crew for tests
    private Crew johnLasseter = new Crew("1","Directing", 2, 1, "Director", "John Lasseter", "John Lasseter profilepath");
    private Cast woody = new Cast(1, "Woody(Voice", "1", 2, 1, "Tom Hanks", 0, "Woody profilepath");
    private Crew janeDoe = new Crew("2","Lighting", 2, 2, "Light Manager", "Jane Doe", "Jane Doe profilepath");
    private Cast buzz = new Cast(2, "Buzz(Voice", "2", 2, 2, "Tim Allen", 1, "Buzz profilepath");
    private Cast isastar = new Cast(3, "isastar(Voice", "3", 2, 3, "isastar", 2, "isastar profilepath");
    private Cast notastar = new Cast(4, "notastar(Voice", "4", 2, 4, "notastar", 3, "notastar profilepath");
           
    

    @BeforeAll
    void setUp(){

        Crew[] tmpCrewID1 = {johnLasseter};
        Cast[] tmpCastID1 = {woody};

        int tmpID = 201;

        manyCredits.add(tmpCastID1, tmpCrewID1, tmpID);

        oneCredits.add(tmpCastID1, tmpCrewID1, tmpID);

        removedCredits.add(tmpCastID1, tmpCrewID1, 201);
        removedCredits.add(tmpCastID1, tmpCrewID1, 202);
        removedCredits.add(tmpCastID1, tmpCrewID1, 203);

        Crew[] tmpCrewID2 = {janeDoe};
        Cast[] tmpCastID2 = {buzz};
        manyCredits.add(tmpCastID2, tmpCrewID1, 202);
        manyCredits.add(tmpCastID1, tmpCrewID2, 203);

        Crew[] tmpCrewID12 = {johnLasseter, janeDoe};
        Cast[] tmpCastID12 = {woody, buzz};
        manyCredits.add(tmpCastID12, tmpCrewID12, 204);

        // make woody and buzz stars then make the final movie rated lower

        ratings.add(1, 201, 4.5f, calendarYear(2000));
        ratings.add(1, 202, 4.5f, calendarYear(2000));
        ratings.add(1, 203, 4.5f, calendarYear(2000));
        ratings.add(1, 204, 3.5f, calendarYear(2000));
        // ratings.add(1, 205, 3.5f, calendarYear(2000));

        Cast[] notstarCast = {notastar};

        Cast[] tmpStarCast = {woody, buzz, isastar};        
        starCredits.add(tmpStarCast, tmpCrewID12, 201);
        starCredits.add(tmpStarCast, tmpCrewID12, 202);
        starCredits.add(tmpStarCast, tmpCrewID12, 203);
        starCredits.add(notstarCast, tmpCrewID1, 204);

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
     * Checks that the cast for film id 201 is the same using compareTo
     */
    @Test void testGetCastPos(){

        Cast[] tmpCast = {woody};

        System.out.println("\nStarting testGetCastPos...");

        assertArrayEquals(tmpCast, oneCredits.getCast(201), "Incorrect value returned.");

    }

    /**
     * Should return null for a not existent ID.
     */
    @Test void testGetCastNeg(){

        System.out.println("\nStarting testGetCastNeg...");

        // Crew[] tmpCrew = {new Crew("52fe4284c3a36847f8024f49","Directing", 2, 1, "Director", "John Lasseter", "John Lasseter profilepath")};
        
        assertNull(oneCredits.getCast(fakeMovieID), "Should return null for a not existent ID.");

    }

    /**
     * Checks that the crew for film id 201 is the same using compareTo
     */
    @Test void testGetCrewPos(){
        
        Crew[] tmpCrew = {johnLasseter};

        System.out.println("\nStarting testGetCastPos...");

        assertArrayEquals(tmpCrew, oneCredits.getCrew(201), "Incorrect values returned.");

    }

    /**
     * Should return null for a not existent ID.
     */
    @Test void testGetCrewNeg(){

        System.out.println("\nStarting testGetCrewNeg...");

        // Crew[] tmpCrew = {new Crew("52fe4284c3a36847f8024f49","Directing", 2, 7879, "Director", "John Lasseter", "John Lasseter profilepath")};
        
        assertNull(oneCredits.getCrew(fakeMovieID), "Should return null for a not existent ID.");
    }


    /**
     * Checks array of size 1 against another, therefore order does not matter.
     */
    @Test void testGetFilmIDsPos(){
        System.out.println("\nStarting testGetFilmIDsPos...");

        int [] tmpFilmIDs = {201};

        assertArrayEquals(tmpFilmIDs, oneCredits.getFilmIDs(), "Incorrect values returned.");
    }


    /**
     * If credits is empty this should return an empty int[].
     */
    @Test void testGetFilmIDsNeg(){

        System.out.println("\nStarting testGetFilmIDsNeg...");

        assertArrayEquals(new int[0], emptyCredits.getFilmIDs(), "If credits is empty this should return an empty array.");

    }
    
    /**
     * Removing a valid ID returns true
     */
    @Test void testRemovePos(){
        System.out.println("\nStarting testRemovePos...");
        assertTrue(removedCredits.remove(203), "Removed credit not returning true.");
    }



    /**
     * Removing 202 should leave 201, the only ID in oneCredits, this relies on getFilmIDs working.
     */
    @Test void testRemovePosValues(){
        System.out.println("\nStarting testRemovePosValues...");

        removedCredits.remove(202);

        assertArrayEquals(oneCredits.getFilmIDs(), removedCredits.getFilmIDs(), "The IDs according to getFilmIDs is not as expected after removal, this could be an issue with remove() or an issue with getFilmIDs().");
    }
    

    /**
     * Removing a not valid id should return a False
     */
    @Test void testRemoveNeg(){
        System.out.println("\nStarting testRemoveNeg...");

        assertFalse(removedCredits.remove(fakeMovieID), "Not valid credit removal should return false.");
    }

    /**
     * This method takes in a castID int and returns all films int[], to do this, make 3 films, 2 with the same cast
     * Calling cast id 1, should return films 201, 203, student output is sorted to compare arrays as not explicityly stated
     */
    @Test void testGetFilmIDsFromCastIDPos(){
        System.out.println("\nStarting testGetFilmIDsFromCastIDPos...");

        int[] tmpFilmIDs = {201, 203, 204};
        int[] testFilmIDs = manyCredits.getFilmIDsFromCastID(1);
        Arrays.sort(testFilmIDs);

        assertArrayEquals(tmpFilmIDs, testFilmIDs, "Incorrect values returned.");

    }
    /**
     * Not valid castID should returm empty array
     *  
     */
    @Test void testGetFilmIDsFromCastIDNeg(){
        System.out.println("\nStarting testGetFilmIDsFromCastIDNeg...");

        int[] tmpFilmIDs = {};
        int[] testFilmIDs = manyCredits.getFilmIDsFromCastID(1000);

        assertArrayEquals(tmpFilmIDs, testFilmIDs, "Not valid castID should returm empty array.");

    }

    /**
     * This method takes in a crewID int and returns all films int[], to do this, make 3 films, 2 with the same crew
     * Calling crew id 1, should return films 201, 202, student output is sorted to compare arrays as not explicityly stated
     */
    @Test void testGetFilmIDsFromCrewIDPos(){
        System.out.println("\nStarting testGetFilmIDsFromCrewIDPos...");

        int[] tmpFilmIDs = {201, 202, 204};
        int[] testFilmIDs = manyCredits.getFilmIDsFromCrewID(1);
        Arrays.sort(testFilmIDs);

        assertArrayEquals(tmpFilmIDs, testFilmIDs, "Incorrect values returned.");

    }
    /**
     * Not valid crewID should returm empty array
     *  
     */
    @Test void testGetFilmIDsFromCrewIDNeg(){
        System.out.println("\nStarting testGetFilmIDsFromCrewIDNeg...");

        int[] tmpFilmIDs = {};
        int[] testFilmIDs = manyCredits.getFilmIDsFromCrewID(1000);

        assertArrayEquals(tmpFilmIDs, testFilmIDs, "Not valid crewID should returm empty array.");

    }


    /**
     * Should return 1 when calling 201
     */
    @Test void testSizeOfCastPos(){
        System.out.println("\nStarting testSizeOfCastPos...");

        assertEquals(1, oneCredits.sizeOfCast(201), "Incorrect value returned.");
    }

    /**
     * Should return -1 when calling not validID
     */
    @Test void testSizeOfCastNeg(){
        System.out.println("\nStarting testSizeOfCastNeg...");

        assertEquals(-1, oneCredits.sizeOfCast(fakeMovieID), "Should return -1 for a not existent ID.");
    }

    /**
     * Should return 1 when calling 201
     */
    @Test void testSizeOfCrewPos(){
        System.out.println("\nStarting testSizeOfCrewPos...");

        assertEquals(1, oneCredits.sizeofCrew(201), "Incorrect value returned.");
    }

    /**
     * Should return -1 when calling not validID
     */
    @Test void testSizeOfCrewNeg(){
        System.out.println("\nStarting testSizeOfCrewNeg...");

        assertEquals(-1, oneCredits.sizeofCrew(fakeMovieID), "Should return -1 for a not existent ID.");
    }

    
    /**
     * Value for cast ID 1 is Tom Hanks
     */
    @Test void testGetCastNamePos(){
        System.out.println("\nStarting testGetCastNamePos...");

        assertEquals("Tom Hanks", oneCredits.getCastName(1), "Incorrect value returned.");
    }

    /**
     * If cast ID is not valid return null
     */
    @Test void testGetCastNameNeg(){
        System.out.println("\nStarting testGetCastNameNeg...");

        assertNull(oneCredits.getCastName(1000), "Should return null for a not existent ID.");
    }

    /**
     * Value for cast ID 1 is John Lasseter
     */
    @Test void testGetCrewNamePos(){
        System.out.println("\nStarting testGetCrewNamePos...");

        assertEquals("John Lasseter", oneCredits.getCrewName(1), "Incorrect value returned.");
    }

    /**
     * If cast ID is not valid return null
     */
    @Test void testGetCrewNameNeg(){
        System.out.println("\nStarting testGetCrewNameNeg...");

        assertNull(oneCredits.getCrewName(1000), "Should return null for a not existent ID.");
    }



    /**
     * Should return [1,2], sorted function result prior to comparison
     */
    @Test void testGetUniqueCastIDsPos(){
        System.out.println("\nStarting testGetUniqueCastIDsPos...");

        int[] tmpCastIDs = {1, 2};
        int[] testCastIDs = manyCredits.getUniqueCastIDs();
        Arrays.sort(testCastIDs);

        assertArrayEquals(tmpCastIDs, testCastIDs, "Incorrect values returned.");
    }
    
    /**
     * Return empty array if there is nothing there.
     */
    @Test void testGetUniqueCastIDsNeg(){
        System.out.println("\nStarting testGetUniqueCastIDsNeg...");

        assertArrayEquals(new int[0], emptyCredits.getUniqueCastIDs());
    }

    /**
     * Should return [1,2], sorted function result prior to comparison
     */
    @Test void testGetUniqueCrewIDsPos(){
        System.out.println("\nStarting testGetUniqueCrewIDsPos...");

        int[] tmpCrewIDs = {1, 2};
        int[] testCrewIDs = manyCredits.getUniqueCrewIDs();
        Arrays.sort(testCrewIDs);

        assertArrayEquals(tmpCrewIDs, testCrewIDs, "Incorrect values returned.");
    }
    
    /**
     * Return empty array if there is nothing there.
     */
    @Test void testGetUniqueCrewIDsNeg(){
        System.out.println("\nStarting testGetUniqueCrewIDsNeg...");

        assertArrayEquals(new int[0], emptyCredits.getUniqueCrewIDs(), "Should return empty array if there is nothing there.");
    }

    /**
     * Find all casts that have buzz in them
     */
    @Test void testFindCastPos(){

        Cast[] tmpCast = {buzz};

        System.out.println("\nStarting testFindCastPos...");
        
        assertArrayEquals(tmpCast, manyCredits.findCast("Tim Allen"), "The results are not correct.");

    }

    
    /**
     * Checks that the cast array returned has length equal to 0, so is empty.
     */
    @Test void testFindCastNeg(){

        System.out.println("\nStarting testFindCastNeg...");

        assertEquals(0, manyCredits.findCast("Unreasonably long check").length, "Finding Cast where there are not.");

    }

    /**
     * Find all crews that have janedoe in them
     */
    @Test void testFindCrewPos(){

        Crew[] tmpCrew = {janeDoe};

        System.out.println("\nStarting testFindCastPos...");

        assertArrayEquals(tmpCrew, manyCredits.findCrew("Jane Doe"), "The results are not correct.");

    }
    
    /**
     * Checks that the cast array returned has length equal to 0, so is empty.
     */
    @Test void testFindCrewNeg(){

        System.out.println("\nStarting testFindCrewNeg...");

        assertEquals(0, manyCredits.findCrew("Unreasonably long check").length, "Finding Crew where there are not.");

    }

    /**
     * Stars should be ids 1,2,3
     */
    @Test void testFindStarCastIDPos(){
        System.out.println("\nStarting testFindStarCastIDPos...");

        int[] tmpStarCast = {1,2,3};
        int[] starCast = starCredits.findStarCastID(ratings);
        Arrays.sort(starCast);


        assertArrayEquals(tmpStarCast, starCast, "Incorrect values returned.");
    }
    
    /**
     * Should return empty array since there are no credits.
     */
    @Test void testFindStarCastIDNeg(){
        System.out.println("\nStarting testFindStarCastIDNeg...");

        assertArrayEquals(new int[0], emptyCredits.findStarCastID(ratings), "Should return empty array if there are no credits.");
    }

    /**
     * Super Stars should be ids 1,2,3
     */
    @Test void testFindSuperStarCastIDPos(){
        System.out.println("\nStarting testFindSuperStarCastIDPos...");

        int[] tmpSuperStarCast = {1,2,3};
        int[] superStarCast = starCredits.findSuperStarCastID(ratings);
        Arrays.sort(superStarCast);


        assertArrayEquals(tmpSuperStarCast, superStarCast, "Incorrect values returned.");
    }

    /**
     * Should return empty array since there are no credits.
     */
    @Test void testFindSuperStarCastIDNeg(){
        System.out.println("\nStarting testFindSuperStarCastIDNeg...");

        assertArrayEquals(new int[0], emptyCredits.findSuperStarCastID(ratings), "Should return empty array if there are no credits.");
    }
    
}
