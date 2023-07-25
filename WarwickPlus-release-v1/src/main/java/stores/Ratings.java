package stores;

import java.util.Calendar;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {

    private int numOfRatings; // total number of ratings

    // Contains IDs of all movies and users, in order of most ratings - multipurpose
    private ArrayList<Integer> topMoviesArray, topUsersArray;

    /*
     * The RatingData class stores ratings for a single user or movie.
    */
    public class RatingData{
        private float sumOfRatings; // stores total of ratings made, used for finding average
        
        //stores ID of rater as key and a Rating class as value
        // e.g. if RatingData belonged to a userID, otherID's key would be a movieID and the value would be a rating.
        //      if RatingData belonged to a movieID, otherID's key would be a userID and the user's rating for value.
        public HashMap<Integer, Rating> otherIDs;

        public ArrayList<Integer> IDsTimeOrder; //Ratings made in time order, for use in the 'getRatingsBetween' functions, desc order

        RatingData(){
            sumOfRatings = 0;
            otherIDs = new HashMap<Integer, Rating>();
            IDsTimeOrder = new ArrayList<Integer>();
        }

        public class Rating{
            public float rating;
            public Calendar timestamp;
        }

        // Sets (or adds if doesn't exist) a rating into otherID's hashmap
        public void setRating(int id, float rating, Calendar timestamp){

            if(otherIDs.get(id) != null){ // Already exists
                remove(id); // to reset
            }

            // Create new Rating
            Rating rd = new Rating();
            rd.rating = rating;
            rd.timestamp = timestamp;
            otherIDs.put(id, rd);
            sumOfRatings += rating;

            // Add to IDsTimeOrder array based on timestamp
            int arrSize = IDsTimeOrder.size();
            if(arrSize == 0){
                IDsTimeOrder.add(id);
            } else{

                for(int i = 0; i < arrSize; i++){ // Add to time order arr in right position
                    if(timestamp.equals(otherIDs.get(IDsTimeOrder.get(i)).timestamp) || timestamp.after(otherIDs.get(IDsTimeOrder.get(i)).timestamp)){
                        IDsTimeOrder.addX(i, id);
                        break;
                    } else if(i == arrSize - 1){
                        IDsTimeOrder.add(id);
                    }
                }
            }
        }

        // Removing a rating
        public void remove(int id){
            sumOfRatings -= otherIDs.get(id).rating;
            otherIDs.remove(id);

            for(int i = 0; i < IDsTimeOrder.size(); i++){
                if(IDsTimeOrder.get(i) == id){
                    IDsTimeOrder.removeIndex(i);
                    break;
                }
            }

        }

        public boolean contains(int id){
            return !otherIDs.containsKey(id);
        }

        public int size(){
            return otherIDs.size();
        }

        public float getAverageRating(){
            return sumOfRatings/(float)size();
        }

    }

    private HashMap<Integer, RatingData> userRatings, movieRatings; // contains which users rated which movies, and vice versa 
    // user ratings: user id , (num of Ratings, movies seperated by commas)

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     */
    public Ratings() {
        userRatings = new HashMap<Integer, RatingData>();
        movieRatings = new HashMap<Integer, RatingData>();
        numOfRatings = 0;
        topMoviesArray = new ArrayList<Integer>();
        topUsersArray = new ArrayList<Integer>();
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userID, int movieID, float rating, Calendar timestamp) {

        // Avoid duplicates
        if(userRatings.get(userID) != null && userRatings.get(userID).otherIDs.get(movieID) != null){
            return false;
        }
        
        if(userRatings.get(userID) == null) { // Check if user doesnt exist on database
            // Create new RatingData for new userID
            RatingData userRD = new RatingData();
            userRD.setRating(movieID, rating, timestamp);
            userRatings.put(userID, userRD);
        } else {
            // else, just set rating for existing RatingData class in HashMap
            userRatings.get(userID).setRating(movieID, rating, timestamp);
        }

        // Add user to top user array based on number of ratings
        if(!topUsersArray.contains(userID)){
            // If user not in array, add to start of array
            topUsersArray.addX(0, userID); 
        } else {
            // If user in array, move in top users array if number of ratings is now more than the next element
            int currentUserRatings = userRatings.get(userID).size();
            topUsersArray.removeIndex(topUsersArray.indexOf(userID));
            boolean addedToArr = false;
            for(int i = 0; i < topUsersArray.size(); i++){ // Ascending order
                if(currentUserRatings > userRatings.get(topUsersArray.get(i)).size()){
                    continue;
                } else {
                    topUsersArray.addX(i, userID);
                    addedToArr = true;
                    break;
                }
            }
            if(!addedToArr){ // If reached end of array, add to end
                topUsersArray.add(userID);
            }
        }

        // REPEAT WITH MOVIES
        if(movieRatings.get(movieID) == null) {
            RatingData movieRD = new RatingData();
            movieRD.setRating(userID, rating, timestamp);
            movieRatings.put(movieID, movieRD);
        } else {
            movieRatings.get(movieID).setRating(userID, rating, timestamp);
        }

        if(!topMoviesArray.contains(movieID)){
            topMoviesArray.addX(0, movieID);
        } else {
            int currentMovieRatings = movieRatings.get(movieID).size();
            topMoviesArray.removeIndex(topMoviesArray.indexOf(movieID));
            boolean addedToArr = false;
            for(int i = 0; i < topMoviesArray.size(); i++){ // ascending
                if(currentMovieRatings > movieRatings.get(topMoviesArray.get(i)).size()){
                    continue;
                } else {
                    topMoviesArray.addX(i, movieID);
                    addedToArr = true;
                    break;
                }
            }
            if(!addedToArr){
                topMoviesArray.add(movieID);
            }
        }

        numOfRatings++;

        return true;
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userID, int movieID) {

        // If rating doesn't exist, return false
        if(userRatings.get(userID) == null) return false;
        if(userRatings.get(userID).otherIDs.get(movieID) == null) return false;

        userRatings.get(userID).remove(movieID);
        movieRatings.get(movieID).remove(userID);

        numOfRatings--;

        return true;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userID, int movieID, float rating, Calendar timestamp) {
        userRatings.get(userID).setRating(movieID, rating, timestamp);
        movieRatings.get(movieID).setRating(userID, rating, timestamp);
        return true;
    }

    /**
     * Find all ratings between a given start date and end date. If a rating falls
     * exactly on a given start date or a given end date, then this should not be
     * included
     * 
     * @param start The start time for the range
     * @param end   The end time for the range
     * @return An array of ratings between start and end. If there are no ratings,
     *         then return an empty array
     */
    @Override
    public float[] getRatingsBetween(Calendar start, Calendar end) {
        ArrayList<Float> newArr = new ArrayList<Float>();

        // Go through all users, use their IDsTimeOrder array and find ratings made between start and end
        // As it goes through all users and their ratings, it must return all ratings between
        for(int i = 0; i < topUsersArray.size(); i++){
            int userID = topUsersArray.get(i);
            ArrayList<Integer> arr = userRatings.get(userID).IDsTimeOrder;
            
            // Go through IDsTimeOrder ArrayList
            for(int j = 0; j < arr.size(); j++){
                Calendar currTime = userRatings.get(userID).otherIDs.get(arr.get(j)).timestamp;
                if(currTime.after(start) && currTime.before(end)){ // Compare Times
                    newArr.add(userRatings.get(userID).otherIDs.get(arr.get(j)).rating);
                }
            }
        }

         // Convert array list to array
        float[] returnArr = new float[newArr.size()];
        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = newArr.get(i);
        }

        return returnArr;
    }

    /**
     * Find all ratings for a given film, between a given start date and end date.
     * If a rating falls exactly on a given start date or a given end date, then
     * this should not be included
     * 
     * @param movieID The movie ID
     * @param start   The start time for the range
     * @param end     The end time for the range
     * @return An array of ratings between start and end for a given film. If there
     *         are no ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatingsBetween(int movieID, Calendar start, Calendar end) {

        // Access IDsTimeOrder for movieID's RatingData
        ArrayList<Integer> arr = movieRatings.get(movieID).IDsTimeOrder;
        ArrayList<Float> newArr = new ArrayList<Float>();

        // Go through the IDsTimeOrder and return ratings between start and end
        for(int i = 0; i < arr.size(); i++){
            Calendar currTime = movieRatings.get(movieID).otherIDs.get(arr.get(i)).timestamp;
            System.out.println(currTime);
            if(currTime.after(start) && currTime.before(end)){
                newArr.add(movieRatings.get(movieID).otherIDs.get(arr.get(i)).rating);
            }
        }

        float[] returnArr = new float[newArr.size()]; // convert array list to array
        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = newArr.get(i);
        }

        return returnArr;
    }

    /**
     * Find all ratings for a given user, between a given start date and end date.
     * If a rating falls exactly on a given start date or a given end date, then
     * this should not be included
     * 
     * @param userID The user ID
     * @param start  The start time for the range
     * @param end    The end time for the range
     * @return An array of ratings between start and end for a given user. If there
     *         are no ratings, then return an empty array
     */
    @Override
    public float[] getUserRatingsBetween(int userID, Calendar start, Calendar end) {
        
        // Access IDsTimeOrder for movieID's RatingData
        ArrayList<Integer> arr = userRatings.get(userID).IDsTimeOrder;
        ArrayList<Float> newArr = new ArrayList<Float>();

        // Go through the IDsTimeOrder and return ratings between start and end
        for(int i = 0; i < arr.size(); i++){
            Calendar currTime = userRatings.get(userID).otherIDs.get(arr.get(i)).timestamp;
            if(currTime.after(start) && currTime.before(end)){
                newArr.add(userRatings.get(userID).otherIDs.get(arr.get(i)).rating);
            }
        }

        float[] returnArr = new float[newArr.size()]; // convert array list to array
        for(int i = 0; i < returnArr.length; i++){
            returnArr[i] = newArr.get(i);
        }

        return returnArr;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieID) {

        if(movieRatings.get(movieID) == null) return new float[0];

        // All ratinsg would be stored in IDsTimeOrder arrayList as it contains all userID's and their ratings for the movie
        ArrayList<Integer> otherIDsArr = movieRatings.get(movieID).IDsTimeOrder;

        // Convert arrayList to array
        float[] mRatings = new float[otherIDsArr.size()];
        for(int i = 0; i < otherIDsArr.size(); i++){
            // flip order
            mRatings[otherIDsArr.size()-(i+1)] = movieRatings.get(movieID).otherIDs.get(otherIDsArr.get(i)).rating;
        }

        return mRatings;
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userID) {

        if(userRatings.get(userID) == null) return new float[0];

        // All ratinsg would be stored in IDsTimeOrder arrayList as it contains all movieID's and their ratings
        ArrayList<Integer> otherIDsArr = userRatings.get(userID).IDsTimeOrder;

        // Convert arrayList to array
        float[] uRatings = new float[otherIDsArr.size()];
        for(int i = 0; i < otherIDsArr.size(); i++){
            // flip order
            uRatings[otherIDsArr.size()-(i+1)] = userRatings.get(userID).otherIDs.get(otherIDsArr.get(i)).rating;
        }

        return uRatings;
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. If the film cannot be
     *         found, or there are no rating, return 0
     */
    @Override
    public float getMovieAverageRatings(int movieID) {
        // Returns 0 if movieID is null, else return average
        return movieRatings.get(movieID) == null ? 0 : movieRatings.get(movieID).getAverageRating();
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found, or there are no rating, return 0
     */
    @Override
    public float getUserAverageRatings(int userID) {
        // Returns 0 if userID is null, else return average
        return userRatings.get(userID) == null ? 0 : userRatings.get(userID).getAverageRating();
    }

    /**
     * Gets the top N films with the most ratings, in order from most to least
     * 
     * @param num The number of films that should be returned
     * @return A sorted array of film IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num films in the store,
     *         then the array should be the same length as the number of films
     */
    @Override
    public int[] getTopMovies(int num) {
        if(num == 0) return new int[0];

        //Creates new return array based on num input. Array size is topMoviesArray max
        int[] topMovies = new int[Math.min(num, topMoviesArray.size())];
        int count = 0;

        // Convert topMoviesArray list to array
        while(count < topMovies.length){
            count++;
            topMovies[count-1] = topMoviesArray.get(topMoviesArray.size() - count);
            
        }
        return topMovies;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users
     */
    @Override
    public int[] getMostRatedUsers(int num) {

        if(num == 0) return new int[0];

        //Creates new return array based on num input. Array size is topUsersArray max
        int[] topUsers = new int[Math.min(num, topUsersArray.size())];
        int count = 0;

        // Convert topUsersArray list to array
        while(count < topUsers.length){
            count++;
            topUsers[count-1] = topUsersArray.get(topUsersArray.size() - count);
            
        }

        return topUsers;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return numOfRatings;
    }

}
