package classe;

import java.util.ArrayList;
import java.util.List;

public class Lehrer extends classe.Person {
    private long lehrerID;
    private List<Long> courses;

    /**
     * @param person
     * @param lehrerID
     */
    public Lehrer(Person person, long lehrerID) {
        super(person.getPersonID(), person.getFirstName(), person.getLastName());
        this.lehrerID = lehrerID;
        this.courses = new ArrayList<>();
    }

    public Lehrer(Person person, long lehrerID, List<Long> courses) {
        super(person.getPersonID(), person.getFirstName(), person.getLastName());
        this.lehrerID = lehrerID;
        this.courses = courses;
    }


    @Override
    public Long getPersonID() {
        return super.getPersonID();
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    public long getLehrerID() { return lehrerID; }

    public List<Long> getCourses() { return courses; }


    @Override
    public void setPersonID(Long personID) {
        super.setPersonID(personID);
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public void setLehrerID(long lehrerID) { this.lehrerID = lehrerID; }

    public void setCourses(List<Long> courses) { this.courses = courses; }

    @Override
    public String toString() {
        return "Lehrer{" +
                "lehrerID=" + lehrerID +
                ", courses=" + courses +
                "} " + super.toString();
    }
}