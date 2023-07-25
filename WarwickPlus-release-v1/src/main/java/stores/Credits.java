package stores;

import interfaces.ICredits;
import structures.*;

public class Credits implements ICredits {

    HashMap<Integer, Film> filmData; // Hashmap containing film ID as key and Film class as value
    HashMap<Integer, CastData> castData; // Hashmap containing cast ID as key and CastData class as value
    HashMap<Integer, CrewData> crewData; // Hashmap containing crew ID as key and CrewData class as value

    // ArrayLists containing all unique IDs of films, cast members, and crew members respectively
    ArrayList<Integer> uFilms, uCast, uCrew;

    // Graph containing the links between cast members based on movies they starred in
    // Cast members in the same movies will have a link meaning they are adjacent in the graph
    // Used for findDistance function
    MyGraph castConnections = new MyGraph();

    // Film class contains information on the cast and crew for each film
    class Film{
        private Cast[] cast;
        private Crew[] crew;

        Film(Cast[] cast, Crew[] crew){
            this.cast = cast;
            this.crew = crew;
        }

        public Cast[] getCast(){
            return cast;
        }

        public Crew[] getCrew(){
            return crew;
        }

    }

    // CastData contains Cast class reference as well as an ArrayList of all movies starred in
    class CastData{
        private Cast cast;
        private ArrayList<Integer> movieIDs;
        
        CastData(Cast cast){
            this.cast = cast;
            movieIDs = new ArrayList<Integer>();
        }

        public Cast getInfo(){
            return cast;
        }

        public void addMovie(int id){
            if(!movieIDs.contains(id)) movieIDs.add(id);
        }

        public void removeMovie(int id){
            movieIDs.removeIndex(movieIDs.indexOf(id));
        }

        public ArrayList<Integer> getMovieIDs(){
            return movieIDs;
        }

    }

    // CrewData contains Crew class reference as well as an ArrayList of all movies involved in
    class CrewData{
        private Crew crew;
        private ArrayList<Integer> movieIDs;
        
        CrewData(Crew crew){
            this.crew = crew;
            movieIDs = new ArrayList<Integer>();
        }

        public Crew getInfo(){
            return crew;
        }

        public void addMovie(int id){
            if(!movieIDs.contains(id)) movieIDs.add(id);
        }

        public void removeMovie(int id){
            movieIDs.removeIndex(movieIDs.indexOf(id));
        }

        public ArrayList<Integer> getMovieIDs(){
            return movieIDs;
        }
        
    }

    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     */
    public Credits() {
        filmData = new HashMap<Integer, Film>();
        castData = new HashMap<Integer, CastData>();
        crewData = new HashMap<Integer, CrewData>();

        uFilms = new ArrayList<Integer>();
        uCast = new ArrayList<Integer>();
        uCrew = new ArrayList<Integer>();
    }

    /**
     * Adds data about the people who worked on a given film
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(Cast[] cast, Crew[] crew, int id) {

        // Create new Film class
        Film film = new Film(cast, crew);
        filmData.put(id, film);

        if(!uFilms.contains(id)){
            uFilms.add(id);
        }

        // get all ids from cast array to add to graph
        int[] castIDs = new int[cast.length];
        for(int i = 0; i < castIDs.length; i++){
            castIDs[i] = cast[i].getID();
        }

        // Iterate through cast and add to castData hashmap
        for(Cast c : cast){
            if(castData.get(c.getID()) == null){ // Doesnt exist, create new
                CastData cData = new CastData(c);
                cData.addMovie(id);
                castData.put(c.getID(), cData);
                uCast.add(c.getID());
            } else {
                castData.get(c.getID()).addMovie(id);
            }

            // Add cast to graph
            castConnections.addNode(c.getID(), castIDs);
        }

        // Iterate through crew and add to crewData hashmap
        for(Crew c : crew){
            if(crewData.get(c.getID()) == null){ // Doesnt exist, create new
                CrewData cData = new CrewData(c);
                cData.addMovie(id);
                crewData.put(c.getID(), cData);
                uCrew.add(c.getID());
            } else {
                crewData.get(c.getID()).addMovie(id);
            }
        }

        return true;
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        if(filmData.get(id) == null) return false;

        Film fData = filmData.get(id);

        castConnections.removeNode(id);

        for(Cast c : fData.getCast()){
            CastData cData = castData.get(c.getID());
            cData.removeMovie(id);
            if(cData.getMovieIDs().size() == 0){ // remove from unique cast list if only movie is removed
                int cid = cData.getInfo().getID();
                uCast.removeIndex(uCast.indexOf(cid));
                castData.put(cid, null); // remove from hashmap
            }
        }

        for(Crew c : fData.getCrew()){
            CrewData cData = crewData.get(c.getID());
            cData.removeMovie(id);
            if(cData.getMovieIDs().size() == 0){ // remove from unique crew list if only movie is removed
                int cid = cData.getInfo().getID();
                uCrew.removeIndex(uCrew.indexOf(cid));
                crewData.put(cid, null); // remove from hashmap
            }
        }

        uFilms.removeIndex(uFilms.indexOf(id));
        
        return true;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs
     */
    @Override
    public int[] getFilmIDs() {
        // Convert uFilms to array
        int[] fids = new int[uFilms.size()];

        for(int i = 0; i < fids.length; i++){
            fids[i] = uFilms.get(i);
        }

        return fids;
    }

    /**
     * Gets all the films worked on by a given cast ID (not cast element ID)
     * 
     * @param castID The ID of the cast member to be found
     * @return An array of film IDs relating to all films worked on by the requested
     *         cast member. If the cast member cannot be found, then return null
     */
    @Override
    public int[] getFilmIDsFromCastID(int castID) {
        // Use cast getMovieIds and convert to array
        CastData c = castData.get(castID);

        if(c == null) return new int[0];

        int[] idArr = new int[c.getMovieIDs().size()];

        for(int i = 0; i < idArr.length; i++){
            idArr[i] = c.getMovieIDs().get(i);
        }

        return idArr;
    }

    /**
     * Gets all the films worked on by a given crew ID (not crew element ID)
     * 
     * @param crewID The ID of the cast member to be found
     * @return An array of film IDs relating to all films worked on by the requested
     *         crew member. If the crew member cannot be found, then return null
     */
    @Override
    public int[] getFilmIDsFromCrewID(int crewID) {
        // Use crew getMovieIds and convert to array
        CrewData c = crewData.get(crewID);

        if(c == null) return new int[0];

        int[] idArr = new int[c.getMovieIDs().size()];

        for(int i = 0; i < idArr.length; i++){
            idArr[i] = c.getMovieIDs().get(i);
        }

        return idArr;
    }

    /**
     * Gets all the cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return An array of Cast objects for all people that worked on a requested
     *         film. If the film cannot be found, then return null
     */
    @Override
    public Cast[] getCast(int filmID) {
        if(filmData.get(filmID) == null) return null;

        return filmData.get(filmID).getCast();
    }

    /**
     * Gets all the cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return An array of Cast objects for all people that worked on a requested
     *         film. If the film cannot be found, then return null
     */
    @Override
    public Crew[] getCrew(int filmID) {
        if(filmData.get(filmID) == null) return null;

        return filmData.get(filmID).getCrew();
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        if(filmData.get(filmID) == null) return -1;

        return filmData.get(filmID).getCast().length;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeofCrew(int filmID) {
        if(filmData.get(filmID) == null) return -1;

        return filmData.get(filmID).getCrew().length;
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return uFilms.size();
    }

    /**
     * Gets the cast name for a given cast ID
     * 
     * @param castID The ID of the cast member to be found
     * @return The name of the cast member for the given ID. If the ID is invalid,
     *         then null should be returned
     */
    @Override
    public String getCastName(int castID) {
        
        if(castData.get(castID) == null) return null;

        return castData.get(castID).getInfo().getName();
    }

    /**
     * Gets the crew name for a given crew ID
     * 
     * @param crewID The ID of the crew member to be found
     * @return The name of the crew member for the given ID. If the ID is invalid,
     *         then null should be returned
     */
    @Override
    public String getCrewName(int crewID) {
        if(crewData.get(crewID) == null) return null;

        return crewData.get(crewID).getInfo().getName();
    }

    /**
     * Gets a list of all unique cast IDs present in the data structure
     * 
     * @return An array of all unique cast IDs. If there are no cast IDs, then
     *         return an empty array
     */
    @Override
    public int[] getUniqueCastIDs() {
        // convert uCast to array
        int[] cids = new int[uCast.size()];

        for(int i = 0; i < cids.length; i++){
            cids[i] = uCast.get(i);
        }

        return cids;
    }

    /**
     * Gets a list of all unique crew IDs present in the data structure
     * 
     * @return An array of all unique crew IDs. If there are no crew IDs, then
     *         return an empty array
     */
    @Override
    public int[] getUniqueCrewIDs() {
        // convert uCrew to array
        int[] cids = new int[uCrew.size()];

        for(int i = 0; i < cids.length; i++){
            cids[i] = uCrew.get(i);
        }

        return cids;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of Cast objects of all cast members that have the requested
     *         string in their name
     */
    @Override
    public Cast[] findCast(String cast) {

        //Iterate through uCast to get IDs, and compare string to name using Cast class
        ArrayList<Cast> castArrList = new ArrayList<Cast>();
        for(int i = 0; i < uCast.size(); i++){
            int c = uCast.get(i);
            if(getCastName(c).toLowerCase().contains(cast.toLowerCase())){ // case insensitive
                castArrList.add(castData.get(c).getInfo());
            }
        }

        //Convert arraylist to array
        Cast[] returnArr = new Cast[castArrList.size()]; 

        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = castArrList.get(i);
        }

        return returnArr;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of Crew objects of all crew members that have the requested
     *         string in their name
     */
    @Override
    public Crew[] findCrew(String crew) {

        //Iterate through uCrew to get IDs, and compare string to name using Crew class
        ArrayList<Crew> crewArrList = new ArrayList<Crew>();
        for(int i = 0; i < uCrew.size(); i++){
            int c = uCrew.get(i);
            if(getCrewName(c).toLowerCase().contains(crew.toLowerCase())){ // case insensitive
                crewArrList.add(crewData.get(c).getInfo());
            }
        }

        //Convert arraylist to array
        Crew[] returnArr = new Crew[crewArrList.size()]; 

        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = crewArrList.get(i);
        }

        return returnArr;
    }

    /**
     * Finds all stars. A star is the following person: a star actor is
     * a cast member who have appeared in 3 or more movies, where each movie
     * has an average score of 4 or higher.
     * 
     * @param ratings The ratings for all films
     * @return An array of Cast IDs that are stars
     */
    @Override
    public int[] findStarCastID(Ratings ratings) {
        ArrayList<Integer> starsArrList = new ArrayList<Integer>();

        // Iterate through cast
        for(int i = 0; i < uCast.size(); i++){
            int c = uCast.get(i);
            int numOfHighRatedMovies = 0;

            // Iterate through movies
            for(int j = 0; j < castData.get(c).getMovieIDs().size(); j++){
                int m = castData.get(c).getMovieIDs().get(j);

                // If average rating is higher than 4, increment counter
                if(ratings.getMovieAverageRatings(m) >= 4){
                    numOfHighRatedMovies++;
                }
            }

            // If counter is more than or equal to 3, cast member is a star
            if(numOfHighRatedMovies >= 3){
                starsArrList.add(c);
            }
        }


        // Convert arraylist to array
        int[] returnArr = new int[starsArrList.size()];

        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = starsArrList.get(i);
        }

        return returnArr;
    }

    /**
     * Finds all superstars. A superstar is the following person: a star actor is
     * also a superstar if they have played in at least two movies with another star
     * actor.
     * 
     * @param ratings The ratings for all films
     * @return An array of Cast IDs that are super stars
     */
    @Override
    public int[] findSuperStarCastID(Ratings ratings) {

        // Use findStarCastID to find stars
        int[] starCastArr = findStarCastID(ratings);

        ArrayList<Integer> superStarsArrList = new ArrayList<Integer>();

        // Iterate through stars
        for(int i = 0; i < starCastArr.length; i++){
            
            int currentStar = starCastArr[i];
            int numOfMoviesWithStars = 0;

            // Iterate through stars again to compare stars with other stars
            for(int j = 0; j < starCastArr.length; j++){
                int otherStar = starCastArr[j];

                if(otherStar == currentStar) continue; //ignore self

                // Use findDistance method to determine if stars starred in the same movie
                int movieDistance = findDistance(currentStar, otherStar).length;

                // If they did, increment counter
                if(movieDistance > 0 && movieDistance <= 2) numOfMoviesWithStars++;
            }

            // If counter >= 2, then star is a superstar
            if(numOfMoviesWithStars >= 2){
                superStarsArrList.add(currentStar);
            }

        }

        int[] returnArr = new int[superStarsArrList.size()];

        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = superStarsArrList.get(i);
        }

        return returnArr;
    }

    /**
     * Finds the distance between cast members A and B, by looking at common cast
     * members in films. For example, if A and B were in different movies, but both
     * started in a movie with cast member C, then there distance would be 1.
     * 
     * @param castIDA The starting cast member
     * @param castIDB The finishing cast member
     * @return If there is no connection, then return an empty array. If castIDA ==
     *         castIDB, then return an array containing ONLY castIDB. If there is a
     *         path from castIDA to castIDB, then all cast IDs in the path should be
     *         listed in order in the returned array, including castIDB. In the
     *         above example, the array should return {castIDC, castIDB}.
     */
    @Override
    public int[] findDistance(int castIDA, int castIDB) {
        return castConnections.findDistance(castIDA, castIDB); // Use findDistance method in MyGraph class
    }

}
