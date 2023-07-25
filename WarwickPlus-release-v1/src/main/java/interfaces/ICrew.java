package interfaces;
import java.lang.Comparable;

public interface ICrew extends Comparable<ICrew> {

    public String getElementID();

    public String getDepartment();

    public int getGender();

    public int getID();

    public String getJob();

    public String getName();

    public String getProfilePath();
    public void setProfilePath(String profilePath);

    public int compareTo(ICrew o);

    public String toString();
}
