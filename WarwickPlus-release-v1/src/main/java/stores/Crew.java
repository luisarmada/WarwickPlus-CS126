package stores;

import interfaces.ICrew;

public class Crew implements interfaces.ICrew {

    private String elementID = null;
    private String department = null;
    private int gender = -1;
    private int id = -1;
    private String job = null;
    private String name = null;
    private String profilePath = null;
    
    public Crew(String elementID, String department, int gender, int id, String job, String name, String profilePath) {
        this.elementID = elementID;
        this.department = department;
        this.gender = gender;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profilePath = profilePath;
    }

    @Override
    public String getElementID() {
        return elementID;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public int getGender() {
        return gender;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getJob() {
        return job;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;        
    }

    @Override
    public int compareTo(ICrew o) {
        return ((Integer) id).compareTo(o.getID());
    }

    @Override
    public String toString() {
        return "Element ID: " + elementID + "\tName: " + name + "\tDepartment : " + department + "\tJob: " + job + "\tGender: " + gender + "\tID: " + id + "\tProfile Path: " + profilePath;
    }
    
}
