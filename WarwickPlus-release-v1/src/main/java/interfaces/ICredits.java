package interfaces;

import stores.Cast;
import stores.Crew;
import stores.Ratings;

public interface ICredits{
    public boolean add(Cast[] cast, Crew[] crew, int id);
    public boolean remove(int id);
    
    public int[] getFilmIDs();
    public int[] getFilmIDsFromCastID(int castID);
    public int[] getFilmIDsFromCrewID(int crewID);

    public Cast[] getCast(int filmID);
    public Crew[] getCrew(int filmID);

    public int sizeOfCast(int filmID);
    public int sizeofCrew(int filmID);

    public int[] getUniqueCastIDs();
    public int[] getUniqueCrewIDs();

    public Cast[] findCast(String searchTerm);
    public Crew[] findCrew(String searchTerm);

    public String getCastName (int castID);
    public String getCrewName(int crewID);

    public int[] findStarCastID(Ratings ratings);
    public int[] findSuperStarCastID(Ratings ratings);

    public int[] findDistance(int castIDA, int castIDB);

    public int size();
    public String toString();
}