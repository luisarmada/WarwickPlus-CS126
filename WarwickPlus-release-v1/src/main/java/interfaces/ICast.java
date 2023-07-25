package interfaces;
import java.lang.Comparable;

public interface ICast extends Comparable<ICast> {
    public int getElementID();

    public String getCharacter();

    public String getCreditID();

    public int getGender();

    public int getID();

    public String getName();

    public int getOrder();
    public void setOrder(int order);

    public String getProfilePath();
    public void setProfilePath(String profilePath);

    public int compareTo(ICast o);

    public String toString();

}
