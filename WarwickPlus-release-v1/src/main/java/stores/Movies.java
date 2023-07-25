package stores;

import java.util.Calendar;

import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies {

    // Stores movieID as key and Movie as value
    private HashMap<Integer, Movie> movieData;

    // Stores movieID as key and it's Collection class as value (only stored if movie has a collection)
    private HashMap<Integer, Collection> collectionData;

    // ArrayList containing all unique movie IDs.
    private ArrayList<Integer> uMovies;

    // Movie class stores all metadata about a movie
    class Movie{
        public String title;
        public String originalTitle;
        public String overview;
        public String tagline;
        public String status;
        public Genre[] genres;
        public Calendar release;
        public long budget;
        public long revenue;
        public String[] languages;
        public String originalLanguage;
        public double runtime;
        public String homepage;
        public boolean adult;
        public boolean video;
        public String poster;

        public double voteAverage;
        public int voteCount;

        public int collectionID;

        public String imdbID;

        public double popularity;

        public ArrayList<Company> companies;

        public ArrayList<String> countries;

        Movie(String title, String originalTitle, String overview, String tagline, String status,
        Genre[] genres, Calendar release, long budget, long revenue, String[] languages, String originalLanguage,
        double runtime, String homepage, boolean adult, boolean video, String poster){
            this. title = title;
            this.originalTitle = originalTitle;
            this.overview = overview;
            this.tagline = tagline;
            this.status = status;
            this.genres = genres;
            this.release = release;
            this.budget = budget;
            this.revenue = revenue;
            this.languages = languages;
            this.originalLanguage = originalLanguage;
            this.runtime = runtime;
            this.homepage = homepage;
            this.adult = adult;
            this.video = video;
            this.poster = poster;

            this.collectionID = -1;

            companies = new ArrayList<Company>();
            countries = new ArrayList<String>();
        }
    }

    // Collection class stores all metadata about a collection
    class Collection{
        public String collectionName;
        public String collectionPosterPath;
        public String collectionBackdropPath;

        Collection(String collectionName, String collectionPosterPath, String collectionBackdropPath){
            this.collectionName = collectionName;
            this.collectionPosterPath = collectionPosterPath;
            this.collectionBackdropPath = collectionBackdropPath;
        }
    }


    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     */
    public Movies() {
        movieData = new HashMap<Integer, Movie>();
        collectionData = new HashMap<Integer, Collection>();
        uMovies = new ArrayList<>();
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status,
            Genre[] genres, Calendar release, long budget, long revenue, String[] languages, String originalLanguage,
            double runtime, String homepage, boolean adult, boolean video, String poster) {
        
        // Create new Movie class and add to HashMap using id.
        Movie movie = new Movie(title, originalTitle, overview, tagline, status, genres, release, budget, revenue,
                        languages, originalLanguage, runtime, homepage, adult, video, poster);

        movieData.put(id, movie);

        if(!uMovies.contains(id)) uMovies.add(id);

        return true;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        
        if(movieData.get(id) == null) return false;

        movieData.put(id, null);

        uMovies.removeIndex(uMovies.indexOf(id));

        return true;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(Calendar start, Calendar end) {
        ArrayList<Integer> arrList = new ArrayList<Integer>();

        // Go through uMovies ArrayList to get all movie ID's and access Movie class via HashMap
        // Then, check if release is between start and end dates
        for(int i = 0; i < uMovies.size(); i++){
            Movie movie = movieData.get(uMovies.get(i));
            if(movie == null || movie.release == null){
                continue;
            }
            Calendar currTime = movie.release;
            if(currTime.after(start) && currTime.before(end)){
                arrList.add(uMovies.get(i));
            }
        }
        
        int[] returnArr = new int[arrList.size()]; // convert array list to array
        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = arrList.get(i);
        }

        return returnArr;
    }

    /**
     * Finds the film IDs of all films released within a given range and within a
     * given range of budget. If a film is
     * released either on the start or end dates, then that film should not be
     * included. If a film has a budgets exactly the same as the lower or upper
     * bounds, then this film should not be included
     * 
     * @param start       The start point of the range of dates
     * @param end         The end point of the range of dates
     * @param lowerBudget The lowest bound of the range for budgets
     * @param upperBudget The upper bound of the range of budgets
     * @return An array of film IDs that were released between start and end, and
     *         had a budget between lowerBudget and upperBudget
     */
    @Override
    public int[] getAllIDsReleasedInRangeAndBudget(Calendar start, Calendar end, long lowerBudget, long upperBudget) {
        
        // Use previous function to find released in range
        int[] inRangeArr = getAllIDsReleasedInRange(start, end);

        ArrayList<Integer> arrList = new ArrayList<Integer>();

        // Repeat but checking budget is in between limits
        for(int i = 0; i < inRangeArr.length; i++){
            long budget = movieData.get(inRangeArr[i]).budget;
            if(budget > lowerBudget && budget < upperBudget){
                arrList.add(inRangeArr[i]);
            }
        }
        
        int[] returnArr = new int[arrList.size()]; // convert array list to array
        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = arrList.get(i);
        }

        return returnArr;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).title;
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).originalTitle;
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).overview;
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).tagline;
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).status;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).genres;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public Calendar getRelease(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).release;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        if(movieData.get(id) == null) return -1;
        return movieData.get(id).budget;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        if(movieData.get(id) == null) return -1;
        return movieData.get(id).revenue;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).languages;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).originalLanguage;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public double getRuntime(int id) {
        if(movieData.get(id) == null) return -1;
        return movieData.get(id).runtime;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).homepage;
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        if(movieData.get(id) == null) return false;
        return movieData.get(id).adult;

    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        if(movieData.get(id) == null) return false;
        return movieData.get(id).video;
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        if(movieData.get(id) == null) return null;
        return movieData.get(id).poster;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        Movie m = movieData.get(id);

        if(m == null) return false;

        m.voteAverage = voteAverage;
        m.voteCount = voteCount;

        return true;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public double getVoteAverage(int id) {
        Movie m = movieData.get(id);

        if(m == null) return -1;

        return m.voteAverage;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        Movie m = movieData.get(id);

        if(m == null) return -1;

        return m.voteCount;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath,
            String collectionBackdropPath) {
        
        Movie m = movieData.get(filmID);

        if(m == null) return false; // If movie doesn't exist, return false

        // Set collection ID in movie and create new Collection class
        m.collectionID = collectionID;

        if(collectionData.get(collectionID) != null) return true; // If collection already exists, no need to initialise a new one

        // Create new collection class with info
        Collection c = new Collection(collectionName, collectionPosterPath, collectionBackdropPath);

        // Store collection class in hashmap
        collectionData.put(collectionID, c);

        return true;
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        Collection c = collectionData.get(collectionID);

        if(c == null) return null;

        return c.collectionName;
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        Collection c = collectionData.get(collectionID);

        if(c == null) return null;

        return c.collectionPosterPath;
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        Collection c = collectionData.get(collectionID);

        if(c == null) return null;

        return c.collectionBackdropPath;
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        Movie m = movieData.get(filmID);

        if(m == null) return -1;

        return m.collectionID;
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        Movie m = movieData.get(filmID);

        if(m == null) return false;

        m.imdbID = imdbID;
        return true;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        Movie m = movieData.get(filmID);

        if(m == null) return null;

        return m.imdbID;
    }

    /**
     * Sets the popularity of a given film
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        Movie m = movieData.get(id);

        if(m == null) return false;

        m.popularity = popularity;
        return true;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public double getPopularity(int id) {
        Movie m = movieData.get(id);

        if(m == null) return -1;

        return m.popularity;
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        Movie m = movieData.get(id);

        if(m == null) return false;

        m.companies.add(company);
        return true;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        Movie m = movieData.get(id);

        if(m == null) return false;

        m.countries.add(country);
        return true;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        
        Movie m = movieData.get(id);

        if(m == null) return null;

        Company[] c = new Company[m.companies.size()];

        for(int i = 0; i < c.length; i++){
            c[i] = m.companies.get(i);
        }

        return c;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        Movie m = movieData.get(id);

        if(m == null) return null;

        String[] c = new String[m.countries.size()];

        for(int i = 0; i < c.length; i++){
            c[i] = m.countries.get(i);
        }

        return c;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return uMovies.size();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {

        ArrayList<Integer> matchingIDs = new ArrayList<Integer>();

        // Go through all movies and compare titles to search term (case insensitive)
        for(int i = 0; i < uMovies.size(); i++){
            int m = uMovies.get(i);
            Movie d = movieData.get(m);
            String search = searchTerm.toLowerCase();
            if(d.title.toLowerCase().contains(search) || d.originalTitle.toLowerCase().contains(search) || d.overview.toLowerCase().contains(search)){
                matchingIDs.add(m);
            }
        }

        if(matchingIDs.size() == 0) return new int[0]; // If no matches return empty array

        // Convert array list to array
        int[] returnArr = new int[matchingIDs.size()];

        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = matchingIDs.get(i);
        }

        return returnArr;
    }
}
