import stores.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)

class MoviesTest {
    private int batchSize = 20;
    private int fakeID = 10;


    private Movies movies = new Movies();
    private Movies batchMovies = new Movies();
    // batch random data
    private ArrayList<Integer> IDs = new ArrayList<Integer>();
    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> originalTitles = new ArrayList<String>();
    private ArrayList<String> overviews = new ArrayList<String>();
    private ArrayList<String> taglines = new ArrayList<String>();
    private ArrayList<String> statuses = new ArrayList<String>();
    private ArrayList<Genre[]> genresList = new ArrayList<Genre[]>();
    private ArrayList<Calendar> releases = new ArrayList<Calendar>();
    private ArrayList<Long> budgets = new ArrayList<Long>();
    private ArrayList<Long> revenues = new ArrayList<Long>();
    private ArrayList<String[]> languagesList = new ArrayList<String[]>();
    private ArrayList<String> originalLanguages = new ArrayList<String>();
    private ArrayList<Double> runtimes = new ArrayList<Double>();
    private ArrayList<String> homepages = new ArrayList<String>();
    private ArrayList<Boolean> adults = new ArrayList<Boolean>();
    private ArrayList<Boolean> videos = new ArrayList<Boolean>();
    private ArrayList<String> posters = new ArrayList<String>();
    
    private ArrayList<Genre> bankOfGenres = new ArrayList<Genre>();

    // languages for random adding
    private final String[][] iso639Data = {
        {"Abkhazian","ab"},
        {"Afar","aa"},
        {"Afrikaans","af"},
        {"Akan","ak"},
        {"Albanian","sq"},
        {"Amharic","am"},
        {"Arabic","ar"},
        {"Aragonese","an"},
        {"Armenian","hy"},
        {"Assamese","as"},
        {"Avaric","av"},
        {"Avestan","ae"},
        {"Aymara","ay"},
        {"Azerbaijani","az"},
        {"Bambara","bm"},
        {"Bashkir","ba"},
        {"Basque","eu"},
        {"Belarusian","be"},
        {"Bengali","bn"},
        {"Bislama","bi"},
        {"Bosnian","bs"},
        {"Breton","br"},
        {"Bulgarian","bg"},
        {"Burmese","my"},
        {"Catalan, Valencian","ca"},
        {"Chamorro","ch"},
        {"Chechen","ce"},
        {"Chichewa, Chewa, Nyanja","ny"},
        {"Chinese","zh"},
        {"Church Slavic, Old Slavonic, Church Slavonic, Old Bulgarian, Old Church Slavonic","cu"},
        {"Chuvash","cv"},
        {"Cornish","kw"},
        {"Corsican","co"},
        {"Cree","cr"},
        {"Croatian","hr"},
        {"Czech","cs"},
        {"Danish","da"},
        {"Divehi, Dhivehi, Maldivian","dv"},
        {"Dutch, Flemish","nl"},
        {"Dzongkha","dz"},
        {"English","en"},
        {"Esperanto","eo"},
        {"Estonian","et"},
        {"Ewe","ee"},
        {"Faroese","fo"},
        {"Fijian","fj"},
        {"Finnish","fi"},
        {"French","fr"},
        {"Western Frisian","fy"},
        {"Fulah","ff"},
        {"Gaelic, Scottish Gaelic","gd"},
        {"Galician","gl"},
        {"Ganda","lg"},
        {"Georgian","ka"},
        {"German","de"},
        {"Greek, Modern (1453–)","el"},
        {"Kalaallisut, Greenlandic","kl"},
        {"Guarani","gn"},
        {"Gujarati","gu"},
        {"Haitian, Haitian Creole","ht"},
        {"Hausa","ha"},
        {"Hebrew","he"},
        {"Herero","hz"},
        {"Hindi","hi"},
        {"Hiri Motu","ho"},
        {"Hungarian","hu"},
        {"Icelandic","is"},
        {"Ido","io"},
        {"Igbo","ig"},
        {"Indonesian","id"},
        {"Interlingua (International Auxiliary Language Association)","ia"},
        {"Interlingue, Occidental","ie"},
        {"Inuktitut","iu"},
        {"Inupiaq","ik"},
        {"Irish","ga"},
        {"Italian","it"},
        {"Japanese","ja"},
        {"Javanese","jv"},
        {"Kannada","kn"},
        {"Kanuri","kr"},
        {"Kashmiri","ks"},
        {"Kazakh","kk"},
        {"Central Khmer","km"},
        {"Kikuyu, Gikuyu","ki"},
        {"Kinyarwanda","rw"},
        {"Kirghiz, Kyrgyz","ky"},
        {"Komi","kv"},
        {"Kongo","kg"},
        {"Korean","ko"},
        {"Kuanyama, Kwanyama","kj"},
        {"Kurdish","ku"},
        {"Lao","lo"},
        {"Latin","la"},
        {"Latvian","lv"},
        {"Limburgan, Limburger, Limburgish","li"},
        {"Lingala","ln"},
        {"Lithuanian","lt"},
        {"Luba-Katanga","lu"},
        {"Luxembourgish, Letzeburgesch","lb"},
        {"Macedonian","mk"},
        {"Malagasy","mg"},
        {"Malay","ms"},
        {"Malayalam","ml"},
        {"Maltese","mt"},
        {"Manx","gv"},
        {"Maori","mi"},
        {"Marathi","mr"},
        {"Marshallese","mh"},
        {"Mongolian","mn"},
        {"Nauru","na"},
        {"Navajo, Navaho","nv"},
        {"North Ndebele","nd"},
        {"South Ndebele","nr"},
        {"Ndonga","ng"},
        {"Nepali","ne"},
        {"Norwegian","no"},
        {"Norwegian Bokmål","nb"},
        {"Norwegian Nynorsk","nn"},
        {"Sichuan Yi, Nuosu","ii"},
        {"Occitan","oc"},
        {"Ojibwa","oj"},
        {"Oriya","or"},
        {"Oromo","om"},
        {"Ossetian, Ossetic","os"},
        {"Pali","pi"},
        {"Pashto, Pushto","ps"},
        {"Persian","fa"},
        {"Polish","pl"},
        {"Portuguese","pt"},
        {"Punjabi, Panjabi","pa"},
        {"Quechua","qu"},
        {"Romanian, Moldavian, Moldovan","ro"},
        {"Romansh","rm"},
        {"Rundi","rn"},
        {"Russian","ru"},
        {"Northern Sami","se"},
        {"Samoan","sm"},
        {"Sango","sg"},
        {"Sanskrit","sa"},
        {"Sardinian","sc"},
        {"Serbian","sr"},
        {"Shona","sn"},
        {"Sindhi","sd"},
        {"Sinhala, Sinhalese","si"},
        {"Slovak","sk"},
        {"Slovenian","sl"},
        {"Somali","so"},
        {"Southern Sotho","st"},
        {"Spanish, Castilian","es"},
        {"Sundanese","su"},
        {"Swahili","sw"},
        {"Swati","ss"},
        {"Swedish","sv"},
        {"Tagalog","tl"},
        {"Tahitian","ty"},
        {"Tajik","tg"},
        {"Tamil","ta"},
        {"Tatar","tt"},
        {"Telugu","te"},
        {"Thai","th"},
        {"Tibetan","bo"},
        {"Tigrinya","ti"},
        {"Tonga (Tonga Islands)","to"},
        {"Tsonga","ts"},
        {"Tswana","tn"},
        {"Turkish","tr"},
        {"Turkmen","tk"},
        {"Twi","tw"},
        {"Uighur, Uyghur","ug"},
        {"Ukrainian","uk"},
        {"Urdu","ur"},
        {"Uzbek","uz"},
        {"Venda","ve"},
        {"Vietnamese","vi"},
        {"Volapük","vo"},
        {"Walloon","wa"},
        {"Welsh","cy"},
        {"Wolof","wo"},
        {"Xhosa","xh"},
        {"Yiddish","yi"},
        {"Yoruba","yo"},
        {"Zhuang, Chuang","za"},
        {"Zulu","zu"}
    };
        

    @BeforeAll 
    void setUp() {

        // Bank of generes to randomly select a genre from
        bankOfGenres.add(new Genre(1, "Horror"));
        bankOfGenres.add(new Genre(2, "Comedy"));
        bankOfGenres.add(new Genre(3, "Action"));
        bankOfGenres.add(new Genre(4, "Drama"));
        bankOfGenres.add(new Genre(5, "Romantic"));
        bankOfGenres.add(new Genre(6, "Thriller"));
        bankOfGenres.add(new Genre(7, "Anime"));
        bankOfGenres.add(new Genre(8, "SciFi"));
        bankOfGenres.add(new Genre(9, "Musical"));
        bankOfGenres.add(new Genre(10, "Documentary"));

        //  generate first fake data
        int id = 1;
        String title = "title";
        String originalTitle = "originalTitle";
        String overview = "overview";
        String tagline = "tagline";
        String status = "status";
        Genre[] genres = new Genre[2];
        genres[0] = new Genre(1, "Genre");
        genres[1] = new Genre(2, "Genre2");

        Calendar release = Calendar.getInstance();
        release.set(Calendar.YEAR, 2000);
        release.set(Calendar.WEEK_OF_YEAR, 1);
        release.set(Calendar.DAY_OF_YEAR, 1);
        release.set(Calendar.MONTH, 1);
        release.set(Calendar.DAY_OF_MONTH, 1);
        release.set(Calendar.WEEK_OF_MONTH, 1);
        release.set(Calendar.YEAR, 2000);
        release.set(Calendar.AM_PM, 1);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.HOUR, 0);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.MINUTE, 0);
        release.set(Calendar.SECOND, 0);
        release.set(Calendar.MILLISECOND, 0);

        long budget = 1;
        long revenue = 2;
        String[] languages = new String[2];
        languages[0] = "en";
        languages[1] = "th";
        String originalLanguage = "en";
        double runtime = 90.0;
        String homepage = "http://homepage.com/homepage";
        boolean adult = false;
        boolean video = false;
        String poster = "/uXDfjJbdP4ijW5hWSBrPrlKpxab.jpg";


        movies.add(id, title, originalTitle, overview, tagline, status,
        genres, release, budget, revenue, languages, originalLanguage,
        runtime, homepage, adult, video, poster);

        // add a second id for unit tests
        release = Calendar.getInstance();
        release.set(Calendar.YEAR, 2001);
        release.set(Calendar.WEEK_OF_YEAR, 1);
        release.set(Calendar.DAY_OF_YEAR, 1);
        release.set(Calendar.MONTH, 1);
        release.set(Calendar.DAY_OF_MONTH, 1);
        release.set(Calendar.WEEK_OF_MONTH, 1);
        release.set(Calendar.YEAR, 2000);
        release.set(Calendar.AM_PM, 1);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.HOUR, 0);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.MINUTE, 0);
        release.set(Calendar.SECOND, 0);
        release.set(Calendar.MILLISECOND, 0);
        

        movies.add(2, "Toy Story", "Toy Story", "Toy Story is a good movie", "Toy Story is a really good movie", "released",
        genres, release, budget, revenue, languages, originalLanguage,
        runtime, homepage, adult, video, poster);

        // generate lots of data for batch testing
       batchGeneration();

    }

    void batchGeneration(){
        System.out.println("Generating fake data");
        // add data to arraylists
        batchData(batchSize);
        // add data from arraylists to batchMovies
        for (int i =0; i<batchSize;i++){
            batchMovies.add(IDs.get(i), titles.get(i), originalTitles.get(i),overviews.get(i),
                taglines.get(i), statuses.get(i),genresList.get(i), releases.get(i), 
                budgets.get(i), revenues.get(i), languagesList.get(i), originalLanguages.get(i), 
                runtimes.get(i), homepages.get(i),adults.get(i),videos.get(i),posters.get(i));
        }

        
    }

    void batchData(int batchSize){
        
        Random random = new Random();
        int stringLength = 10;
        double upperRuntime = 180.0;
        double lowerRuntime = 70.0;
        double upperLong = 180.0;
        double lowerLong = 70.0;
        

        for (int j = 0; j<batchSize; j++)
        {
            // add the data to arraylists
            IDs.add(j);
            titles.add(randStringMaker(stringLength));
            originalTitles.add(randStringMaker(stringLength));
            overviews.add(randStringMaker(stringLength));
            taglines.add(randStringMaker(stringLength));
            statuses.add(randStringMaker(stringLength));
            genresList.add(randGenres());
            releases.add(randCalendar());    
            budgets.add((long) (random.nextLong() * (upperLong - lowerLong) + lowerLong));
            revenues.add((long) (random.nextLong() * (upperLong - lowerLong) + lowerLong));
            languagesList.add(randLanguages());
            originalLanguages.add(randStringMaker(stringLength));
            runtimes.add(random.nextDouble() * (upperRuntime - lowerRuntime) + lowerRuntime);
            homepages.add(randStringMaker(stringLength));
            adults.add(random.nextBoolean());
            videos.add(random.nextBoolean());
            posters.add(randStringMaker(stringLength));
            
        }
        // IDs are shuffled rather than randomly generated so that they are unique
        Collections.shuffle(IDs);

    }

    /**
     * @param stringLength how long the string should be
     * @return random string of stringlength
     */
    String randStringMaker(int stringLength){
        Random random = new Random(); 
        int lowerLimit = 97; // letter 'a'
        int upperLimit = 122; // letter 'z'
        
        String generatedString = random.ints(lowerLimit, upperLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /**
     * @return random Calendar date between 1950 and 2020
     */
    Calendar randCalendar(){
        Random random = new Random(); 
        
        Calendar release = Calendar.getInstance();
        release.set(Calendar.YEAR, random.nextInt(70)+1950);
        release.set(Calendar.WEEK_OF_YEAR, 1);
        release.set(Calendar.DAY_OF_YEAR, 1);
        release.set(Calendar.MONTH, 1);
        release.set(Calendar.DAY_OF_MONTH, 1);
        release.set(Calendar.WEEK_OF_MONTH, 1);
        release.set(Calendar.YEAR, 2000);
        release.set(Calendar.AM_PM, 1);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.HOUR, 0);
        release.set(Calendar.HOUR_OF_DAY, 0);
        release.set(Calendar.MINUTE, 0);
        release.set(Calendar.SECOND, 0);
        release.set(Calendar.MILLISECOND, 0);

        return release;
    }

    Genre[] randGenres(){
        Random random = new Random(); 
        // make 1 or 2 genres
        int numberOfGenres = random.nextInt(1)+1;
        
        Genre[] randGenres = new Genre[numberOfGenres];
        for (int i = 0; i < numberOfGenres; i++){
            randGenres[i] = bankOfGenres.get(random.nextInt(bankOfGenres.size()));
        }

        return randGenres;
    }

    String[] randLanguages(){
        Random random = new Random(); 
        // make 1 - 5 languages
        int numberOfLanguages = random.nextInt(4)+1;

        Set<String> randLanguages = new HashSet<String>();;
        
        
        // add random languages to set
        for (int i = 0; i < numberOfLanguages; i++){
            randLanguages.add(iso639Data[random.nextInt(iso639Data.length)][1]);
        }

        //  make array of unique values
        String[] uniqueRandLanguages = new String[randLanguages.size()];
        randLanguages.toArray(uniqueRandLanguages);

        return uniqueRandLanguages;
    }


    /**
     * Checks against default value.
     */
    @Test void testGetTitlePos() {

        System.out.println("\nStarting testGetTitlePos...");

        assertEquals("title", movies.getTitle(1), "Incorrect value returned.");

    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetTitleNeg() {
        

        System.out.println("\nStarting testGetTitleNeg...");

        assertNull( movies.getTitle(fakeID),"Getting values for a non existent ID should return null.");

    }

    /**
     * Check against batch generation.
     */
    @Test void testGetTitleAll() {

        System.out.println("\nStarting testGetTitleAll...");
        ArrayList<String> resultTitles = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultTitles.add(batchMovies.getTitle(IDs.get(i)));

        }

        assertEquals(titles, resultTitles, "Incorrect values returned.");
        
    }

    /**
     * Checks against default value.
     */
    @Test void testGetOriginalTitlePos() {

        System.out.println("\nStarting testGetOriginalTitlePos...");

        assertEquals("originalTitle", movies.getOriginalTitle(1), "Incorrect value returned.");
        
    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetOriginalTitleNeg() {

        System.out.println("\nStarting testGetOriginalTitleNeg...");

        assertNull(movies.getOriginalTitle(fakeID),"Getting values for a non existent ID should return null.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetOriginalTitleAll() {

        System.out.println("\nStarting testGetOriginalTitleAll...");
        ArrayList<String> resultOriginalTitles = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultOriginalTitles.add(batchMovies.getOriginalTitle(IDs.get(i)));

        }
        
        assertEquals(originalTitles, resultOriginalTitles, "Incorrect values returned.");

    }

    /**
     * Checks against default value.
     */
    @Test void testGetOverviewPos() {

        System.out.println("\nStarting testGetOverviewPos...");

        assertEquals("overview", movies.getOverview(1), "Incorrect value returned.");

    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetOverviewNeg() {

        System.out.println("\nStarting testGetOverviewNeg...");
 
        assertNull(movies.getOverview(fakeID), "Getting values for a non existent ID should return null.");

    }

    /**
     * Check against batch generation.
     */
    @Test void testGetOverviewAll() {

        System.out.println("\nStarting testGetOverviewAll...");
        ArrayList<String> resultOverview = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultOverview.add(batchMovies.getOverview(IDs.get(i)));

        }

        assertEquals(overviews, resultOverview, "Incorrect values returned.");
        
    }

    /**
     * Checks against default value.
     */
    @Test void testGetTaglinePos() {

        System.out.println("\nStarting testGetTaglinePos...");

        assertEquals("tagline", movies.getTagline(1), "Incorrect value returned.");

    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetTaglineNeg() {

        System.out.println("\nStarting testGetTaglineNeg...");
        
        assertNull( movies.getTagline(fakeID), "Getting values for a non existent ID should return null.");
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetTaglineAll() {

        System.out.println("\nStarting testGetTaglineAll...");
        ArrayList<String> resultTagline = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultTagline.add(batchMovies.getTagline(IDs.get(i)));

        }
        
        assertEquals(taglines, resultTagline, "Incorrect values returned.");
    }

    /**
     * Checks against default value.
     */
    @Test void testGetStatusPos() {

        System.out.println("\nStarting testGetStatusPos...");
        
        assertEquals("status", movies.getStatus(1), "Incorrect value returned.");
    
    }
    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetStatusNeg() {

        System.out.println("\nStarting testGetStatusNeg...");
        
        assertNull(movies.getStatus(fakeID), "Getting values for a non existent ID should return null.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetStatusAll() {

        System.out.println("\nStarting testGetStatusAll...");
        ArrayList<String> resultStatus = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultStatus.add(batchMovies.getStatus(IDs.get(i)));

        }
        
        assertEquals(statuses, resultStatus, "Incorrect values returned.");
        
    }

    /**
     * Checks same length and then if values are the same.
     */
    @Test void testGenrePos() {


        boolean allSame = true;

        Genre[] testGenres = movies.getGenres(1);
        Genre[] tmpGenres = new Genre[2];
        tmpGenres[0] = new Genre(1, "Genre");
        tmpGenres[1] = new Genre(2, "Genre2");
        System.out.println("\nStarting testGenrePos...");
        
        boolean tmpSame = false;

        assertEquals(tmpGenres.length, testGenres.length, "They are not the same length.");

        for (int i = 0; i < tmpGenres.length; i++){
            System.out.println(tmpGenres[i].compareTo(testGenres[i]));
            tmpSame = (0 == tmpGenres[i].compareTo(testGenres[i]));
            if (!tmpSame){
                allSame = false;
            }
        }
        System.out.println(movies.getGenres(1));
        
        assertTrue(allSame, "Incorrect values returned.");
  
    }

    /**
    * Check against batch generation.
    */
    @Test void testGetGenreAll() {

        System.out.println("\nStarting testGetGenreAll...");
        ArrayList<Genre[]> resultGenre = new ArrayList<Genre[]>();

        for (int i = 0; i< batchSize; i++){
            resultGenre.add(batchMovies.getGenres(IDs.get(i)));

        }
        
        assertEquals(genresList, resultGenre, "Incorrect values returned.");
        
    }


    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGenreNegID() {

        System.out.println("\nStarting testGenreNeg...");
        
        assertNull(movies.getGenres(fakeID), "Getting values for a non existent ID should return null.");
        
    }

    /**
     * Checks against default value using compareTo which should be 0.
     */
    @Test void testGetReleasePos() {

        System.out.println("\nStarting testGetReleasePos...");

        Calendar tmpRelease = Calendar.getInstance();
        tmpRelease.set(Calendar.YEAR, 2000);
        tmpRelease.set(Calendar.WEEK_OF_YEAR, 1);
        tmpRelease.set(Calendar.DAY_OF_YEAR, 1);
        tmpRelease.set(Calendar.MONTH, 1);
        tmpRelease.set(Calendar.DAY_OF_MONTH, 1);
        tmpRelease.set(Calendar.WEEK_OF_MONTH, 1);
        tmpRelease.set(Calendar.YEAR, 2000);
        tmpRelease.set(Calendar.AM_PM, 1);
        tmpRelease.set(Calendar.HOUR_OF_DAY, 0);
        tmpRelease.set(Calendar.HOUR, 0);
        tmpRelease.set(Calendar.HOUR_OF_DAY, 0);
        tmpRelease.set(Calendar.MINUTE, 0);
        tmpRelease.set(Calendar.SECOND, 0);
        tmpRelease.set(Calendar.MILLISECOND, 0);

        assertEquals(0, movies.getRelease(1).compareTo(tmpRelease), "Incorrect value returned.");
        
    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetReleaseNegVal() {

        System.out.println("\nStarting testGetReleaseNeg...");
        
        assertNull( movies.getRelease(fakeID), "Getting values for a non existent ID should return null.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetReleaseAll() {

        System.out.println("\nStarting testGetReleaseAll...");
        ArrayList<Calendar> resultRelease = new ArrayList<Calendar>();

        for (int i = 0; i< batchSize; i++){
            resultRelease.add(batchMovies.getRelease(IDs.get(i)));

        }
        
        assertEquals(releases, resultRelease, "Does not return the correct values.");
       
    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetReleaseNeg() {

        System.out.println("\nStarting testGetReleaseNeg...");
        
        assertNull( movies.getRelease(fakeID),"Getting values for a non existent ID should return null.");
        
    }

    /**
     * Compares value of 1 to value returned by getBudget.
     */
    @Test void testGetBudgetPos() {

        System.out.println("\nStarting testGetBudgetPos...");

        System.out.println(movies.getBudget(1));
        
        assertEquals(1, movies.getBudget(1), "Does not return the correct value.");
        
    }
    /**
     * When supplied with a non existent ID, function should return -1
     */
    @Test void testGetBudgetNeg() {

        System.out.println("\nStarting testGetBudgetNeg...");
        
        assertEquals(-1, movies.getBudget(fakeID), "When supplied with a non existent ID should return -1");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetBudgetAll() {

        System.out.println("\nStarting testGetBudgetAll...");
        ArrayList<Long> resultBudget = new ArrayList<Long>();

        for (int i = 0; i< batchSize; i++){
            resultBudget.add(batchMovies.getBudget(IDs.get(i)));

        }
        
        assertEquals(budgets, resultBudget, "Does not return the correct values.");
       
    }

    /**
     * Checks against default value.
     */
    @Test void testGetRevenuePos() {

        System.out.println("\nStarting testGetRevenuePos...");

        assertEquals(2, movies.getRevenue(1), "Does not return the correct value.");
        
    }

    /**
     * Getting values for a non existent ID should return -1.
     */
    @Test void testGetRevenueNeg() {

        System.out.println("\nStarting testGetRevenueNeg...");

        assertEquals(-1, movies.getRevenue(fakeID), "Getting values for a non existent ID should return -1.");
       
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetRevenueAll() {

        System.out.println("\nStarting testGetRevenueAll...");
        ArrayList<Long> resultRevenue = new ArrayList<Long>();

        for (int i = 0; i< batchSize; i++){
            resultRevenue.add(batchMovies.getRevenue(IDs.get(i)));

        }
        
        assertEquals(revenues, resultRevenue, "Does not return the correct values.");

    }

    /**
     * Checks against default value.
     */
    @Test void testGetLanguagesPos(){
        System.out.println("\nStarting testGetLanguagesPos");
        String[] languages = new String[2];
        languages[0] = "en";
        languages[1] = "th";

        assertArrayEquals(languages, movies.getLanguages(1),"Does not return the correct values.");

    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetLanguagesNeg(){
        System.out.println("\nStarting testGetLanguagesNeg");

        assertNull(movies.getLanguages(fakeID), "Getting values for a non existent ID should return null.");
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetLanguagesMany(){
        System.out.println("\nStarting testGetLanguagesMany");
        ArrayList<String[]> resultLanguage = new ArrayList<String[]>();

        for (int i = 0; i< batchSize; i++){
            resultLanguage.add(batchMovies.getLanguages(IDs.get(i)));

        }
        
        assertEquals(languagesList, resultLanguage, "Does not return the correct values.");

    }
    

    /**
     * Checks against default value.
     */
    @Test void testGetOriginalLanguagePos() {

        System.out.println("\nStarting testGetOriginalLanguagePos...");

        assertEquals("en", movies.getOriginalLanguage(1), "Does not return the correct value.");
        
    }

    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetOriginalLanguageNeg() {

        System.out.println("\nStarting testGetOriginalLanguageNeg...");


        assertNull(movies.getOriginalLanguage(fakeID), "Getting values for a non existent ID should return null.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetOriginalLanguageAll() {

        System.out.println("\nStarting testGetOriginalLanguageAll...");
        ArrayList<String> resultOriginalLanguage = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultOriginalLanguage.add(batchMovies.getOriginalLanguage(IDs.get(i)));

        }
        
        assertEquals(originalLanguages, resultOriginalLanguage, "Does not return the correct values.");
        
    }

    /**
     * Checks against default value.
     */
    @Test void testGetRuntimePos() {

        System.out.println("\nStarting testGetRuntimePos...");

        assertEquals(90.0, movies.getRuntime(1), "Does not return the correct value.");
        
    }

    /**
     * Getting values for a non existent ID should return -1.
     */
    @Test void testGetRuntimeNeg() {

        System.out.println("\nStarting testGetRuntimeNeg...");

        assertEquals(-1, movies.getRuntime(fakeID), "Getting values for a non existent ID should return -1.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetRuntimeAll() {

        System.out.println("\nStarting testGetRuntimeAll...");
        ArrayList<Double> resultRuntime = new ArrayList<Double>();

        // System.out.println(movies.getTitle(1));
        for (int i = 0; i< batchSize; i++){
            resultRuntime.add(batchMovies.getRuntime(IDs.get(i)));

        }
        
        assertEquals(runtimes, resultRuntime, "Does not return the correct values.");
        
    }

    /**
     * Checks against default value.
     */
    @Test void testGetHomepagePos() {

        System.out.println("\nStarting testGetHomepagePos...");

        System.out.println(movies.getHomepage(1));
        
        assertEquals("http://homepage.com/homepage", movies.getHomepage(1), "Does not return the correct value.");
        
    }
    
    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetHomepageNeg() {

        System.out.println("\nStarting testGetHomepageNeg...");

        assertNull(movies.getHomepage(fakeID),"Getting values for a non existent ID should return null.");
        
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetHomepageAll() {

        System.out.println("\nStarting testGetHomepageAll...");
        ArrayList<String> resultHomepage = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultHomepage.add(batchMovies.getHomepage(IDs.get(i)));

        }
    
        assertEquals(homepages, resultHomepage);
        
    }

    /**
     * Checks against default value.
     */
    @Test void testAdultPos() {

        System.out.println("\nStarting testAdultPos...");
        
        assertFalse(movies.getAdult(1));
        
    }

    /**
     * Getting values for a non existent ID should return false.
     */
    @Test void testGetAdultNeg() {

        System.out.println("\nStarting testGetAdultNeg...");

        assertEquals(false, movies.getAdult(fakeID), "Getting values for a non existent ID should return false.");

    }

    /**
     * Check against batch generation.
     */
    @Test void testGetAdultAll() {

        System.out.println("\nStarting testGetAdultAll...");
        ArrayList<Boolean> resultAdult = new ArrayList<Boolean>();

        
        for (int i = 0; i< batchSize; i++){
            resultAdult.add(batchMovies.getAdult(IDs.get(i)));

        }
        
        assertEquals(adults, resultAdult);
        
    }

    /**
     * Checks against default value.
     */
    @Test void testGetVideoPos() {

        System.out.println("\nStarting testGetVideoPos...");
        
        assertEquals(false, movies.getVideo(1));
        
    }

    /**
     * Getting values for a non existent ID should return false.
     */
    @Test void testGetVideoNeg() {

        System.out.println("\nStarting testGetVideoNeg...");

        assertFalse(movies.getVideo(fakeID), "Getting values for a non existent ID should return false.");
    
    }

    /**
     * Check against batch generation.
     */
    @Test void testGetVideoAll() {

        System.out.println("\nStarting testGetVideoAll...");
        ArrayList<Boolean> resultVideo = new ArrayList<Boolean>();

        for (int i = 0; i< batchSize; i++){
            resultVideo.add(batchMovies.getVideo(IDs.get(i)));

        }
        
        assertEquals(videos, resultVideo);

    }

    /**
     * Checks against default value.
     */
    @Test void testGetPosterPos() {

        System.out.println("\nStarting testGetPosterPos...");

        assertEquals("/uXDfjJbdP4ijW5hWSBrPrlKpxab.jpg", movies.getPoster(1));
        
    }
    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetPosterNeg() {

        System.out.println("\nStarting testGetPosterNeg...");

        System.out.println(movies.getPoster(fakeID));
        
        assertNull(movies.getPoster(fakeID), "Getting values for a non existent ID should return null.");

    }

    /**
     * Check against batch generation.
     */
    @Test void testGetPosterAll() {

        System.out.println("\nStarting testGetPosterAll...");
        ArrayList<String> resultPoster = new ArrayList<String>();

        for (int i = 0; i< batchSize; i++){
            resultPoster.add(batchMovies.getPoster(IDs.get(i)));

        }

        assertEquals(posters, resultPoster, "Incorrect values returned.");

    }

    /**
     * Set and get IMDB.
     */
    @Test void testGetIMDBPos() {

        System.out.println("\nStarting testGetIMDBPos...");
        movies.setIMDB(1,"IMDBid");
        
        assertEquals("IMDBid", movies.getIMDB(1), "Incorrect value returned.");

    }
    
    /**
     * Getting values for a non existent ID should return null.
     */
    @Test void testGetIMDBNeg() {

        System.out.println("\nStarting testGetIMDBNeg...");
        
        assertNull(movies.getIMDB(fakeID), "Getting values for a non existent ID should return null.");

    }

    /**
     * Setting a value for a valid ID should return true.
     */
    @Test void testSetIMDBPos() {

        System.out.println("\nStarting testGetPosterPos...");

        assertTrue(movies.setIMDB(1, "changedIMDBid"), "Setting a value for a valid ID should return true.");

    }

    /**
     * Setting a value for a non existent ID should return false.
     */
    @Test void testSetIMDBNeg() {

        System.out.println("\nStarting testSetIMDBNeg...");

        assertFalse(movies.setIMDB(fakeID, "changedIMDBid"), "Setting a value for a non existent ID should return false.");

    }

    /**
     * Setting a value for a valid ID should return true.
     */
    @Test void testSetVotePos() {

        System.out.println("\nStarting testSetVotePos...");

        assertTrue(movies.setVote(1, 2.2, 2000), "Setting a value for a valid ID should return true.");

    }
    
    /**
     * Setting a value for a non existent ID should return false.
     */
    @Test void testSetVoteNeg() {

        System.out.println("\nStarting testSetVoteNeg...");

        assertFalse(movies.setVote(fakeID, 3.3, 3000), "Setting a value for a non existent ID should return false.");

    }

    /**
     * Set and get value.
     */
    @Test void testGetVoteCountPos(){
        double voteAverage = 1.1;
        int voteCount = 1000;

        System.out.println("\nStarting testGetVoteCountPos...");

        movies.setVote(1, voteAverage, voteCount);
        
        assertEquals(voteCount, movies.getVoteCount(1), "Incorrect value returned.");

    }

    /**
     * Getting values for a non existent ID should return -1.
     */
    @Test void testGetVoteCountNeg(){

        System.out.println("\nStarting testGetVoteCountNeg...");
        
        assertEquals(-1, movies.getVoteCount(fakeID), "Getting values for a non existent ID should return -1.");
        
    }

    /**
     * Sets and then gets value.
     */
    @Test void testGetVoteAveragePos(){

        double voteAverage = 1.1;
        int voteCount = 1000;

        System.out.println("\nStarting testGetVoteAveragePos...");

        movies.setVote(1, voteAverage, voteCount);
        
        assertEquals(voteAverage, movies.getVoteAverage(1), "Incorrect value returned.");

    }

    /**
     * Getting values for a non existent ID should return -1.
     */
    @Test void testGetVoteAverageNeg(){

        System.out.println("\nStarting testGetVoteAverageNeg...");
        
        assertEquals(-1, movies.getVoteAverage(fakeID), "Getting values for a non existent ID should return -1.");
        
    }

    /**
     * Adding a valid movie should return true.
     */
    @Test void testAddProductionCountryPos() {

        System.out.println("\nStarting testAddProductionCountryPos...");

        assertTrue(movies.addProductionCountry(1, "GB"), "Adding a valid movie should return true.");

    }
    
    /**
     * Adding a production country to a non existent movie should return false.
     */
    @Test void testAddProductionCountryNeg() {

        System.out.println("\nStarting testAddProductionCountryNeg...");

        assertFalse(movies.addProductionCountry(fakeID, "GB"), "Adding a production country to a non existent movie should return false.");

    }
    
    /**
     * Test if get works directly after adding for ID 2.
     */
    @Test void testGetProductionCountryPos(){
        String[] tmpCountries = {"GB", "YE"};

        System.out.println("\nStarting testGetProductionCountryPos...");

        movies.addProductionCountry(2, tmpCountries[0]);
        movies.addProductionCountry(2, tmpCountries[1]);
        
        assertArrayEquals(tmpCountries, movies.getProductionCountries(2), "Values dont match.");
        
    }

    /**
     * Non existent ID input should return null.
     */
    @Test void testGetProductionCountriesNeg(){

        String[] tmpCountries = {"GB", "YE"};

        System.out.println("\nStarting testGetProductionCountriesNeg...");

        // Other values added just ot make sure it doesn't catch these.
        movies.addProductionCountry(1, tmpCountries[0]);
        movies.addProductionCountry(1, tmpCountries[1]);
        
        assertNull(movies.getProductionCountries(fakeID), "Non existent ID input should return null.");
        
    }

    /**
     * Should return true if successfully added.
     */
    @Test void testAddProductionCompanyPos() {

        System.out.println("\nStarting testAddProductionCompanyPos...");

        assertTrue( movies.addProductionCompany(1, new Company(1,
        "Pixar Animation Studios")), "Should return true if successfully added.");

    }
    
    /**
     * Invalid add should return false.
     */
    @Test void testAddProductionCompanyNeg() {

        System.out.println("\nStarting testAddProductionCompanyNeg...");

        assertFalse(movies.addProductionCompany(fakeID, new Company(1,
        "Pixar Animation Studios")), "Invalid add should return false.");

    }

    /**
     * Check that companies returned are correct.
     */
    @Test void testGetProductionCompanyPos() {

        Company[] tmpCompanies = {new Company(1,
            "Pixar Animation Studios"),new Company(2,
            "Pixar Animation Studios2")};

        System.out.println("\nStarting testGetProductionCompanyPos...");
        movies.addProductionCompany(2, tmpCompanies[0]);
        movies.addProductionCompany(2, tmpCompanies[1]);


        Company[] testCompanies = movies.getProductionCompanies(2);

        assertArrayEquals(tmpCompanies, testCompanies, "Incorrect result.");
        
    }
    

    /**
     * Invalid film ID supplied should return null.
     */
    @Test void testGetProductionCompanyNeg() {
        
        Company[] tmpCompanies = {new Company(1,
            "Pixar Animation Studios"),new Company(2,
            "Pixar Animation Studios2")};

        System.out.println("\nStarting testGetProductionCompanyNeg...");
        movies.addProductionCompany(1, tmpCompanies[0]);
        movies.addProductionCompany(1, tmpCompanies[1]);

        assertNull( movies.getProductionCompanies(fakeID), "Invalid film ID supplied should return null.");

    }

    /**
     * A valid add should return true.
     */
    @Test void testAddToCollectionPos (){

        System.out.println("\nStarting testAddToCollectionPos...");
        
        int filmID = 1; 
        int collectionID = 1; 
        String collectionName = "Toy Story Series";
        String collectionPosterPath = "collectionposter";
        String collectionBackdropPath = "collectionbackdrop";

        assertTrue(movies.addToCollection(filmID, collectionID, collectionName, collectionPosterPath, collectionBackdropPath), "A valid add should return true.");

    }

    /**
     * A non existent film ID should not add and then return false.
     */
    @Test void testAddToCollectionNeg (){

        System.out.println("\nStarting testAddToCollectionNeg...");
        
        
        int collectionID = 1; 
        String collectionName = "Toy Story Series";
        String collectionPosterPath = "collectionposter";
        String collectionBackdropPath = "collectionbackdrop";

        assertFalse(movies.addToCollection(fakeID, collectionID, collectionName, collectionPosterPath, collectionBackdropPath), "A non existent film ID should not add and then return false.");

    }

    /**
     * Expects "Toy Story Series" for collectionID 1.
     */
    @Test void testGetCollectionNamePos(){

        System.out.println("\nStarting testGetCollectionNamePos...");

        assertEquals("Toy Story Series", movies.getCollectionName(1), "Incorrect value returned.");
    }

    /**
     * Non existent ID should return null.
     */
    @Test void testGetCollectionNameNeg(){

        System.out.println("\nStarting testGetCollectionNameNeg...");

        assertNull(movies.getCollectionName(fakeID), "Non existent ID should return null.");
    }

    /**
     * Expects "collectionposter" for collectionID 1.
     */
    @Test void testGetCollectionPosterPos(){
        System.out.println("\nStarting testGetCollectionPosterPos...");

        assertEquals("collectionposter", movies.getCollectionPoster(1), "Incorrect value returned.");
    }

    /**
     * Non existent ID should return null.
     */
    @Test void testGetCollectionPosterNeg(){
        System.out.println("\nStarting testGetCollectionPosterNeg...");

        assertNull(movies.getCollectionPoster(fakeID), "Non existent ID should return null.");
    }

    /**
     * Expects "collectionbackdrop" for collectionID 1.
     */
    @Test void testGetCollectionBackdropPos(){
        System.out.println("\nStarting testGetCollectionBackdropPos...");

        assertEquals("collectionbackdrop", movies.getCollectionBackdrop(1), "Incorrect value returned.");
    }

    /**
     * Non existent ID should return null.
     */
    @Test void testGetCollectionBackdropNeg(){
        System.out.println("\nStarting testGetCollectionBackdropNeg...");

        assertNull(movies.getCollectionBackdrop(fakeID), "Non existent ID should return null.");
    }

    /**
     * Expects ID 1 for filmID 1.
     */
    @Test void testGetCollectionIDPos(){
        System.out.println("\nStarting testGetCollectionIDPos...");

        assertEquals(1, movies.getCollectionID(1), "Incorrect ID returned.");
    }

    /**
     * If a film does not have a collection -1 should be returned.
     */
    @Test void testGetCollectionIDNeg(){
        System.out.println("\nStarting testGetCollectionIDNeg...");

        assertEquals(-1, movies.getCollectionID(fakeID), "If a film does not have a collection -1 should be returned.");
    }

    /**
     * Should return Toy Story movie with ID 2.
     */
    @Test void testfindFilmsPos(){
        System.out.println("\nStarting testfindFilmsPos...");
        int[] toyFilms = {2};
        assertArrayEquals(toyFilms, movies.findFilms("Toy"), "Could not find a valid film.");

    }

    /**
     * If there are no films matching string there should be an empty array.
     */
    @Test void testfindFilmsNeg(){
        System.out.println("\nStarting testfindFilmsNeg...");
        int[] emptyFilms = {};
        assertArrayEquals(emptyFilms, movies.findFilms("Value returned when there are no valid matches."));

    }

    /**
     * Compare size to 2.
     */
    @Test void testSize(){
        System.out.println("\nStarting testSize...");

        assertEquals(2, movies.size(), "Size not equal.");

    }
 
}
